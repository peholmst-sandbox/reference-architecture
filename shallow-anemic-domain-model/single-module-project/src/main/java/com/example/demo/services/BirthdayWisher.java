package com.example.demo.services;

import com.example.demo.domain.*;
import com.example.demo.services.spi.MailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDate;
import java.time.MonthDay;
import java.time.Period;
import java.util.concurrent.Executor;

@Service
class BirthdayWisher {
    private static final Logger log = LoggerFactory.getLogger(BirthdayWisher.class);
    private final MailSender mailSender;
    private final ContactRepository contactRepository;
    private final BirthdayWishRepository birthdayWishRepository;
    private final Executor executor;

    BirthdayWisher(MailSender mailSender,
                   ContactRepository contactRepository,
                   BirthdayWishRepository birthdayWishRepository,
                   @Qualifier("applicationTaskExecutor") Executor executor) {
        this.mailSender = mailSender;
        this.contactRepository = contactRepository;
        this.birthdayWishRepository = birthdayWishRepository;
        this.executor = executor;
    }

    @Transactional(propagation = Propagation.NEVER) // Each repository call should run inside its own transaction
    public void sendBirthdayWishesForToday(Clock clock) {
        log.debug("Sending birthday wishes for today");
        // TODO In a real world application, this query should be paginated or streamed.
        contactRepository.findAll(ContactSpecifications.withBirthDay(MonthDay.now(clock)))
                .forEach(contact -> executor.execute(() -> sendBirthdayWishes(contact, clock)));
    }

    @Transactional(propagation = Propagation.NEVER) // Each repository call should run inside its own transaction
    public void sendBirthdayWishes(Contact contact, Clock clock) {
        if (contact.getBirthDate() == null) {
            log.debug("Contact [{}] has no birth date, skipping", contact.getId());
            return;
        }
        if (contact.getEmail() == null) {
            log.debug("Contact [{}] has no email, skipping", contact.getId());
            return;
        }

        // Is it the contact's birthday today in their timezone?
        var birthday = contact.getBirthDate().atStartOfDay(contact.getTimeZone()).toLocalDate();
        var today = LocalDate.now(clock.withZone(contact.getTimeZone()));
        if (!birthday.getMonth().equals(today.getMonth()) || birthday.getDayOfMonth() != today.getDayOfMonth()) {
            log.debug("Contact [{}] has not a birthday on {}, skipping", contact.getId(), today);
            return;
        }

        // Has the contact already been wished a happy birthday this year?
        var alreadyWished = birthdayWishRepository.existsByContactAndYear(contact, today.getYear());
        if (alreadyWished) {
            log.debug("Contact [{}] has already been wished a happy birthday this year, skipping", contact.getId());
            return;
        }

        var ageInYears = Period.between(birthday, today).getYears();
        var message = "Congratulations %s %s for turning %d today!".formatted(contact.getFirstName(), contact.getLastName(), ageInYears);

        birthdayWishRepository.saveAndFlush(new BirthdayWish(contact, today.getYear()));
        mailSender.sendMail(contact.getEmail(), "Happy birthday!", message);

        log.info("Birthday wish has been sent to contact [{}]", contact.getId());
    }
}
