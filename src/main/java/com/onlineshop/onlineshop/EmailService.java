package com.onlineshop.onlineshop;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class EmailService {
    private final Properties prop;
    private final String username;
    private final String password;

    public EmailService() {
        prop = new Properties();
        prop.put("mail.smtp.host", "smtp.yandex.ru");
        prop.put("mail.smtp.port", 465);
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.ssl.enable", "true");
        username = "java-online-shop-project";
        password = "";
    }

    public void SendMail(List<String> recipients, String subject, String content) throws Exception {
        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("java-online-shop-project@yandex.ru"));
        ArrayList<InternetAddress> recipientsAddresses = new ArrayList<>();
        for (String recipient : recipients) {
            Collections.addAll(recipientsAddresses, InternetAddress.parse(recipient));
        }
        message.setRecipients(Message.RecipientType.TO, recipientsAddresses.toArray(new InternetAddress[0]));
        message.setSubject(subject);
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(content, "text/html; charset=utf-8");
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);
        message.setContent(multipart);
        Transport.send(message);
    }
}