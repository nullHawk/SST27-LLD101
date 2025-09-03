package exercise;

/**
 * Adapter for EmployeeLDAP to implement Employee interface.
 * Follows Single Responsibility Principle - only responsible for adapting LDAP format to Employee interface.
 * Follows Open/Closed Principle - can be extended without modifying existing code.
 */
public class EmployeeLDAPAdapter implements Employee {
    private final EmployeeLDAP employeeLDAP;
    
    public EmployeeLDAPAdapter(EmployeeLDAP employeeLDAP) {
        this.employeeLDAP = employeeLDAP;
    }
    
    @Override
    public String getId() {
        return employeeLDAP.get("uid");
    }
    
    @Override
    public String getFirstName() {
        return employeeLDAP.get("givenName");
    }
    
    @Override
    public String getLastName() {
        return employeeLDAP.get("sn");
    }
    
    @Override
    public String getEmail() {
        return employeeLDAP.get("mail");
    }
}
