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

    //GetHours() etc
    public String getInfo() {
    	return name + "|" + number + "|" + hours + "|" + minutes + "|" + seconds + "|" + day + "|" + month + "|" + year  + "|" + fee;
    }
}
