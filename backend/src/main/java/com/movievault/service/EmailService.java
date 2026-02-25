package com.movievault.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${mail.from}")
    private String fromEmail;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendPasswordResetEmail(String to, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(to);
        message.setSubject("MovieVault - Password Reset");
        message.setText("Click the link to reset your password:\n\n" +
                "http://localhost/reset-password?token=" + token + "\n\n" +
                "This link expires in 1 hour.\n\n" +
                "If you didn't request this, please ignore this email.");
        mailSender.send(message);
    }
}
