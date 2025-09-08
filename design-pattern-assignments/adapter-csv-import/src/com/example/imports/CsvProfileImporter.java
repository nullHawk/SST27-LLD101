package com.example.imports;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public class CsvProfileImporter implements ProfileImporter {
    private final NaiveCsvReader csvReader;
    private final ProfileService profileService;
    
    public CsvProfileImporter(NaiveCsvReader csvReader, ProfileService profileService) {
        this.csvReader = Objects.requireNonNull(csvReader, "csvReader");
        this.profileService = Objects.requireNonNull(profileService, "profileService");
    }
    
    @Override
    public int importFrom(Path csvFile) {
        Objects.requireNonNull(csvFile, "csvFile");
        
        List<String[]> rows = csvReader.read(csvFile);
        int importedCount = 0;
        
        for (int i = 0; i < rows.size(); i++) {
            String[] row = rows.get(i);
            
            // Skip header row if it exists
            if (i == 0 && isHeaderRow(row)) {
                continue;
            }
            
            if (isValidRow(row)) {
                try {
                    String id = row[0].trim();
                    String email = row[1].trim();
                    String displayName = row[2].trim();
                    
                    profileService.createProfile(id, email, displayName);
                    importedCount++;
                } catch (Exception e) {
                    System.out.println("Skipping row " + (i + 1) + ": " + e.getMessage());
                }
            } else {
                System.out.println("Skipping row " + (i + 1) + ": missing required fields (id, email) or invalid email format");
            }
        }
        
        return importedCount;
    }
    
    private boolean isHeaderRow(String[] row) {
        return row.length >= 3 && 
               ("id".equalsIgnoreCase(row[0].trim()) || 
                "email".equalsIgnoreCase(row[1].trim()) || 
                "displayName".equalsIgnoreCase(row[2].trim()));
    }
    
    private boolean isValidRow(String[] row) {
        if (row.length < 3) {
            return false;
        }
        
        String id = row[0];
        String email = row[1];
        
        // Check for missing required fields
        if (id == null || id.trim().isEmpty()) {
            return false;
        }
        
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        
        // Check email format (basic validation)
        String trimmedEmail = email.trim();
        if (!trimmedEmail.contains("@") || trimmedEmail.indexOf("@") == 0 || 
            trimmedEmail.indexOf("@") == trimmedEmail.length() - 1) {
            return false;
        }
        
        return true;
    }
}
