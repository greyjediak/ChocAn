package chocan;

import java.time.LocalDate;

public class ServiceRecord {
    private String providerNumber;
    private String memberNumber;
    private String serviceCode;
    private double serviceFee;
    private LocalDate serviceDate;

    // Default constructor for Gson deserialization - Wheeler Knight 12/5/2025
    public ServiceRecord() {}

    public ServiceRecord(String providerNumber, String memberNumber, String serviceCode, double serviceFee, LocalDate serviceDate) {
        this.providerNumber = providerNumber;
        this.memberNumber = memberNumber;
        this.serviceCode = serviceCode;
        this.serviceFee = serviceFee;
        this.serviceDate = serviceDate;
    }

    public String getProviderNumber() { return providerNumber; }
    public String getMemberNumber() { return memberNumber; }
    public String getServiceCode() { return serviceCode; }
    public double getServiceFee() { return serviceFee; }
    public LocalDate getServiceDate() { return serviceDate; }
}
