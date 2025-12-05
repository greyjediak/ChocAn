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
        // Edited by Wheeler Knight on 12/4/2025 - Added labels and improved login screen
        loginScreen = new JPanel();
        loginScreen.setLayout(null);
        
        JLabel titleLabel = new JLabel("ChocAn Login");
        titleLabel.setBounds(140, 30, 150, 30);
        titleLabel.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 16));
        
        JLabel nameLabel = new JLabel("Full Name:");
        nameLabel.setBounds(60, 80, 80, 20);
        
        JTextField nameField = new JTextField();
        nameField.setBounds(140, 80, 150, 20);
        
        JLabel numberLabel = new JLabel("User Number:");
        numberLabel.setBounds(50, 105, 90, 20);
        
        JTextField userNumberField = new JTextField();
        userNumberField.setBounds(140, 105, 150, 20);
        
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(140, 135, 150, 25);
        
        // Written by Wheeler Knight on 12/4/2025 - Added input validation
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	String name = nameField.getText().trim();
            	String number = userNumberField.getText().trim();
            	
            	// Input validation
            	if (name.isEmpty()) {
            		JOptionPane.showMessageDialog(Terminal.this, "Please enter your name", "Validation Error", JOptionPane.WARNING_MESSAGE);
            		return;
            	}
            	if (number.isEmpty()) {
            		JOptionPane.showMessageDialog(Terminal.this, "Please enter your user number", "Validation Error", JOptionPane.WARNING_MESSAGE);
            		return;
            	}
            	
            	switch (verifyLogin(name, number)) {
                case 1:
                	currMember = getMember(name, number);
                	swapInterface(memberScreen);
                    break;
                case 2:
                	currProvider = getProvider(name, number);
                	swapInterface(providerScreen);
                    break;
                case 3:
                	currManager = getManager(name, number);
                	swapInterface(managerScreen);
                    break;
                default:
                	JOptionPane.showMessageDialog(Terminal.this, "Invalid Login. Please check your name and number.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
            }
        });
       
        loginScreen.add(titleLabel);
        loginScreen.add(nameLabel);
        loginScreen.add(nameField);
        loginScreen.add(numberLabel);
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
        // Edited by Wheeler Knight on 12/4/2025 - Fixed typo: was getFirstName() + getFirstName(), now correctly uses getLastName()
        for(int i = 0; i < providers.length; i++) {
        	providerNames[i] = providers[i].getFirstName() + " " + providers[i].getLastName();
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
        
        // Edited by Wheeler Knight on 12/4/2025 - Fixed NPE: removed fetchServiceReportCount() call from constructor (currMember is null before login)
        JLabel status = new JLabel("No Fees!");
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
        
        // Written by Wheeler Knight on 12/4/2025 - Implemented service approval logic
        JButton approveButton = new JButton("Approve");
        approveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get pending requests for this provider
                ServiceRequest[] requests = sys.getPendingServiceRequest();
                if (requests != null && requests.length > 0 && currProvider != null) {
                    // Find first request for this provider
                    for (int i = 0; i < requests.length; i++) {
                        ServiceRequest req = requests[i];
                        if (req != null && req.providerName.equals(currProvider.getFullName())) {
                            // Create a ServiceRecord for the approved service
                            String serviceCode = "1"; // Default to CONSULTATION
                            for (int j = 0; j < sys.SERVICE_NAMES.length; j++) {
                                if (sys.SERVICE_NAMES[j].equals(req.serviceType)) {
                                    serviceCode = String.valueOf(j + 1);
                                    break;
                                }
                            }
                            double fee = sys.getServiceFeeByCode(Integer.parseInt(serviceCode));
                            ServiceRecord record = new ServiceRecord(
                                currProvider.getProviderNumber(),
                                req.member.getCard().getMemberNumber(),
                                serviceCode,
                                fee,
                                java.time.LocalDate.now()
                            );
                            sys.addServiceRecord(record);
                            sys.removePendingServiceRequest(i);
                            JOptionPane.showMessageDialog(Terminal.this, "Service approved and recorded!", "Approved", JOptionPane.INFORMATION_MESSAGE);
                            break;
                        }
                    }
                }
                swapInterface(providerScreen);
            }
        });
        
        requestApprovalScreen.add(approveButton);
        
        // Written by Wheeler Knight on 12/4/2025 - Implemented service decline logic
        JButton declineButton = new JButton("Decline");
        declineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get pending requests for this provider and remove
                ServiceRequest[] requests = sys.getPendingServiceRequest();
                if (requests != null && requests.length > 0 && currProvider != null) {
                    for (int i = 0; i < requests.length; i++) {
                        ServiceRequest req = requests[i];
                        if (req != null && req.providerName.equals(currProvider.getFullName())) {
                            sys.removePendingServiceRequest(i);
                            JOptionPane.showMessageDialog(Terminal.this, "Service request declined.", "Declined", JOptionPane.INFORMATION_MESSAGE);
                            break;
                        }
                    }
                }
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
        
        // Edited by Wheeler Knight on 12/4/2025 - Fixed NPE: removed fetchServiceRequestCount() call from constructor (currProvider is null before login)
        JLabel pendingServiceRequest = new JLabel("No Request!");
        providerScreen.add(pendingServiceRequest);
        
        
        //Provider screen end
        
        // Written by Wheeler Knight on 12/4/2025 - Implemented Manager Screen
        managerScreen = new JPanel();
        
        JButton managerLogoutButton = new JButton("Logout");
        managerLogoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                swapInterface(loginScreen);
            }
        });
        managerScreen.add(managerLogoutButton);
        
        JButton requestServiceReportButton = new JButton("Request Service Report");
        requestServiceReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currManager != null) {
                    currManager.RequestServiceReport(accounting);
                    JOptionPane.showMessageDialog(Terminal.this, "Service Report generated. Check console output.", "Report Generated", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        managerScreen.add(requestServiceReportButton);
        
        JButton requestSummaryReportButton = new JButton("Request Summary Report");
        requestSummaryReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currManager != null) {
                    currManager.RequestSummaryReport(accounting);
                    JOptionPane.showMessageDialog(Terminal.this, "Summary Report generated. Check console output.", "Report Generated", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        managerScreen.add(requestSummaryReportButton);
        
        JButton viewAllMembersButton = new JButton("View All Members");
        viewAllMembersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder sb = new StringBuilder("=== All Members ===\n");
                for (Member m : sys.getMembers()) {
                    if (m != null) {
                        sb.append(m.getFullName()).append(" - ").append(m.getCard().getMemberNumber()).append("\n");
                    }
                }
                JOptionPane.showMessageDialog(Terminal.this, sb.toString(), "All Members", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        managerScreen.add(viewAllMembersButton);
        
        JButton viewAllProvidersButton = new JButton("View All Providers");
        viewAllProvidersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder sb = new StringBuilder("=== All Providers ===\n");
                for (Provider p : sys.getProviders()) {
                    if (p != null) {
                        sb.append(p.getFullName()).append(" - ").append(p.getProviderNumber()).append("\n");
                    }
                }
                JOptionPane.showMessageDialog(Terminal.this, sb.toString(), "All Providers", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        managerScreen.add(viewAllProvidersButton);
        
        // Written by Wheeler Knight on 12/4/2025 - Add Member UI
        JButton addMemberButton = new JButton("Add New Member");
        addMemberButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create input dialog for new member
                JTextField firstNameField = new JTextField();
                JTextField lastNameField = new JTextField();
                JTextField phoneField = new JTextField();
                JTextField addressField = new JTextField();
                JTextField cityField = new JTextField();
                JTextField stateField = new JTextField();
                JTextField zipField = new JTextField();
                JTextField emailField = new JTextField();
                JTextField numberField = new JTextField();
                
                Object[] fields = {
                    "First Name:", firstNameField,
                    "Last Name:", lastNameField,
                    "Phone:", phoneField,
                    "Address:", addressField,
                    "City:", cityField,
                    "State:", stateField,
                    "Zip Code:", zipField,
                    "Email:", emailField,
                    "Member Number:", numberField
                };
                
                int result = JOptionPane.showConfirmDialog(Terminal.this, fields, "Add New Member", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    sys.addMember(
                        firstNameField.getText().trim(),
                        lastNameField.getText().trim(),
                        phoneField.getText().trim(),
                        addressField.getText().trim(),
                        cityField.getText().trim(),
                        stateField.getText().trim(),
                        zipField.getText().trim(),
                        emailField.getText().trim(),
                        numberField.getText().trim()
                    );
                    JOptionPane.showMessageDialog(Terminal.this, "Member added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        managerScreen.add(addMemberButton);
        
        // Written by Wheeler Knight on 12/4/2025 - Add Provider UI
        JButton addProviderButton = new JButton("Add New Provider");
        addProviderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create input dialog for new provider
                JTextField firstNameField = new JTextField();
                JTextField lastNameField = new JTextField();
                JTextField phoneField = new JTextField();
                JTextField addressField = new JTextField();
                JTextField cityField = new JTextField();
                JTextField stateField = new JTextField();
                JTextField zipField = new JTextField();
                JTextField numberField = new JTextField();
                
                Object[] fields = {
                    "First Name:", firstNameField,
                    "Last Name:", lastNameField,
                    "Phone:", phoneField,
                    "Address:", addressField,
                    "City:", cityField,
                    "State:", stateField,
                    "Zip Code:", zipField,
                    "Provider Number:", numberField
                };
                
                int result = JOptionPane.showConfirmDialog(Terminal.this, fields, "Add New Provider", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    sys.addProvider(
                        firstNameField.getText().trim(),
                        lastNameField.getText().trim(),
                        phoneField.getText().trim(),
                        addressField.getText().trim(),
                        cityField.getText().trim(),
                        stateField.getText().trim(),
                        zipField.getText().trim(),
                        numberField.getText().trim()
                    );
                    JOptionPane.showMessageDialog(Terminal.this, "Provider added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        managerScreen.add(addProviderButton);
        
        // Written by Wheeler Knight on 12/4/2025 - EFT Data Generation
        JButton generateEFTButton = new JButton("Generate EFT Data");
        generateEFTButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateEFTData();
                JOptionPane.showMessageDialog(Terminal.this, "EFT data generated to eft_data.txt", "EFT Generated", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        managerScreen.add(generateEFTButton);
        
        // Written by Wheeler Knight on 12/4/2025 - Export Reports to File
        JButton exportReportsButton = new JButton("Export Reports");
        exportReportsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportReportsToFile();
                JOptionPane.showMessageDialog(Terminal.this, "Reports exported to reports/ folder", "Reports Exported", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        managerScreen.add(exportReportsButton);
        
        //Manager screen end
        
        this.add(memberScreen);
        this.add(providerScreen);
        this.add(managerScreen);
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
        // Edited by Wheeler Knight on 12/4/2025 - Null check still needed for safety
        if (forms == null) return "";
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
        // Edited by Wheeler Knight on 12/4/2025 - Null check still needed for safety
        if (pendingRequests == null) return "";
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
	
	// Written by Wheeler Knight on 12/4/2025 - Generate EFT (Electronic Funds Transfer) data for ACME
	private void generateEFTData() {
		try {
			java.io.FileWriter fw = new java.io.FileWriter("eft_data.txt", false);
			java.io.BufferedWriter bw = new java.io.BufferedWriter(fw);
			java.io.PrintWriter out = new java.io.PrintWriter(bw);
			
			out.println("=== EFT Data Report ===");
			out.println("Generated: " + java.time.LocalDateTime.now().toString());
			out.println();
			
			// Get service records for the last week
			java.util.List<ServiceRecord> records = sys.getServiceRecordsForLastWeek();
			
			// Aggregate fees by provider
			java.util.Map<String, Double> providerFees = new java.util.HashMap<>();
			for (ServiceRecord sr : records) {
				String providerNum = sr.getProviderNumber();
				double fee = sr.getServiceFee();
				providerFees.put(providerNum, providerFees.getOrDefault(providerNum, 0.0) + fee);
			}
			
			// Generate EFT entries
			out.printf("%-20s %-15s %-15s\n", "Provider Name", "Provider #", "Amount Due");
			out.println("--------------------------------------------------");
			
			double totalAmount = 0.0;
			for (String providerNum : providerFees.keySet()) {
				Provider p = sys.getProviderByNumber(providerNum);
				String providerName = (p != null) ? p.getFullName() : "Unknown";
				double amount = providerFees.get(providerNum);
				out.printf("%-20s %-15s $%-14.2f\n", providerName, providerNum, amount);
				totalAmount += amount;
			}
			
			out.println("--------------------------------------------------");
			out.printf("Total EFT Amount: $%.2f\n", totalAmount);
			
			out.close();
			System.out.println("EFT data generated to eft_data.txt");
		} catch (java.io.IOException e) {
			System.err.println("Error generating EFT data: " + e.getMessage());
		}
	}
	
	// Written by Wheeler Knight on 12/4/2025 - Export reports to files with timestamp
	private void exportReportsToFile() {
		// Create reports directory if it doesn't exist
		java.io.File reportsDir = new java.io.File("reports");
		if (!reportsDir.exists()) {
			reportsDir.mkdir();
		}
		
		String timestamp = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
		
		// Export Member Service Report
		try {
			java.io.FileWriter fw = new java.io.FileWriter("reports/member_service_report_" + timestamp + ".txt", false);
			java.io.BufferedWriter bw = new java.io.BufferedWriter(fw);
			java.io.PrintWriter out = new java.io.PrintWriter(bw);
			
			out.println("=== Member Service Report ===");
			out.println("Generated: " + java.time.LocalDateTime.now().toString());
			out.println();
			
			java.util.List<ServiceRecord> records = sys.getServiceRecordsForLastWeek();
			
			for (Member m : sys.getMembers()) {
				if (m == null) continue;
				out.println("Member: " + m.getFullName());
				out.println("Number: " + m.getCard().getMemberNumber());
				out.println("Address: " + m.getAddress() + ", " + m.getCity() + ", " + m.getState() + " " + m.getZipCode());
				out.println("Services:");
				
				boolean hasServices = false;
				for (ServiceRecord sr : records) {
					if (sr.getMemberNumber().equals(m.getCard().getMemberNumber())) {
						Provider p = sys.getProviderByNumber(sr.getProviderNumber());
						String providerName = (p != null) ? p.getFullName() : "Unknown";
						String serviceName = "Unknown";
						try {
							int code = Integer.parseInt(sr.getServiceCode());
							if (code >= 1 && code <= sys.SERVICE_NAMES.length) {
								serviceName = sys.SERVICE_NAMES[code - 1];
							}
						} catch (Exception ignore) {}
						out.printf("  %s - %s - %s - $%.2f\n", 
							sr.getServiceDate(), providerName, serviceName, sr.getServiceFee());
						hasServices = true;
					}
				}
				if (!hasServices) {
					out.println("  No services this week");
				}
				out.println();
			}
			
			out.close();
		} catch (java.io.IOException e) {
			System.err.println("Error exporting member service report: " + e.getMessage());
		}
		
		// Export Summary Report
		try {
			java.io.FileWriter fw = new java.io.FileWriter("reports/summary_report_" + timestamp + ".txt", false);
			java.io.BufferedWriter bw = new java.io.BufferedWriter(fw);
			java.io.PrintWriter out = new java.io.PrintWriter(bw);
			
			out.println("=== Summary Report ===");
			out.println("Generated: " + java.time.LocalDateTime.now().toString());
			out.println();
			
			java.util.List<ServiceRecord> records = sys.getServiceRecordsForLastWeek();
			
			// Aggregate by provider
			java.util.Map<String, Integer> providerConsults = new java.util.HashMap<>();
			java.util.Map<String, Double> providerFees = new java.util.HashMap<>();
			
			for (ServiceRecord sr : records) {
				String providerNum = sr.getProviderNumber();
				providerConsults.put(providerNum, providerConsults.getOrDefault(providerNum, 0) + 1);
				providerFees.put(providerNum, providerFees.getOrDefault(providerNum, 0.0) + sr.getServiceFee());
			}
			
			out.printf("%-20s %-15s %-15s %-15s\n", "Provider Name", "Number", "Consultations", "Total Fee");
			out.println("--------------------------------------------------------------");
			
			int totalConsults = 0;
			double totalFees = 0.0;
			int providersWithService = 0;
			
			for (String providerNum : providerConsults.keySet()) {
				Provider p = sys.getProviderByNumber(providerNum);
				String providerName = (p != null) ? p.getFullName() : "Unknown";
				int consults = providerConsults.get(providerNum);
				double fees = providerFees.get(providerNum);
				out.printf("%-20s %-15s %-15d $%-14.2f\n", providerName, providerNum, consults, fees);
				totalConsults += consults;
				totalFees += fees;
				providersWithService++;
			}
			
			out.println("--------------------------------------------------------------");
			out.println("Total providers: " + providersWithService);
			out.println("Total consultations: " + totalConsults);
			out.printf("Total fees: $%.2f\n", totalFees);
			
			out.close();
		} catch (java.io.IOException e) {
			System.err.println("Error exporting summary report: " + e.getMessage());
		}
		
		System.out.println("Reports exported to reports/ folder");
	}
    
}
