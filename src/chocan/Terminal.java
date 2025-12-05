// Terminal Class
/*
This class implements the running terminal functions. It houses the swing gui and 
is responsible for verifying login and various 
 */

package chocan;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Terminal extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	DataCenter sys;
	ACMEAccountingServices accounting;
	JPanel loginScreen, memberScreen, managerScreen, providerScreen, serviceRequestScreen, requestApprovalScreen;
	Member currMember;
	Provider currProvider;
	Manager currManager;

	public Terminal() {
		
		sys = new DataCenter();
		accounting = new ACMEAccountingServices(sys.getMembers(), sys.getSuspendedMembers());
		
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                sys.saveInfo();
                System.exit(0);
            }
        });
        
        //Login screen set up
        loginScreen = new JPanel();
        loginScreen.setLayout(null);
        
        JTextField nameField = new JTextField();
        nameField.setBounds(140, 80, 100, 20);
        JTextField userNumberField = new JTextField();
        userNumberField.setBounds(140, 100, 100, 20);
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(140, 120, 100, 20);
        
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	switch (verifyLogin(nameField.getText(), userNumberField.getText())) {
                case 1:
                	currMember = getMember(nameField.getText(), userNumberField.getText());
                	swapInterface(memberScreen);
                    break;
                case 2:
                	currProvider = getProvider(nameField.getText(), userNumberField.getText());
                	swapInterface(providerScreen);
                    break;
                case 3:
                	currManager = getManager(nameField.getText(), userNumberField.getText());
                	swapInterface(managerScreen);
                    break;
                default:
                	JOptionPane.showMessageDialog(Terminal.this, "Invalid Login", "Warning", JOptionPane.WARNING_MESSAGE);
            }
            }
        });
       
        loginScreen.add(nameField);
        loginScreen.add(userNumberField);
        loginScreen.add(loginButton);
        
        //Login screen end
        
        //Member screen set up
        
        memberScreen = new JPanel();
        
        //Service request screen set up
        serviceRequestScreen = new JPanel();
        String[] serviceTypeOptions = sys.SERVICE_NAMES;
        JComboBox<String> optionBox = new JComboBox<>(serviceTypeOptions);
        Provider[] providers = sys.getProviders();
        String[] providerNames = new String[providers.length];
        for(int i = 0; i < providers.length; i++) {
        	providerNames[i] = providers[i].getFirstName() + providers[i].getFirstName();
        }
        JComboBox<String> providerBox = new JComboBox<>(providerNames);
        
        serviceRequestScreen.add(optionBox);
        serviceRequestScreen.add(providerBox);
        
        
        JButton submitServiceRequestButton = new JButton("Submit");
        submitServiceRequestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	sys.addPendingServiceRequest(currMember, (String) providerBox.getSelectedItem(), (String) optionBox.getSelectedItem());
                swapInterface(memberScreen);
            }
        });
        
        serviceRequestScreen.add(submitServiceRequestButton);
        
        //Service request screen end
        
        JButton MemberLogoutButton = new JButton("Logout");
        MemberLogoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                swapInterface(loginScreen);
            }
        });
        
        JButton serviceRequestButton = new JButton("Request Service");
        serviceRequestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                swapInterface(serviceRequestScreen);
            }
        });
        serviceRequestButton.setBounds(0, 20, 140, 20);
        memberScreen.add(MemberLogoutButton);
        memberScreen.add(serviceRequestButton);
        
        JLabel status = new JLabel();
        
        int serviceReportCount = fetchServiceReportCount(currMember);
        if(serviceReportCount == 0) {
        	status.setText("No Fees!");
        }
        else {
        	status.setText(accumulateFees(currMember));
        }
        
        memberScreen.add(status);
        
        //Member screen end
        
        //Provider screen set up
        
        providerScreen = new JPanel();
        
        //Request Approval set up
        requestApprovalScreen = new JPanel();
        
        
        
        JButton checkCardButton = new JButton("Check");
        checkCardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currProvider.checkCard(currMember.getCard());
            }
        });
        
        requestApprovalScreen.add(checkCardButton);
        
        JButton approveButton = new JButton("Approve");
        approveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                swapInterface(providerScreen);
            }
        });
        
        requestApprovalScreen.add(approveButton);
        
        JButton declineButton = new JButton("Decline");
        declineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                swapInterface(providerScreen);
            }
        });
        
        requestApprovalScreen.add(declineButton);
        
        //Request Approval end
        
        JButton ProviderLogoutButton = new JButton("Logout");
        ProviderLogoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                swapInterface(loginScreen);
            }
        });
        
        providerScreen.add(ProviderLogoutButton);
        
        JButton approveRequestButton = new JButton("Approve/Decline Request");
        
        approveRequestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                swapInterface(requestApprovalScreen);
            }
        });
        
        providerScreen.add(approveRequestButton);
        
        JLabel pendingServiceRequest = new JLabel();
        
        int ServiceRequestCount = fetchServiceRequestCount(currProvider);
        if(ServiceRequestCount == 0) {
        	pendingServiceRequest.setText("No Request!");
        }
        else {
        	pendingServiceRequest.setText(accumulateRequest(currProvider));
        }
        
        providerScreen.add(pendingServiceRequest);
        
        
        //Provider screen end
        
        this.add(memberScreen);
        this.add(providerScreen);
