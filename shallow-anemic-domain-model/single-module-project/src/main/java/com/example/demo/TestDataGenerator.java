package com.example.demo;

import com.example.demo.domain.Contact;
import com.example.demo.domain.ContactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;

@Component
class TestDataGenerator {

    private static final int NUMBER_OF_CONTACTS_TO_GENERATE = 200;
    private static final Logger log = LoggerFactory.getLogger(TestDataGenerator.class);
    private final ContactRepository contactRepository;

    TestDataGenerator(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
        generateTestData();
    }

    private void generateTestData() {
        if (contactRepository.count() > 0) {
            log.info("The database is not empty so skipping test data generation");
            return;
        }
        log.info("Generating test data");
        for (int i = 1; i <= NUMBER_OF_CONTACTS_TO_GENERATE; i++) {
            String firstName = generateRandomFirstName();
            String lastName = generateRandomLastName();
            String timeZone = generateRandomTimeZone();
            String emailAddress = generateRandomEmail(firstName, lastName);
            LocalDate birthdate = generateRandomBirthdate();

            var contact = new Contact();
            contact.setFirstName(firstName);
            contact.setLastName(lastName);
            contact.setTimeZone(ZoneId.of(timeZone));
            contact.setEmail(emailAddress);
            contact.setBirthDate(birthdate);

            contactRepository.save(contact);
        }
    }

    private static final Random random = new Random();
    private static final String[] firstNames = {
            "Emily", "Emma", "Olivia", "Ava", "Sophia",
            "Isabella", "Mia", "Amelia", "Harper", "Evelyn",
            "Liam", "Noah", "Oliver", "Elijah", "William",
            "James", "Benjamin", "Lucas", "Henry", "Alexander"
    };
    private static final String[] lastNames = {
            "Smith", "Johnson", "Williams", "Jones", "Brown",
            "Davis", "Miller", "Wilson", "Moore", "Taylor",
            "Anderson", "Thomas", "Jackson", "White", "Harris",
            "Martin", "Thompson", "Garcia", "Martinez", "Robinson"
    };
    private static final String[] domains = {"example.com", "test.com", "domain.com", "sample.org", "mail.net"};
    private static final List<String> timeZones = List.copyOf(ZoneId.getAvailableZoneIds());

    private static String generateRandomFirstName() {
        return firstNames[random.nextInt(firstNames.length)];
    }

    private static String generateRandomLastName() {
        return lastNames[random.nextInt(lastNames.length)];
    }

    private static String generateRandomTimeZone() {
        return timeZones.get(random.nextInt(timeZones.size()));
    }

    private static String generateRandomEmail(String firstName, String lastName) {
        String emailPrefix = firstName.toLowerCase() + "." + lastName.toLowerCase();
        return emailPrefix + "@" + domains[random.nextInt(domains.length)];
    }

    private static LocalDate generateRandomBirthdate() {
        int year = random.nextInt(30) + 1970; // Random year between 1970 and 1999
        int month = random.nextInt(12) + 1; // Random month between 1 and 12
        int day = random.nextInt(28) + 1; // Random day between 1 and 28
        return LocalDate.of(year, month, day);
    }
}
