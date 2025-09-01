package com.example.orders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Immutable Order with Builder pattern for construction.
 */
public final class Order {
    private final String id;
    private final String customerEmail;
    private final List<OrderLine> lines;
    private final Integer discountPercent;
    private final boolean expedited;
    private final String notes;

    // Private constructor - only Builder can create instances
    private Order(Builder builder) {
        this.id = builder.id;
        this.customerEmail = builder.customerEmail;
        // Create defensive copy of lines list
        List<OrderLine> defensiveCopy = new ArrayList<>();
        for (OrderLine line : builder.lines) {
            defensiveCopy.add(new OrderLine(line.getSku(), line.getQuantity(), line.getUnitPriceCents()));
        }
        this.lines = Collections.unmodifiableList(defensiveCopy);
        this.discountPercent = builder.discountPercent;
        this.expedited = builder.expedited;
        this.notes = builder.notes;
    }

    // Getters - no setters for immutability
    public String getId() { return id; }
    public String getCustomerEmail() { return customerEmail; }
    public List<OrderLine> getLines() { return lines; } // returns unmodifiable list
    public Integer getDiscountPercent() { return discountPercent; }
    public boolean isExpedited() { return expedited; }
    public String getNotes() { return notes; }

    public int totalBeforeDiscount() {
        int sum = 0;
        for (OrderLine l : lines) sum += l.getQuantity() * l.getUnitPriceCents();
        return sum;
    }

    public int totalAfterDiscount() {
        int base = totalBeforeDiscount();
        if (discountPercent == null) return base;
        return base - (base * discountPercent / 100);
    }

    /**
     * Builder for creating immutable Order instances.
     */
    public static class Builder {
        // Required fields
        private final String id;
        private final String customerEmail;
        private final List<OrderLine> lines = new ArrayList<>();
        
        // Optional fields with defaults
        private Integer discountPercent;
        private boolean expedited = false;
        private String notes;

        public Builder(String id, String customerEmail) {
            this.id = id;
            this.customerEmail = customerEmail;
        }

        public Builder addLine(OrderLine line) {
            this.lines.add(line);
            return this;
        }

        public Builder addLine(String sku, int quantity, int unitPriceCents) {
            this.lines.add(new OrderLine(sku, quantity, unitPriceCents));
            return this;
        }

        public Builder discountPercent(Integer discountPercent) {
            this.discountPercent = discountPercent;
            return this;
        }

        public Builder expedited(boolean expedited) {
            this.expedited = expedited;
            return this;
        }

        public Builder notes(String notes) {
            this.notes = notes;
            return this;
        }

        public Order build() {
            // Centralized validation
            if (id == null || id.trim().isEmpty()) {
                throw new IllegalArgumentException("id must not be blank");
            }
            
            if (!PricingRules.isValidEmail(customerEmail)) {
                throw new IllegalArgumentException("invalid email format");
            }
            
            if (lines.isEmpty()) {
                throw new IllegalArgumentException("order must have at least one line");
            }
            
            if (!PricingRules.isValidDiscount(discountPercent)) {
                throw new IllegalArgumentException("discount must be between 0 and 100");
            }
            
            return new Order(this);
        }
    }
}
