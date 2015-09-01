package it.xpug.kata.birthday_greetings;

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

    private EmailService emailService;
    private EmployeeRepository employeeRepository;
    private String fakeToday;
    private String anyEmail;

    @Before
    public void setUp() throws Exception {
        emailService = mock(EmailService.class);
        employeeRepository = mock(EmployeeRepository.class);
        fakeToday = "2008/10/08";
        anyEmail = "anyEmail";
    }

    @Test
    public void send_a_greet_message_on_employee_birthday() throws ParseException, MessagingException, IOException {
        String anyRecipient = "fake@fake.com";
        Employee employeeWithBirthdayToday = employeeWithBirthdayOn(fakeToday, anyRecipient);
        when(employeeRepository.getEmployees()).thenReturn(asList(employeeWithBirthdayToday,
                                                                  employeeWithBirthdayOn("2008/11/08", "other@fake.com")));
        BirthdayServiceHelper birthdayService = new BirthdayServiceHelper(employeeRepository, emailService);

        birthdayService.sendGreetings(new XDate(fakeToday));

        verify(emailService, times(1)).sendEmail(new TestableEmail("sender@here.com", anyRecipient, "Happy Birthday!", "Happy Birthday, dear anyName!"));
    }

    private Employee employeeWithBirthdayOn(String birthday, String anyRecipient) throws ParseException {
        return new Employee("anyName", "anyLastName", birthday, anyRecipient);
    }
}
