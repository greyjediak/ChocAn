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
	JPanel loginScreen, memberScreen, managerScreen, providerScreen, serviceRequestScreen;
	Member currMember;
	Provider currPovider;
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
                	swapInterface(memberScreen);
                    break;
                case 2:
                	swapInterface(providerScreen);
                    break;
                case 3:
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
        
        memberScreen = new JPanel();
        
        //Service request screen set up
        serviceRequestScreen = new JPanel();
        String[] serviceTypeOptions = sys.SERVICENAMES;
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
            	
                swapInterface(memberScreen);
            }
        });
        
        serviceRequestScreen.add(submitServiceRequestButton);
        
        //Service request screen end
        
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                swapInterface(loginScreen);
            }
        });
        
        logoutButton.setBounds(0, 0, 80, 20);
        
        JButton serviceRequestButton = new JButton("Request Service");
        serviceRequestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                swapInterface(serviceRequestScreen);
            }
        });
        serviceRequestButton.setBounds(0, 20, 140, 20);
        memberScreen.add(logoutButton);
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
        providerScreen.add(logoutButton);
        
        
        
        
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
		
		return 2;
	}
	
	/* 
	 * find the specified member by calling (sys.getMembers())
	 * to a Member array (Member[]) and return it 
	*/
	
	private Member getMember(String name, String number) {
		
		return null;
	}
	
	/* 
	 * find the specified provider by calling (sys.getProviders())
	 * to a provider array (Provider[]) and return it 
	*/
	
	private Provider getProvider(String name, String number) {
		
		return null;
	}
	
	/* 
	 * find the specified manager by calling (sys.getManager())
	 * to a manager array (Manager[]) and return it 
	*/

	private Manager getManager(String name, String number) {
	
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
		int count = 0;
		
		return count;
	}
	
	/*
	 * In this function, Call getWeeklyProviderForm from sys.
	 * for every time the member matches, use getInfo on
	 * weeklyProviderForms and append it to return string 
	 * followed by '+ "\n"' to get to next line
	 */
	
	private String accumulateFees(Member member) {
		String returnString = "";
		
		return returnString;
		
	}
	
	
    
}
