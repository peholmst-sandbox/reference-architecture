package com.example.demo.services;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Clock;

@Component
class BirthdayWisherScheduler {

    private static final String CRON_EVERY_HOUR = "0 15 * * * *";

    private final BirthdayWisher birthdayWisher;

    BirthdayWisherScheduler(BirthdayWisher birthdayWisher) {
        this.birthdayWisher = birthdayWisher;
    }

    @Scheduled(zone = "UTC", cron = CRON_EVERY_HOUR)
    public void sendBirthdayWishes() {
        birthdayWisher.sendBirthdayWishesForToday(Clock.systemUTC());
    }
}
