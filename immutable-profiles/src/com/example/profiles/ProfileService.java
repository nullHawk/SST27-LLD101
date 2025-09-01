package com.example.profiles;

import java.util.Objects;

/**
 * Service for creating immutable UserProfile instances using Builder pattern.
 */
public class ProfileService {

    /**
     * Creates a minimal profile with only required fields.
     * Validation is centralized in the Builder.
     */
    public UserProfile createMinimal(String id, String email) {
        return new UserProfile.Builder(id, email)
                .build();
    }

    /**
     * Creates a profile with display name.
     * Returns a new immutable instance instead of mutating existing one.
     */
    public UserProfile createWithDisplayName(String id, String email, String displayName) {
        return new UserProfile.Builder(id, email)
                .displayName(displayName)
                .build();
    }

    /**
     * Creates a complete profile with all optional fields.
     */
    public UserProfile createComplete(String id, String email, String phone, 
                                    String displayName, String address, 
                                    boolean marketingOptIn, String twitter, String github) {
        return new UserProfile.Builder(id, email)
                .phone(phone)
                .displayName(displayName)
                .address(address)
                .marketingOptIn(marketingOptIn)
                .twitter(twitter)
                .github(github)
                .build();
    }

    /**
     * Updates a profile by creating a new immutable instance with modified display name.
     * This replaces the old mutating approach.
     */
    public UserProfile updateDisplayName(UserProfile original, String newDisplayName) {
        Objects.requireNonNull(original, "profile");
        
        return new UserProfile.Builder(original.getId(), original.getEmail())
                .phone(original.getPhone())
                .displayName(newDisplayName)
                .address(original.getAddress())
                .marketingOptIn(original.isMarketingOptIn())
                .twitter(original.getTwitter())
                .github(original.getGithub())
                .build();
    }
}
