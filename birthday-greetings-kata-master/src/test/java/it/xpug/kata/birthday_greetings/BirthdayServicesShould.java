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

    @Before
    public void setUp() throws Exception {
        mailService = mock(MailService.class);
        employeeRepository = mock(EmployeeRepository.class);
        fakeToday = "2008/10/08";
        anyEmail = "anyEmail";
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
