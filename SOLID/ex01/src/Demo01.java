

public class Demo01 {
    public static void main(String[] args) {
        IEmailClient emailClient = new EmailClient();
        OrderService orderService = new OrderService(emailClient); // Injecting interface to OrderService earlier OrderService class was directly creating EmailClient instance which violates Dependency Inversion Principle
        orderService.checkout("a@shop.com", 100.0);
    }
}
