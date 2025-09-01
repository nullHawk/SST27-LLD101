import com.example.profiles.*;

public class TryIt {
    public static void main(String[] args) {
        ProfileService svc = new ProfileService();
        
        // Create a minimal profile
        UserProfile p = svc.createMinimal("u1", "a@b.com");
        System.out.println("Original profile: " + p.getEmail());
        
        // Demonstrate immutability - no setters available
        System.out.println("Profile is immutable - no setEmail() method exists!");
        
        // Show how to "update" by creating a new instance
        UserProfile updated = svc.updateDisplayName(p, "John Doe");
        System.out.println("Updated profile: " + updated);
        System.out.println("Original profile unchanged: " + p.getDisplayName());
        
        // Demonstrate Builder pattern
        UserProfile built = new UserProfile.Builder("u2", "user@example.com")
                .phone("123-456-7890")
                .displayName("Jane Smith")
                .marketingOptIn(true)
                .twitter("@janesmith")
                .github("janesmith")
                .build();
        System.out.println("Built profile: " + built);
        
        // Test validation
        try {
            new UserProfile.Builder("", "invalid-email").build();
        } catch (IllegalArgumentException e) {
            System.out.println("Validation works: " + e.getMessage());
        }
    }
}
