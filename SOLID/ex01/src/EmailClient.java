public class EmailClient implements IEmailClient {
    public void send(String to, String body) {
        System.out.println("[EMAIL to=" + to + "] " + body);
    }
}