package it.xpug.kata.birthday_greetings;

import com.dumbster.smtp.SimpleSmtpServer;
import com.dumbster.smtp.SmtpMessage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class MailServiceShould {

    private static final int port = 9999;
    private static final String host = "localhost";
    private SimpleSmtpServer fakeMailServer;
    private EmailService service;

    @Before
    public void setUp() throws Exception {
        fakeMailServer = SimpleSmtpServer.start(port);
        service = new EmailService(host, port);
    }

    @After
    public void tearDown() throws Exception {
        fakeMailServer.stop();
        Thread.sleep(200);
    }

    @Test
    public void send_an_email() throws Exception {
        service.sendEmail("anyEmail", "anySubject", "anyBody", "anyRecipient");

        SmtpMessage message = (SmtpMessage) fakeMailServer.getReceivedEmail().next();
        String recipient = message.getHeaderValues("To")[0];
        String sender = message.getHeaderValues("From")[0];
        assertThat(fakeMailServer.getReceivedEmailSize(), is(1));
        assertEquals("anyEmail", sender);
        assertEquals("anySubject", message.getHeaderValue("Subject"));
        assertEquals("anyBody", message.getBody());
        assertEquals("anyRecipient", recipient);
    }
}
