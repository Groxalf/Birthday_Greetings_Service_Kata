package it.xpug.kata.birthday_greetings;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class BirthdayService {
    protected EmployeeRepository employeeRepository;
    protected MailService mailService;
    private String employees;

    public BirthdayService(String employees, MailService mailService) {
        this.employees = employees;
        this.mailService = mailService;
    }

    public BirthdayService(EmployeeRepository employeeRepository, MailService mailService) {
        this.employeeRepository = employeeRepository;
        this.mailService = mailService;
    }

    public void sendGreetings(final XDate xDate) throws IOException, ParseException, AddressException, MessagingException {
        for (Employee employee : getEmployees()) {
            xxx(xDate, employee);
        }
    }

    protected List<Employee> getEmployees() throws IOException, ParseException {
        BufferedReader in = new BufferedReader(new FileReader(employees));
        String str = "";
        str = in.readLine(); // skip header
        List<Employee> employeesRepository = new ArrayList<Employee>();
        while ((str = in.readLine()) != null) {
            String[] employeeData = str.split(", ");
            Employee employee = new Employee(employeeData[1], employeeData[0], employeeData[2], employeeData[3]);
            employeesRepository.add(employee);

        }
        return employeesRepository;
    }

    private void xxx(XDate xDate, Employee employee) throws MessagingException {
        if (employee.isBirthday(xDate)) {
            String recipient = employee.getEmail();
            String body = "Happy Birthday, dear %NAME%!".replace("%NAME%", employee.getFirstName());
            String subject = "Happy Birthday!";
            mailService.sendMessage("sender@here.com", subject, body, recipient);
        }
    }


}
