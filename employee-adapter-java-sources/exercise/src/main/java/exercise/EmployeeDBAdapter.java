package exercise;

/**
 * Adapter for EmployeeDB to implement Employee interface.
 * Follows Single Responsibility Principle - only responsible for adapting DB format to Employee interface.
 * Follows Open/Closed Principle - can be extended without modifying existing code.
 */
public class EmployeeDBAdapter implements Employee {
    private final EmployeeDB employeeDB;
    
    public EmployeeDBAdapter(EmployeeDB employeeDB) {
        this.employeeDB = employeeDB;
    }
    
    @Override
    public String getId() {
        return String.valueOf(employeeDB.getId());
    }
    
    @Override
    public String getFirstName() {
        return employeeDB.getFirstName();
    }
    
    @Override
    public String getLastName() {
        return employeeDB.getSurname();
    }
    
    @Override
    public String getEmail() {
        return employeeDB.getEmailAddress();
    }
}
