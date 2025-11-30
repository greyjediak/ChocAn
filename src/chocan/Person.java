package chocan;

public class Person {
    protected String firstName;
    protected String lastName;
    protected String number;
    protected String address;
    protected String city;
    protected String state;
    protected String zipCode;

    //Add getters setters and constructors
    public Person(String firstName, String lastName, String number, String address, String city, String state, String zipCode)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.number = number;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }
    
    public String getFirstName(){ return firstName;}
    public void setFirstName(String firstName){ this.firstName = firstName;}

    public String getLastName(){ return lastName;}
    public void setLastName(String lastName) {this.lastName = lastName;}

    public String getNumber(){ return number;}
    public void setNumber(String number) {this.number = number;}

    public String getAddress(){ return address;}
    public void setAddress(String address){ this.address = address;}

    public String getCity(){ return city;}
    public void setCity(String city) {this.city = city;}

    public String getState(){ return state;}
    public void setState(String state) {this.state = state;}

    public String getZipCode(){ return zipCode;}
    public void setZipCode(String zipCode) {this.zipCode = zipCode;}


}
