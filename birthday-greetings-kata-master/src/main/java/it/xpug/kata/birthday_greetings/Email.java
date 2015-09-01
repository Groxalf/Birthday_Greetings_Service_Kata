package it.xpug.kata.birthday_greetings;

public class Email {
    private final String sender;
    private final String recipient;
    private final String subject;
    private final String body;

    public Email(String sender, String recipient, String subject, String body) {
        this.sender = sender;
        this.recipient = recipient;
        this.subject = subject;
        this.body = body;
    }

    public String sender() {
        return sender;
    }

    public String recipient() {
        return recipient;
    }

    public String subject() {
        return subject;
    }

    public String body() {
        return body;
    }
}
