package it.xpug.kata.birthday_greetings;

import java.io.*;
import java.text.ParseException;

import javax.mail.*;
import javax.mail.internet.*;

public class Main {

	public static void main(String[] args) throws AddressException, IOException, ParseException, MessagingException {
        String host = "localhost";
        int port = 9999;
        BirthdayService service = new BirthdayService("employee_data.txt", new MailService(host, port));
		service.sendGreetings(new XDate());
	}

}
