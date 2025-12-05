// Class ProviderForm
// This class is responsible for the form that 
//is filled out when a service is provided.
// It should detail the who for, who by, and what was provided
// INCOMPLETE

package chocan;

public class ProviderForm {
    private byte hours;
    private byte minutes;
    private byte seconds;
    private String number;
    private String name;
    private byte day;
    private byte month;
    private short year;
    private double fee;

    private String memberName;
    private String memberNumber;

    private String providerName;
    private String providerNumber;

    private String serviceCode;
    private String serviceName;

    private String comments;

    // No-arg constructor
    public ProviderForm() {}

    // Constructor with all fields
    public ProviderForm(byte hours, byte minutes, byte seconds, byte day, byte month, short year, String providerName, String providerNumber, String memberName, String memberNumber, String serviceCode, String serviceName, double fee, String comments)
    {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
        this.day = day;
        this.month = month;
        this.year = year;
        this.providerName = providerName;
        this.providerNumber = providerNumber;
        this.memberName = memberName;
        this.memberNumber = memberNumber;
        this.serviceCode = serviceCode;
        this.serviceName = serviceName;
        this.fee = fee;
        this.comments = comments;
    }

    // Getters and Setters
    public byte getHours() { return hours; }
    public void setHours(byte hours) { this.hours = hours; }

    public byte getMinutes() { return minutes; }
    public void setMinutes(byte minutes) { this.minutes = minutes; }

    public byte getSeconds() { return seconds; }
    public void setSeconds(byte seconds) { this.seconds = seconds; }

    public byte getDay() { return day; }
    public void setDay(byte day) { this.day = day; }

    public byte getMonth() { return month; }
    public void setMonth(byte month) { this.month = month; }

    public short getYear() { return year; }
    public void setYear(short year) { this.year = year; }

    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getFee() { return fee; }
    public void setFee(double fee) { this.fee = fee; }

    public String getMemberName() { return memberName; }
    public void setMemberName(String memberName) { this.memberName = memberName; }

    public String getMemberNumber() { return memberNumber; }
    public void setMemberNumber(String memberNumber) { this.memberNumber = memberNumber; }

    public String getProviderName() { return providerName; }
    public void setProviderName(String providerName) { this.providerName = providerName; }

    public String getProviderNumber() { return providerNumber; }
    public void setProviderNumber(String providerNumber) { this.providerNumber = providerNumber; }

    public String getServiceCode() { return serviceCode; }
    public void setServiceCode(String serviceCode) { this.serviceCode = serviceCode; }

    public String getServiceName() { return serviceName; }
    public void setServiceName(String serviceName) { this.serviceName = serviceName; }

    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }

    public String getInfo() {
        return name + "|" + number + "|" + hours + "|" + minutes + "|" + seconds + "|" + day + "|" + month + "|" + year
                + "|" + fee;
    }
}
