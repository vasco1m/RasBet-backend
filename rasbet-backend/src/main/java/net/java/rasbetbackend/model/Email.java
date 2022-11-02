package net.java.rasbetbackend.model;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.websocket.Session;

public class Email {
    private String to;
    private String from = "rasbet.test@gmail.com";
    private String host = "localhost";
    private String subject;
    private String text;


    public Email(String to, String subject, String text) {
        this.to = to;
        this.subject = subject;
        this.text = text;
    }

    public void send() {
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        Session session = Session.getDefaultInstance(properties);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            message.setSubject(subject);

            message.setText(text);

            Transport.send(message);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

}
