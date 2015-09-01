package it.xpug.kata.birthday_greetings;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class BirthdayServiceHelper extends BirthdayService{

    public BirthdayServiceHelper(EmployeeRepository employeeRepository, EmailService emailService) {
        super(employeeRepository, emailService);
    }

    @Override
    protected List<Employee> getEmployees() throws IOException, ParseException {
        return employeeRepository.getEmployees();
    }
}
