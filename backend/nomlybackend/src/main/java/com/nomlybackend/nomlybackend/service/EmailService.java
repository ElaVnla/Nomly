package com.nomlybackend.nomlybackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

// We use sendgrid to send email to user
// DONT SPAM EMAIL PLS WE ONLY HAVE 100 FREE PER DAY
// https://stackoverflow.com/questions/48453077/how-to-configure-spring-boot-to-send-email-via-sendgrid/48457911
// https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/mail/SimpleMailMessage.html
// https://www.twilio.com/
@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendOtpEmail(String toEmail, String otpCode) {
        try
        {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom("no-reply@Nomly.com");
            message.setTo(toEmail);
            message.setSubject("Your OTP Code");
            message.setText(String.format(
                    "Hi there!\n\nCan't wait for you to start using Nomly\n\nHere is your OTP code: %s\n\nPlease enter this code to verify your account.\n\nThanks,\nTeam Nomly",
                    otpCode
            ));

            mailSender.send(message);
        }
        catch (MailException e)
        {
            throw new RuntimeException("OTP FAILEDDD");
        }
    }
}
