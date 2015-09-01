package it.xpug.kata.birthday_greetings;

import com.dumbster.smtp.SimpleSmtpServer;
import com.dumbster.smtp.SmtpMessage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.Normalizer;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class MailServiceShould {

    private static final int port = 9999;
    private static final String host = "localhost";
    private SimpleSmtpServer fakeMailServer;
    private EmailService service;
    private SmtpMessage message;

    @Before
    public void setUp() throws Exception {
        fakeMailServer = SimpleSmtpServer.start(port);
        service = new EmailService(host, port);
    }

    private TestableEmail anyEmail() {
        return new TestableEmail("anySender", "anyRecipient", "anySubject", "anyBody");
    }

    @After
    public void tearDown() throws Exception {
        fakeMailServer.stop();
        Thread.sleep(200);
    }

    @Test
    public void send_an_email() throws Exception {
        service.sendEmail(anyEmail());
        message = (SmtpMessage) fakeMailServer.getReceivedEmail().next();

        assertThat(fakeMailServer.getReceivedEmailSize(), is(1));
        Email expectedEmail = anyEmail();
        assertThat(emailFrom(message), is(expectedEmail));
    }

    private TestableEmail emailFrom(SmtpMessage message) {
        return new TestableEmail(message.getHeaderValues("From")[0],
                message.getHeaderValues("To")[0],
                message.getHeaderValue("Subject"),
                message.getBody());
    }

}
