package com.example.demo.integrations.mail;

import com.example.demo.services.spi.MailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
class DummyMailSender implements MailSender {

    private static final Logger log = LoggerFactory.getLogger(DummyMailSender.class);

    @Override
    public void sendMail(String to, String subject, String body) {
        log.info("Pretending to send mail to <{}> with subject [{}] and body [{}]", to, subject, body);
    }
}
