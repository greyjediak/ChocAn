/*
Person class authored by Lindsey B
Responsible for basic "actor" functions and info that Provider, Member, and 
Manager will all inherit.
 */

package chocan;

public class Person {
    protected String firstName;
    protected String lastName;
    protected String phoneNumber;
    protected String address;
    protected String city;
    protected String state;
    protected String zipCode;
    // Removed unused idNumber field - Wheeler Knight 12/4/2025

    // Default constructor for Gson deserialization - Wheeler Knight 12/5/2025
    public Person() {}

    //Add getters setters and constructors
    public Person(String firstName, String lastName, String phoneNumber, String address, String city, String state, String zipCode)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }
    
    public String getFirstName(){ return firstName;}
    public void setFirstName(String firstName){ this.firstName = firstName;}

    public String getLastName(){ return lastName;}
    public void setLastName(String lastName) {this.lastName = lastName;}

    public String getFullName() {return firstName + " " + lastName;}

    public String getPhoneNumber(){ return phoneNumber;}
    public void setNumber(String number) {this.phoneNumber = number;}

    public String getAddress(){ return address;}
    public void setAddress(String address){ this.address = address;}

    public String getCity(){ return city;}
    public void setCity(String city) {this.city = city;}

    public String getState(){ return state;}
    public void setState(String state) {this.state = state;}

    public String getZipCode(){ return zipCode;}
    public void setZipCode(String zipCode) {this.zipCode = zipCode;}
    
    // Written by Wheeler Knight on 12/4/2025 - Base returnInfo method for common fields
    public String returnBaseInfo() {
        return firstName + "_" + lastName + "_" + phoneNumber + "_" + address + "_" + city + "_" + state + "_" + zipCode;
    }
}
