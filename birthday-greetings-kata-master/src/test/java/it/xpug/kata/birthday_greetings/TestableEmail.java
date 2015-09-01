package it.xpug.kata.birthday_greetings;

public class TestableEmail extends Email {

    public TestableEmail(String sender, String recipient, String subject, String body) {
        super(sender, recipient, subject, body);
    }

    @Override
    public boolean equals(Object object) {
        Email other = (Email) object;
        return this.sender().equals(other.sender()) &&
                this.recipient().equals(other.recipient()) &&
                this.subject().equals(other.subject()) &&
                this.body().equals(other.body());
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
