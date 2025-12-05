package chocan;

/**
 * Operator class - responsible for managing Members and Providers
 * Written by Wheeler Knight on 12/5/2025
 */
public class Operator extends Person {
    
    private String operatorNumber;
    private String password;

    // Default constructor for Gson deserialization
    public Operator() {
        super();
    }

    public Operator(String firstName, String lastName, String phoneNumber, String address, 
                   String city, String state, String zipCode, String number, String password) {
        super(firstName, lastName, phoneNumber, address, city, state, zipCode);
        this.operatorNumber = number;
        this.password = password;
    }

    public String getOperatorNumber() {
        return operatorNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String returnInfo() {
        return firstName + "_" + lastName + "_" + phoneNumber + "_" + address + "_" + 
               city + "_" + state + "_" + zipCode + "_" + operatorNumber;
    }
}

