package com.example.orders;

import java.util.List;

/**
 * Service for creating immutable Order instances using Builder pattern.
 */
public class OrderService {

    /**
     * Creates an order using the Builder pattern with validation.
     * All validation is centralized in the Builder.build() method.
     */
    public Order createOrder(String id, String email, List<OrderLine> lines, Integer discount, boolean expedited, String notes) {
        Order.Builder builder = new Order.Builder(id, email);
        
        if (lines != null) {
            for (OrderLine line : lines) {
                builder.addLine(line);
            }
        }
        
        return builder.discountPercent(discount)
                      .expedited(expedited)
                      .notes(notes)
                      .build();
    }

    /**
     * Creates a simple order with just required fields and one line.
     */
    public Order createSimpleOrder(String id, String email, String sku, int quantity, int unitPriceCents) {
        return new Order.Builder(id, email)
                .addLine(sku, quantity, unitPriceCents)
                .build();
    }

    /**
     * Creates an order with multiple lines using the Builder pattern.
     */
    public Order createOrderWithLines(String id, String email, String... lineData) {
        if (lineData.length % 3 != 0) {
            throw new IllegalArgumentException("lineData must be in groups of 3: sku, quantity, unitPriceCents");
        }
        
        Order.Builder builder = new Order.Builder(id, email);
        for (int i = 0; i < lineData.length; i += 3) {
            String sku = lineData[i];
            int quantity = Integer.parseInt(lineData[i + 1]);
            int unitPriceCents = Integer.parseInt(lineData[i + 2]);
            builder.addLine(sku, quantity, unitPriceCents);
        }
        
        return builder.build();
    }
}
