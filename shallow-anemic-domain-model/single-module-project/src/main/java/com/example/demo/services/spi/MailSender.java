package com.example.demo.services.spi;

public interface MailSender {
    void sendMail(String to, String subject, String body);
}
