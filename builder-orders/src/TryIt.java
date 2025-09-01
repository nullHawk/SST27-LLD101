import com.example.orders.*;
import java.util.List;
import java.util.ArrayList;

public class TryIt {
    public static void main(String[] args) {
        System.out.println("=== Builder Pattern Demo ===");
        
        // Create order using Builder pattern
        Order order = new Order.Builder("o1", "customer@example.com")
                .addLine("A", 1, 200)
                .addLine("B", 3, 100)
                .discountPercent(10)
                .expedited(true)
                .notes("Rush order")
                .build();
        
        System.out.println("Order created: " + order.getId());
        System.out.println("Total before discount: " + order.totalBeforeDiscount());
        System.out.println("Total after discount: " + order.totalAfterDiscount());
        System.out.println("Lines count: " + order.getLines().size());
        
        // Demonstrate immutability
        System.out.println("\n=== Immutability Demo ===");
        OrderLine originalLine = new OrderLine("C", 2, 150);
        Order immutableOrder = new Order.Builder("o2", "test@example.com")
                .addLine(originalLine)
                .build();
        
        int originalTotal = immutableOrder.totalAfterDiscount();
        System.out.println("Original total: " + originalTotal);
        
        // Try to modify the original line (this won't affect the order due to defensive copy)
        originalLine.setQuantity(999);
        int modifiedTotal = immutableOrder.totalAfterDiscount();
        System.out.println("After modifying original line: " + modifiedTotal);
        System.out.println("Totals are equal: " + (originalTotal == modifiedTotal));
        System.out.println("=> Order remains immutable due to defensive copies!");
        
        // Demonstrate validation
        System.out.println("\n=== Validation Demo ===");
        try {
            new Order.Builder("", "invalid-email").build();
        } catch (IllegalArgumentException e) {
            System.out.println("Validation works: " + e.getMessage());
        }
        
        try {
            new Order.Builder("o3", "valid@email.com").build(); // no lines
        } catch (IllegalArgumentException e) {
            System.out.println("Validation works: " + e.getMessage());
        }
        
        try {
            new Order.Builder("o4", "valid@email.com")
                    .addLine("D", 1, 100)
                    .discountPercent(150) // invalid discount
                    .build();
        } catch (IllegalArgumentException e) {
            System.out.println("Validation works: " + e.getMessage());
        }
        
        // Demonstrate OrderService
        System.out.println("\n=== OrderService Demo ===");
        OrderService service = new OrderService();
        
        List<OrderLine> lines = new ArrayList<>();
        lines.add(new OrderLine("E", 2, 75));
        lines.add(new OrderLine("F", 1, 300));
        
        Order serviceOrder = service.createOrder("o5", "service@example.com", lines, 15, false, "Service order");
        System.out.println("Service order total: " + serviceOrder.totalAfterDiscount());
        
        Order simpleOrder = service.createSimpleOrder("o6", "simple@example.com", "G", 5, 50);
        System.out.println("Simple order total: " + simpleOrder.totalAfterDiscount());
    }
}
