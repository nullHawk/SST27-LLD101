public class OrderService {
    private final double taxRate = 0.18;
    private final IEmailClient emailClient;

    public OrderService(IEmailClient emailClient) {
        this.emailClient = emailClient;
    }

    double totalWithTax(double subtotal) {
        return subtotal + subtotal * taxRate;
    }

    void checkout(String customerEmail, double subtotal) {
        double total = totalWithTax(subtotal);
        emailClient.send(customerEmail, "Thanks! Your total is " + total);
        System.out.println("Order stored (pretend DB).");
    }
}