//        this.add(managerScreen);
        this.add(loginScreen);
        this.setSize(400, 400);
        this.setVisible(true);
        this.setLayout(null);
        this.setResizable(false);
	}
	
	/* 
	 * verifies login by using inputed info; 
	 * name (formatted ' first + " " + last ')
	 * and number. Call validMember, validProvider, 
	 * and validManage from "sys" (Each method is 
	 * formatted: (Name, Number)) to determine who 
	 * is logging in. Member = 1, Provider = 2, Manager = 3
	 * return 0 if the login is NOT valid
	*/
	private int verifyLogin(String name, String number) {
		if (sys.validMember(name, number))
        {
            return 1;
        }
        if (sys.validProvider(name, number))
        {
            return 2;
        }
        if (sys.validManager(name, number))
        {
            return 3;
        }
		
        return 0;
	}
	
	/* 
	 * find the specified member by calling (sys.getMembers())
	 * to a Member array (Member[]) and return it 
	*/
	
	private Member getMember(String name, String number) {
		Member[] members = sys.getMembers();
        if (members == null) {return null;}
        for (Member m : members)
        {
            if (m == null)
            {
                continue;
            }
            String fullName = m.getFullName(); // full name of each member as loop continues
            if (fullName.equals(name) && m.getCard().getMemberNumber().equals(number)) // if match
            {
                return m;
            }
        }
        return null;
	}
	
	/* 
	 * find the specified provider by calling (sys.getProviders())
	 * to a provider array (Provider[]) and return it 
	*/
	
	private Provider getProvider(String name, String number) {
		
        Provider[] providers = sys.getProviders();
        if (providers == null)
        {
            return null;
        }
        
        for (Provider p : providers)
        {
            if (p == null)
            {
                continue;
            }
            String fullName = p.getFullName();
            if (fullName.equals(name) && p.getProviderNumber().equals(number))
            {
                return p;
            }
        }
		return null;
	}
	
	/* 
	 * find the specified manager by calling (sys.getManager())
	 * to a manager array (Manager[]) and return it 
	*/

	private Manager getManager(String name, String number) {
	
        Manager[] manager = sys.getManagers();
        if (manager == null)
        {
            return null;
        }
        for (Manager mgr : manager)
        {
            if (mgr.getFullName().equals(name) && mgr.getManagerNumber().equals(number))
            {
                return mgr;
            }
        }
		return null;
	
	}
	
	private void swapInterface(JPanel screen) {
		this.setContentPane(screen);
		revalidate();
        repaint();
	}
	
	/*
	 * In this function, Call getWeeklyProviderForm from sys.
	 * return how many times the specific member shows up
	 * in weeklyProviderForm.
	 */
	
	private int fetchServiceReportCount(Member member) {
		int memberCount = 0;
		String membName = member.getFullName();
        ProviderForm[] forms = sys.getWeeklyProviderForm();

        for (ProviderForm form : forms)
        {
            if (form == null) 
            {continue;}
            if (membName.equals(form.getMemberName()))
            {
                memberCount++;
            }
        }
		return memberCount;
	}
	
	/*
	 * In this function, Call pendingServiceRequest from sys.
	 * return how many times the specific provider shows up
	 * in pendingServiceRequest.
	 */
	
	private int fetchServiceRequestCount(Provider provider) {
		int count = 0;
		
        ServiceRequest[] pendingRequest = sys.getPendingServiceRequest();
        if (pendingRequest == null) return 0;

        String providerName = provider.getFullName();
        for(ServiceRequest request : pendingRequest)
        {
            if (request == null)
            {
                continue;
            }
            if (providerName.equals(request.providerName))
            {
                count++;
            }
        }
		return count;
	}
	
	/*
	 * In this function, Call getWeeklyProviderForm from sys.
	 * for every time the member name matches, use getInfo on
	 * weeklyProviderForms and append it to returnString 
	 * followed by '+ "\n"' to get to next line
	 */
	
	private String accumulateFees(Member member) {
		
        ProviderForm[] forms = sys.getWeeklyProviderForm();
        if (forms == null) return null; // FIXME?
        String memberName = member.getFullName();
        StringBuilder returnString = new StringBuilder();
        for (ProviderForm form : forms)
        {
            if (form == null) {continue;}
            if (memberName.equals(form.getMemberName())) // add fee number to string every time member name is found
            {
                // variable double fee is converted into string text for purpose of adding to a string list
                returnString.append(String.valueOf(form.getFee())).append("\n"); 
            }
        }

		return returnString.toString();
		
	}
	
	/*
	 * In this function, Call pendingServiceRequest from sys.
	 * for every time the provider name matches, use getInfo on
	 * pendingServiceRequest and append it to returnString 
	 * followed by '+ "\n"' to get to next line
	 */
	
	private String accumulateRequest(Provider provider) {
		//String returnString = "";

        ServiceRequest[] pendingRequests = sys.getPendingServiceRequest();
        if (pendingRequests == null) return null; //FIXME?
        String providerName = provider.getFullName();
        StringBuilder returnString = new StringBuilder(); // use of built in StringBuilder function

        for(ServiceRequest request : pendingRequests)
        {
            if (providerName.equals(request.providerName))
            {
                returnString.append(request.getInfo()).append('\n');
            }
        }
		
		return returnString.toString();
		
	}
	
	
    
}
