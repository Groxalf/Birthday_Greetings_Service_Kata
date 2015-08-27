package it.xpug.kata.birthday_greetings;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailService {
    private final String hostname;
    private final int port;

    public MailService(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public String hostname() {
        return hostname;
    }

    public int port() {
        return port;
    }

    public void sendMessage(String senderEmail, String subject, String body, String recipient) throws AddressException, MessagingException {
        // Create a mail session
        java.util.Properties props = new java.util.Properties();
        props.put("mail.smtp.host", hostname);
        props.put("mail.smtp.port", "" + port);
        Session session = Session.getInstance(props, null);

        // Construct the message
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(senderEmail));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        msg.setSubject(subject);
        msg.setText(body);

        // Send the message
        Transport.send(msg);
    }
}
