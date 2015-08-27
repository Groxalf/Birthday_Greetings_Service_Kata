package it.xpug.kata.birthday_greetings;

public class MailService {
    private final String hostname;
    private final int port;

    public MailService(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public String hostname() {
        return hostname;
    }

    public int port() {
        return port;
    }
}
