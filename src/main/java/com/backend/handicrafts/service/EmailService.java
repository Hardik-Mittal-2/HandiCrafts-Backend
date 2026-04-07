package com.backend.handicrafts.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    @Async
    public void sendNewProductEmail(String toEmail, String productName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("New Handicraft Product Available!");
        message.setText("A new product '" + productName + "' has been added. Visit the platform now!");

        mailSender.send(message);
        log.info("New product email sent to {}", toEmail);
    }
}
