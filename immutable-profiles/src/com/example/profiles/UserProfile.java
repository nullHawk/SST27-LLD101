package com.example.profiles;

/**
 * Immutable UserProfile with Builder pattern for construction.
 */
public final class UserProfile {
    private final String id;
    private final String email;
    private final String phone;
    private final String displayName;
    private final String address;
    private final boolean marketingOptIn;
    private final String twitter;
    private final String github;

    // Private constructor - only Builder can create instances
    private UserProfile(Builder builder) {
        this.id = builder.id;
        this.email = builder.email;
        this.phone = builder.phone;
        this.displayName = builder.displayName;
        this.address = builder.address;
        this.marketingOptIn = builder.marketingOptIn;
        this.twitter = builder.twitter;
        this.github = builder.github;
    }

    // Getters - no setters for immutability
    public String getId() { return id; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getDisplayName() { return displayName; }
    public String getAddress() { return address; }
    public boolean isMarketingOptIn() { return marketingOptIn; }
    public String getTwitter() { return twitter; }
    public String getGithub() { return github; }

    @Override
    public String toString() {
        return "UserProfile{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", displayName='" + displayName + '\'' +
                ", address='" + address + '\'' +
                ", marketingOptIn=" + marketingOptIn +
                ", twitter='" + twitter + '\'' +
                ", github='" + github + '\'' +
                '}';
    }

    /**
     * Builder for creating immutable UserProfile instances.
     */
    public static class Builder {
        // Required fields
        private final String id;
        private final String email;
        
        // Optional fields with defaults
        private String phone;
        private String displayName;
        private String address;
        private boolean marketingOptIn = false;
        private String twitter;
        private String github;

        public Builder(String id, String email) {
            this.id = id;
            this.email = email;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder displayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Builder marketingOptIn(boolean marketingOptIn) {
            this.marketingOptIn = marketingOptIn;
            return this;
        }

        public Builder twitter(String twitter) {
            this.twitter = twitter;
            return this;
        }

        public Builder github(String github) {
            this.github = github;
            return this;
        }

        public UserProfile build() {
            // Centralized validation
            Validation.requireNonBlank(id, "id");
            Validation.requireEmail(email);
            
            // Optional field validations
            if (displayName != null && displayName.length() > 100) {
                throw new IllegalArgumentException("displayName must not exceed 100 characters");
            }
            
            return new UserProfile(this);
        }
    }
}
