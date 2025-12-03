//Terminal.java class written by Lindsey Bowen
/*
The terminal will handle the main functions of the service. Providers should be able to login with credentials and provide health
services, generate service reports and log fees, and ChocAn managers should be able to login with their credentials and add/delete/edit
members and providers. They will also be able to run the Friday accounting procedure and generate reports of all existing providers and all
fees generated.
*/

package chocan;

public class Terminal {

    private ACMEAccountingServices accounting;
    private ProviderDirectory directory;
    
    public Terminal(ACMEAccountingServices accounting, ProviderDirectory directory)
    {
        this.accounting = accounting;
        this.directory = directory;
    }
}
