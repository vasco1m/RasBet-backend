package net.java.rasbetbackend.services;

import it.ozimov.springboot.mail.model.Email;
import it.ozimov.springboot.mail.model.InlinePicture;
import it.ozimov.springboot.mail.service.EmailService;
import it.ozimov.springboot.mail.service.exception.CannotSendEmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Map;

@Component
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("rasbet@rasbet.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    public void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment) {

        MimeMessage message = emailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("rasbet@rasbet.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);

            FileSystemResource file
                    = new FileSystemResource(new File(pathToAttachment));
            helper.addAttachment("Invoice", file);

            emailSender.send(message);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public MimeMessage send(Email email) {
        return null;
    }

    @Override
    public MimeMessage send(Email email, String s, Map<String, Object> map, InlinePicture... inlinePictures) throws CannotSendEmailException {
        return null;
    }
}