package it.xpug.kata.birthday_greetings;

import com.dumbster.smtp.SimpleSmtpServer;
import com.dumbster.smtp.SmtpMessage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.mail.MessagingException;
import java.io.IOException;
import java.text.ParseException;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class BirthdayServicesShould {

    private MailService mailService;
    private EmployeeRepository employeeRepository;
    private String fakeToday;
    private String anyEmail;

    private static final int port = 9999;
    private final String host = "localhost";
    private SimpleSmtpServer mailServer;
    private String employees = "employee_data.txt";
    private BirthdayService service;

    @Before
    public void setUp() throws Exception {
        mailServer = SimpleSmtpServer.start(port);
        service = new BirthdayService(employees, new MailService(host, port));

        mailService = mock(MailService.class);
        employeeRepository = mock(EmployeeRepository.class);
        fakeToday = "2008/10/08";
        anyEmail = "anyEmail";
    }

    @After
    public void tearDown() throws Exception {
        mailServer.stop();
        Thread.sleep(200);
    }

    @Test
    public void send_a_greet_message_on_employee_birthday_integration() throws Exception {
        service.sendGreetings(new XDate("2008/10/08"));
        assertThat(mailServer.getReceivedEmailSize(), is(1));
        SmtpMessage message = (SmtpMessage) mailServer.getReceivedEmail().next();
        assertEquals("Happy Birthday, dear John!", message.getBody());
        assertEquals("Happy Birthday!", message.getHeaderValue("Subject"));
        String[] recipients = message.getHeaderValues("To");
        assertEquals(1, recipients.length);
        assertEquals("john.doe@foobar.com", recipients[0].toString());
    }

    @Test
    public void not_send_emails_when_nobodys_birthday() throws Exception {
        service.sendGreetings(new XDate("2008/01/01"));
        assertEquals("what? messages?", 0, mailServer.getReceivedEmailSize());
    }

    @Test
    public void send_a_greet_message_on_employee_birthday() throws ParseException, MessagingException, IOException {
        Employee employeeWithBirthdayToday = employeeWithBirthdayOn(fakeToday);
        when(employeeRepository.getEmployees()).thenReturn(asList(employeeWithBirthdayToday,
                employeeWithBirthdayOn("2008/11/08")));
        BirthdayServiceHelper birthdayService = new BirthdayServiceHelper(employeeRepository, mailService);

        birthdayService.sendGreetings(new XDate(fakeToday));

        verify(mailService, times(1)).sendMessage(anyString(), anyString(), anyString(), matches(anyEmail));
    }

    private Employee employeeWithBirthdayOn(String birthday) throws ParseException {
        return new Employee("anyName", "anyLastName", birthday, "anyEmail");
    }
}
