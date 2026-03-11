package org.example.util;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class EmailSMTP {

    public static String email(String email) {
    String from = "sivapaivalasa@gmail.com";
    String password = "gnli rfyi mbug pnzs";


    Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");


    Session session = Session.getInstance(props, new Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(from, password);

        }
    });

        try {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
        message.setSubject("You are signed in");
        message.setText("you are signed in BlueScopeTask");
        Transport.send(message);
        return "email sent";


    } catch (MessagingException e) {
        throw new RuntimeException(e);
    }
}
}
