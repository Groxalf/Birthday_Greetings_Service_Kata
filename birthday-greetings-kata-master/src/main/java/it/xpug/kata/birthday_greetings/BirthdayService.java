package it.xpug.kata.birthday_greetings;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

public class BirthdayService {
    protected EmployeeRepository employeeRepository;
    protected EmailService emailService;
    private String employees;

    public BirthdayService(String employees, EmailService emailService) {
        this.employees = employees;
        this.emailService = emailService;
    }

    public BirthdayService(EmployeeRepository employeeRepository, EmailService emailService) {
        this.employeeRepository = employeeRepository;
        this.emailService = emailService;
    }

    public void sendGreetings(final XDate xDate) throws IOException, ParseException, MessagingException {
        for (Employee employee : getEmployees()) {
            sendMessageToEmployee(employee, xDate);
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

    private void sendMessageToEmployee(Employee employee, XDate xDate) throws MessagingException {
        if (employee.isBirthday(xDate)) {
            String recipient = employee.getEmail();
            String body = "Happy Birthday, dear %NAME%!".replace("%NAME%", employee.getFirstName());
            String subject = "Happy Birthday!";
            emailService.sendEmail(new Email("sender@here.com", subject, body, recipient));
        }
    }


}
