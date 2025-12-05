// Terminal Class
/*
This class implements the running terminal functions. It houses the swing gui and 
is responsible for verifying login and various operations.
Rewritten by Wheeler Knight on 12/5/2025 - Added all missing features per requirements
 */

package chocan;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.time.LocalDate;

public class Terminal extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	DataCenter sys;
	ACMEAccountingServices accounting;
	
	// Screens
	JPanel loginScreen, memberScreen, managerScreen, providerScreen, operatorScreen;
	JPanel serviceRequestScreen, requestApprovalScreen;
	
	// Current logged in user
	Member currMember;
	Provider currProvider;
	Manager currManager;
	Operator currOperator;

	public Terminal() {
		
		sys = new DataCenter();
		accounting = new ACMEAccountingServices(sys.getMembers(), sys.getSuspendedMembers());
		
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setTitle("ChocAn Terminal System");

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showConfirmDialog(Terminal.this, 
                    "Save data before exiting?", "Exit", JOptionPane.YES_NO_CANCEL_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    sys.saveInfo();
                    System.exit(0);
                } else if (confirm == JOptionPane.NO_OPTION) {
                    System.exit(0);
                }
                // Cancel does nothing
            }
        });
        
        setupLoginScreen();
        setupMemberScreen();
        setupProviderScreen();
        setupManagerScreen();
        setupOperatorScreen();
        
        this.add(loginScreen);
        this.setSize(500, 500);
        this.setVisible(true);
        this.setResizable(false);
	}
	
	// ==================== LOGIN SCREEN ====================
	
	private void setupLoginScreen() {
		loginScreen = new JPanel();
        loginScreen.setLayout(null);
        
        JLabel titleLabel = new JLabel("ChocAn Terminal System");
        titleLabel.setBounds(140, 20, 220, 30);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        JLabel subtitleLabel = new JLabel("Please login to continue");
        subtitleLabel.setBounds(160, 50, 180, 20);
        
        JLabel nameLabel = new JLabel("Full Name:");
        nameLabel.setBounds(60, 100, 100, 20);
        
        JTextField nameField = new JTextField();
        nameField.setBounds(160, 100, 200, 25);
        
        JLabel numberLabel = new JLabel("User Number:");
        numberLabel.setBounds(60, 135, 100, 20);
        
        JTextField userNumberField = new JTextField();
        userNumberField.setBounds(160, 135, 200, 25);
        
        JLabel pinLabel = new JLabel("PIN/Password:");
        pinLabel.setBounds(60, 170, 100, 20);
        
        JPasswordField pinField = new JPasswordField();
        pinField.setBounds(160, 170, 200, 25);
        
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(160, 210, 200, 30);
        
        loginButton.addActionListener(e -> {
        	String name = nameField.getText().trim();
        	String number = userNumberField.getText().trim();
        	String pin = new String(pinField.getPassword()).trim();
        	
        	if (name.isEmpty() || number.isEmpty()) {
        		JOptionPane.showMessageDialog(Terminal.this, "Please enter your name and number", 
        			"Validation Error", JOptionPane.WARNING_MESSAGE);
        		return;
        	}
        	
        	int loginResult = verifyLoginWithPassword(name, number, pin);
        	
        	switch (loginResult) {
            case 1: // Member
            	currMember = getMember(name, number);
            	nameField.setText("");
            	userNumberField.setText("");
            	pinField.setText("");
            	swapInterface(memberScreen);
                break;
            case 2: // Provider
            	currProvider = getProvider(name, number);
            	nameField.setText("");
            	userNumberField.setText("");
            	pinField.setText("");
            	swapInterface(providerScreen);
                break;
            case 3: // Manager
            	currManager = getManager(name, number);
            	nameField.setText("");
            	userNumberField.setText("");
            	pinField.setText("");
            	swapInterface(managerScreen);
                break;
            case 4: // Operator
            	currOperator = getOperator(name, number);
            	nameField.setText("");
            	userNumberField.setText("");
            	pinField.setText("");
            	swapInterface(operatorScreen);
                break;
            default:
            	JOptionPane.showMessageDialog(Terminal.this, 
            		"Invalid Login. Please check your credentials.", 
            		"Login Failed", JOptionPane.WARNING_MESSAGE);
        	}
        });
        
        // Main Accounting Procedure button (accessible without login for demo)
        JButton mainAccountingButton = new JButton("Run Main Accounting Procedure");
        mainAccountingButton.setBounds(100, 280, 280, 30);
        mainAccountingButton.addActionListener(e -> runMainAccountingProcedure());
        
        JLabel accountingNote = new JLabel("(Generates Summary, Member, and Provider Reports)");
        accountingNote.setBounds(100, 310, 300, 20);
        accountingNote.setFont(new Font("Arial", Font.ITALIC, 10));
       
        loginScreen.add(titleLabel);
        loginScreen.add(subtitleLabel);
        loginScreen.add(nameLabel);
        loginScreen.add(nameField);
        loginScreen.add(numberLabel);
        loginScreen.add(userNumberField);
        loginScreen.add(pinLabel);
        loginScreen.add(pinField);
        loginScreen.add(loginButton);
        loginScreen.add(mainAccountingButton);
        loginScreen.add(accountingNote);
	}
	
	// ==================== MEMBER SCREEN ====================
	
	private void setupMemberScreen() {
		memberScreen = new JPanel();
		memberScreen.setLayout(null);
        
        JLabel titleLabel = new JLabel("Member Portal");
        titleLabel.setBounds(180, 20, 150, 30);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        JButton requestServiceButton = new JButton("Request Health Service");
        requestServiceButton.setBounds(130, 80, 220, 35);
        requestServiceButton.addActionListener(e -> showServiceRequestDialog());
        
        JButton viewServicesButton = new JButton("View My Services");
        viewServicesButton.setBounds(130, 130, 220, 35);
        viewServicesButton.addActionListener(e -> viewMemberServices());
        
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(130, 200, 220, 35);
        logoutButton.addActionListener(e -> {
        	currMember = null;
        	swapInterface(loginScreen);
        });
        
        memberScreen.add(titleLabel);
        memberScreen.add(requestServiceButton);
        memberScreen.add(viewServicesButton);
        memberScreen.add(logoutButton);
	}
	
	// ==================== PROVIDER SCREEN ====================
	
	private void setupProviderScreen() {
		providerScreen = new JPanel();
		providerScreen.setLayout(null);
        
        JLabel titleLabel = new JLabel("Provider Portal");
        titleLabel.setBounds(175, 20, 150, 30);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        JButton approveRequestsButton = new JButton("View/Approve Service Requests");
        approveRequestsButton.setBounds(100, 70, 280, 35);
        approveRequestsButton.addActionListener(e -> showApprovalDialog());
        
        JButton addServiceRecordButton = new JButton("Add Service Record (Bill ChocAn)");
        addServiceRecordButton.setBounds(100, 115, 280, 35);
        addServiceRecordButton.addActionListener(e -> showAddServiceRecordDialog());
        
        JButton viewDirectoryButton = new JButton("Request Provider Directory");
        viewDirectoryButton.setBounds(100, 160, 280, 35);
        viewDirectoryButton.addActionListener(e -> showProviderDirectory());
        
        JButton viewMyReportButton = new JButton("View My Weekly Report");
        viewMyReportButton.setBounds(100, 205, 280, 35);
        viewMyReportButton.addActionListener(e -> {
        	if (currProvider != null) {
        		ProviderReport.printProviderReport(currProvider, sys);
        		JOptionPane.showMessageDialog(this, "Provider report printed to console.", 
        			"Report Generated", JOptionPane.INFORMATION_MESSAGE);
        	}
        });
        
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(100, 280, 280, 35);
        logoutButton.addActionListener(e -> {
        	currProvider = null;
        	swapInterface(loginScreen);
        });
        
        providerScreen.add(titleLabel);
        providerScreen.add(approveRequestsButton);
        providerScreen.add(addServiceRecordButton);
        providerScreen.add(viewDirectoryButton);
        providerScreen.add(viewMyReportButton);
        providerScreen.add(logoutButton);
	}
	
	// ==================== MANAGER SCREEN ====================
	
	private void setupManagerScreen() {
		managerScreen = new JPanel();
		managerScreen.setLayout(null);
        
        JLabel titleLabel = new JLabel("Manager Portal");
        titleLabel.setBounds(175, 10, 150, 30);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        // Reports section
        JLabel reportsLabel = new JLabel("--- Reports ---");
        reportsLabel.setBounds(195, 45, 100, 20);
        
        JButton summaryReportButton = new JButton("Summary Report");
        summaryReportButton.setBounds(100, 70, 140, 30);
        summaryReportButton.addActionListener(e -> {
        	SummaryReport.printSummaryReport(sys);
        	JOptionPane.showMessageDialog(this, "Summary Report printed to console.", 
        		"Report", JOptionPane.INFORMATION_MESSAGE);
        });
        
        JButton memberReportButton = new JButton("Member Reports");
        memberReportButton.setBounds(250, 70, 140, 30);
        memberReportButton.addActionListener(e -> {
        	printAllMemberReports();
        	JOptionPane.showMessageDialog(this, "Member Reports printed to console.", 
        		"Report", JOptionPane.INFORMATION_MESSAGE);
        });
        
        JButton providerReportButton = new JButton("Provider Reports");
        providerReportButton.setBounds(100, 110, 140, 30);
        providerReportButton.addActionListener(e -> {
        	ProviderReport.printAllProviderReports(sys);
        	JOptionPane.showMessageDialog(this, "Provider Reports printed to console.", 
        		"Report", JOptionPane.INFORMATION_MESSAGE);
        });
        
        JButton exportReportsButton = new JButton("Export All Reports");
        exportReportsButton.setBounds(250, 110, 140, 30);
        exportReportsButton.addActionListener(e -> {
        	exportAllReportsToFile();
        	JOptionPane.showMessageDialog(this, "Reports exported to reports/ folder.", 
        		"Export Complete", JOptionPane.INFORMATION_MESSAGE);
        });
        
        // View section
        JLabel viewLabel = new JLabel("--- View Data ---");
        viewLabel.setBounds(185, 150, 120, 20);
        
        JButton viewMembersButton = new JButton("View All Members");
        viewMembersButton.setBounds(100, 175, 140, 30);
        viewMembersButton.addActionListener(e -> showAllMembers());
        
        JButton viewProvidersButton = new JButton("View All Providers");
        viewProvidersButton.setBounds(250, 175, 140, 30);
        viewProvidersButton.addActionListener(e -> showAllProviders());
        
        JButton viewSuspendedButton = new JButton("View Suspended");
        viewSuspendedButton.setBounds(175, 215, 140, 30);
        viewSuspendedButton.addActionListener(e -> showSuspendedMembers());
        
        // Add section (Manager can also add)
        JLabel addLabel = new JLabel("--- Add New ---");
        addLabel.setBounds(190, 255, 110, 20);
        
        JButton addMemberButton = new JButton("Add Member");
        addMemberButton.setBounds(100, 280, 140, 30);
        addMemberButton.addActionListener(e -> showAddMemberDialog());
        
        JButton addProviderButton = new JButton("Add Provider");
        addProviderButton.setBounds(250, 280, 140, 30);
        addProviderButton.addActionListener(e -> showAddProviderDialog());
        
        // EFT
        JButton eftButton = new JButton("Generate EFT Data");
        eftButton.setBounds(175, 320, 140, 30);
        eftButton.addActionListener(e -> {
        	generateEFTData();
        	JOptionPane.showMessageDialog(this, "EFT data generated to eft_data.txt", 
        		"EFT Generated", JOptionPane.INFORMATION_MESSAGE);
        });
        
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(175, 380, 140, 30);
        logoutButton.addActionListener(e -> {
        	currManager = null;
        	swapInterface(loginScreen);
        });
        
        managerScreen.add(titleLabel);
        managerScreen.add(reportsLabel);
        managerScreen.add(summaryReportButton);
        managerScreen.add(memberReportButton);
        managerScreen.add(providerReportButton);
        managerScreen.add(exportReportsButton);
        managerScreen.add(viewLabel);
        managerScreen.add(viewMembersButton);
        managerScreen.add(viewProvidersButton);
        managerScreen.add(viewSuspendedButton);
        managerScreen.add(addLabel);
        managerScreen.add(addMemberButton);
        managerScreen.add(addProviderButton);
        managerScreen.add(eftButton);
        managerScreen.add(logoutButton);
	}
	
	// ==================== OPERATOR SCREEN ====================
	
	private void setupOperatorScreen() {
		operatorScreen = new JPanel();
		operatorScreen.setLayout(null);
        
        JLabel titleLabel = new JLabel("Operator Portal");
        titleLabel.setBounds(175, 10, 150, 30);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        // Member Management
        JLabel memberLabel = new JLabel("--- Member Management ---");
        memberLabel.setBounds(155, 45, 180, 20);
        
        JButton addMemberButton = new JButton("Add Member");
        addMemberButton.setBounds(50, 70, 120, 30);
        addMemberButton.addActionListener(e -> showAddMemberDialog());
        
        JButton editMemberButton = new JButton("Edit Member");
        editMemberButton.setBounds(180, 70, 120, 30);
        editMemberButton.addActionListener(e -> showEditMemberDialog());
        
        JButton deleteMemberButton = new JButton("Delete Member");
        deleteMemberButton.setBounds(310, 70, 130, 30);
        deleteMemberButton.addActionListener(e -> showDeleteMemberDialog());
        
        JButton suspendMemberButton = new JButton("Suspend Member");
        suspendMemberButton.setBounds(100, 110, 140, 30);
        suspendMemberButton.addActionListener(e -> showSuspendMemberDialog());
        
        JButton unsuspendMemberButton = new JButton("Unsuspend Member");
        unsuspendMemberButton.setBounds(250, 110, 150, 30);
        unsuspendMemberButton.addActionListener(e -> showUnsuspendMemberDialog());
        
        // Provider Management
        JLabel providerLabel = new JLabel("--- Provider Management ---");
        providerLabel.setBounds(150, 160, 190, 20);
        
        JButton addProviderButton = new JButton("Add Provider");
        addProviderButton.setBounds(50, 185, 120, 30);
        addProviderButton.addActionListener(e -> showAddProviderDialog());
        
        JButton editProviderButton = new JButton("Edit Provider");
        editProviderButton.setBounds(180, 185, 120, 30);
        editProviderButton.addActionListener(e -> showEditProviderDialog());
        
        JButton deleteProviderButton = new JButton("Delete Provider");
        deleteProviderButton.setBounds(310, 185, 130, 30);
        deleteProviderButton.addActionListener(e -> showDeleteProviderDialog());
        
        // View
        JLabel viewLabel = new JLabel("--- View ---");
        viewLabel.setBounds(200, 235, 100, 20);
        
        JButton viewMembersButton = new JButton("View All Members");
        viewMembersButton.setBounds(100, 260, 140, 30);
        viewMembersButton.addActionListener(e -> showAllMembers());
        
        JButton viewProvidersButton = new JButton("View All Providers");
        viewProvidersButton.setBounds(250, 260, 140, 30);
        viewProvidersButton.addActionListener(e -> showAllProviders());
        
        JButton viewSuspendedButton = new JButton("View Suspended Members");
        viewSuspendedButton.setBounds(140, 300, 200, 30);
        viewSuspendedButton.addActionListener(e -> showSuspendedMembers());
        
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(175, 380, 140, 30);
        logoutButton.addActionListener(e -> {
        	currOperator = null;
        	swapInterface(loginScreen);
        });
        
        operatorScreen.add(titleLabel);
        operatorScreen.add(memberLabel);
        operatorScreen.add(addMemberButton);
        operatorScreen.add(editMemberButton);
        operatorScreen.add(deleteMemberButton);
        operatorScreen.add(suspendMemberButton);
        operatorScreen.add(unsuspendMemberButton);
        operatorScreen.add(providerLabel);
        operatorScreen.add(addProviderButton);
        operatorScreen.add(editProviderButton);
        operatorScreen.add(deleteProviderButton);
        operatorScreen.add(viewLabel);
        operatorScreen.add(viewMembersButton);
        operatorScreen.add(viewProvidersButton);
        operatorScreen.add(viewSuspendedButton);
        operatorScreen.add(logoutButton);
	}
	
	// ==================== DIALOG METHODS ====================
	
	private void showServiceRequestDialog() {
		if (currMember == null) return;
		
		String[] serviceOptions = sys.SERVICE_NAMES;
		Provider[] providers = sys.getProviders();
		String[] providerNames = new String[providers.length];
		for (int i = 0; i < providers.length; i++) {
			providerNames[i] = providers[i].getFullName();
		}
		
		JComboBox<String> serviceBox = new JComboBox<>(serviceOptions);
		JComboBox<String> providerBox = new JComboBox<>(providerNames);
		
		Object[] fields = {
			"Select Service:", serviceBox,
			"Select Provider:", providerBox
		};
		
		int result = JOptionPane.showConfirmDialog(this, fields, "Request Service", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			sys.addPendingServiceRequest(currMember, (String) providerBox.getSelectedItem(), (String) serviceBox.getSelectedItem());
			JOptionPane.showMessageDialog(this, "Service request submitted!", "Success", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	private void viewMemberServices() {
		if (currMember == null) return;
		
		java.util.List<ServiceRecord> records = sys.getServiceRecordsForMember(currMember.getCard().getMemberNumber());
		
		StringBuilder sb = new StringBuilder();
		sb.append("=== Your Service History ===\n\n");
		
		if (records.isEmpty()) {
			sb.append("No services on record.");
		} else {
			double total = 0;
			for (ServiceRecord sr : records) {
				String serviceName = "Unknown";
				try {
					int code = Integer.parseInt(sr.getServiceCode());
					if (code >= 1 && code <= sys.SERVICE_NAMES.length) {
						serviceName = sys.SERVICE_NAMES[code - 1];
					}
				} catch (Exception e) {}
				
				sb.append(String.format("Date: %s\n", sr.getServiceDate()));
				sb.append(String.format("Service: %s\n", serviceName));
				sb.append(String.format("Fee: $%.2f\n\n", sr.getServiceFee()));
				total += sr.getServiceFee();
			}
			sb.append(String.format("Total Fees: $%.2f", total));
		}
		
		JOptionPane.showMessageDialog(this, sb.toString(), "Service History", JOptionPane.INFORMATION_MESSAGE);
	}
	
	private void showApprovalDialog() {
		ServiceRequest[] requests = sys.getPendingServiceRequest();
		
		if (requests == null || requests.length == 0) {
			JOptionPane.showMessageDialog(this, "No pending service requests.", "Info", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		
		// Find requests for current provider
		StringBuilder sb = new StringBuilder();
		sb.append("Pending Requests for you:\n\n");
		int count = 0;
		int firstIndex = -1;
		
		for (int i = 0; i < requests.length; i++) {
			ServiceRequest req = requests[i];
			if (req != null && currProvider != null && req.providerName.equals(currProvider.getFullName())) {
				if (firstIndex == -1) firstIndex = i;
				sb.append(String.format("%d. Member: %s, Service: %s\n", 
					count + 1, req.member.getFullName(), req.serviceType));
				count++;
			}
		}
		
		if (count == 0) {
			JOptionPane.showMessageDialog(this, "No pending requests for you.", "Info", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		
		String[] options = {"Approve First", "Decline First", "Cancel"};
		int choice = JOptionPane.showOptionDialog(this, sb.toString(), "Service Requests",
			JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		
		if (choice == 0 && firstIndex >= 0) {
			// Approve
			ServiceRequest req = requests[firstIndex];
			String serviceCode = "1";
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
				LocalDate.now()
			);
			sys.addServiceRecord(record);
			sys.removePendingServiceRequest(firstIndex);
			JOptionPane.showMessageDialog(this, "Service approved and recorded!", "Approved", JOptionPane.INFORMATION_MESSAGE);
		} else if (choice == 1 && firstIndex >= 0) {
			// Decline
			sys.removePendingServiceRequest(firstIndex);
			JOptionPane.showMessageDialog(this, "Service request declined.", "Declined", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	private void showAddServiceRecordDialog() {
		if (currProvider == null) return;
		
		JTextField memberNumField = new JTextField();
		String[] serviceOptions = sys.SERVICE_NAMES;
		JComboBox<String> serviceBox = new JComboBox<>(serviceOptions);
		JTextField dateField = new JTextField(LocalDate.now().toString());
		JTextField commentsField = new JTextField();
		
		Object[] fields = {
			"Member Number:", memberNumField,
			"Service:", serviceBox,
			"Date (YYYY-MM-DD):", dateField,
			"Comments (optional):", commentsField
		};
		
		int result = JOptionPane.showConfirmDialog(this, fields, "Add Service Record", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			String memberNum = memberNumField.getText().trim();
			
			// Validate member
			String validation = sys.validateMemberCard(memberNum);
			if (validation.equals("INVALID")) {
				JOptionPane.showMessageDialog(this, "INVALID: Member number not found!", 
					"Validation Failed", JOptionPane.ERROR_MESSAGE);
				return;
			} else if (validation.equals("SUSPENDED")) {
				JOptionPane.showMessageDialog(this, "SUSPENDED: Member account is suspended!", 
					"Validation Failed", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			// Member is VALID
			JOptionPane.showMessageDialog(this, "VALIDATED: Member is active!", 
				"Validation Success", JOptionPane.INFORMATION_MESSAGE);
			
			int serviceIndex = serviceBox.getSelectedIndex();
			String serviceCode = String.valueOf(serviceIndex + 1);
			double fee = sys.SERVICE_FEE_RATES[serviceIndex];
			
			LocalDate serviceDate;
			try {
				serviceDate = LocalDate.parse(dateField.getText().trim());
			} catch (Exception e) {
				serviceDate = LocalDate.now();
			}
			
			// Show fee confirmation
			int confirm = JOptionPane.showConfirmDialog(this, 
				String.format("Service: %s\nFee: $%.2f\n\nConfirm billing?", 
					sys.SERVICE_NAMES[serviceIndex], fee),
				"Confirm Service", JOptionPane.YES_NO_OPTION);
			
			if (confirm == JOptionPane.YES_OPTION) {
				ServiceRecord record = new ServiceRecord(
					currProvider.getProviderNumber(),
					memberNum,
					serviceCode,
					fee,
					serviceDate
				);
				sys.addServiceRecord(record);
				JOptionPane.showMessageDialog(this, "Service record added successfully!", 
					"Success", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	
	private void showProviderDirectory() {
		ProviderDirectory directory = new ProviderDirectory(sys);
		directory.displayProviderDirectory();
		directory.saveProviderDirectoryToFile("provider_directory.txt");
		
		StringBuilder sb = new StringBuilder();
		sb.append("=== Provider Directory ===\n\n");
		sb.append(String.format("%-20s %-10s %-10s\n", "Service", "Code", "Fee"));
		sb.append("----------------------------------------\n");
		for (int i = 0; i < sys.SERVICE_NAMES.length; i++) {
			sb.append(String.format("%-20s %-10s $%-9.2f\n", 
				sys.SERVICE_NAMES[i], String.format("%06d", i + 1), sys.SERVICE_FEE_RATES[i]));
		}
		sb.append("\n(Also saved to provider_directory.txt)");
		
		JOptionPane.showMessageDialog(this, sb.toString(), "Provider Directory", JOptionPane.INFORMATION_MESSAGE);
	}
	
	private void showAddMemberDialog() {
		JTextField firstNameField = new JTextField();
		JTextField lastNameField = new JTextField();
		JTextField phoneField = new JTextField();
		JTextField addressField = new JTextField();
		JTextField cityField = new JTextField();
		JTextField stateField = new JTextField();
		JTextField zipField = new JTextField();
		JTextField emailField = new JTextField();
		JTextField numberField = new JTextField();
		JTextField pinField = new JTextField("1234");
		
		Object[] fields = {
			"First Name:", firstNameField,
			"Last Name:", lastNameField,
			"Phone:", phoneField,
			"Address:", addressField,
			"City:", cityField,
			"State:", stateField,
			"Zip Code:", zipField,
			"Email:", emailField,
			"Member Number:", numberField,
			"PIN:", pinField
		};
		
		int result = JOptionPane.showConfirmDialog(this, fields, "Add New Member", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			String number = numberField.getText().trim();
			if (sys.memberNumberExists(number)) {
				JOptionPane.showMessageDialog(this, "Member number already exists!", 
					"Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			sys.addMember(
				firstNameField.getText().trim(),
				lastNameField.getText().trim(),
				phoneField.getText().trim(),
				addressField.getText().trim(),
				cityField.getText().trim(),
				stateField.getText().trim(),
				zipField.getText().trim(),
				emailField.getText().trim(),
				number
			);
			
			// Update the member's PIN
			Member newMember = sys.getMemberByNumber(number);
			if (newMember != null) {
				newMember.setPin(pinField.getText().trim());
			}
			
			JOptionPane.showMessageDialog(this, "Member added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	private void showEditMemberDialog() {
		String memberNum = JOptionPane.showInputDialog(this, "Enter member number to edit:");
		if (memberNum == null || memberNum.trim().isEmpty()) return;
		
		Member member = sys.getMemberByNumber(memberNum.trim());
		if (member == null) {
			JOptionPane.showMessageDialog(this, "Member not found!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		JTextField firstNameField = new JTextField(member.getFirstName());
		JTextField lastNameField = new JTextField(member.getLastName());
		JTextField phoneField = new JTextField(member.getPhoneNumber());
		JTextField addressField = new JTextField(member.getAddress());
		JTextField cityField = new JTextField(member.getCity());
		JTextField stateField = new JTextField(member.getState());
		JTextField zipField = new JTextField(member.getZipCode());
		JTextField emailField = new JTextField(member.getEmail());
		
		Object[] fields = {
			"First Name:", firstNameField,
			"Last Name:", lastNameField,
			"Phone:", phoneField,
			"Address:", addressField,
			"City:", cityField,
			"State:", stateField,
			"Zip Code:", zipField,
			"Email:", emailField
		};
		
		int result = JOptionPane.showConfirmDialog(this, fields, "Edit Member: " + member.getFullName(), JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			sys.editMember(
				memberNum.trim(),
				firstNameField.getText().trim(),
				lastNameField.getText().trim(),
				phoneField.getText().trim(),
				addressField.getText().trim(),
				cityField.getText().trim(),
				stateField.getText().trim(),
				zipField.getText().trim(),
				emailField.getText().trim()
			);
			JOptionPane.showMessageDialog(this, "Member updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	private void showDeleteMemberDialog() {
		String memberNum = JOptionPane.showInputDialog(this, "Enter member number to delete:");
		if (memberNum == null || memberNum.trim().isEmpty()) return;
		
		Member member = sys.getMemberByNumber(memberNum.trim());
		if (member == null) {
			JOptionPane.showMessageDialog(this, "Member not found!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		int confirm = JOptionPane.showConfirmDialog(this, 
			"Are you sure you want to delete member:\n" + member.getFullName() + " (" + memberNum + ")?",
			"Confirm Delete", JOptionPane.YES_NO_OPTION);
		
		if (confirm == JOptionPane.YES_OPTION) {
			sys.deleteMember(memberNum.trim());
			JOptionPane.showMessageDialog(this, "Member deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	private void showSuspendMemberDialog() {
		String memberNum = JOptionPane.showInputDialog(this, "Enter member number to suspend:");
		if (memberNum == null || memberNum.trim().isEmpty()) return;
		
		if (sys.suspendMember(memberNum.trim())) {
			JOptionPane.showMessageDialog(this, "Member suspended successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(this, "Member not found!", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void showUnsuspendMemberDialog() {
		String memberNum = JOptionPane.showInputDialog(this, "Enter member number to unsuspend:");
		if (memberNum == null || memberNum.trim().isEmpty()) return;
		
		if (sys.unsuspendMember(memberNum.trim())) {
			JOptionPane.showMessageDialog(this, "Member unsuspended successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(this, "Suspended member not found!", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void showAddProviderDialog() {
		JTextField firstNameField = new JTextField();
		JTextField lastNameField = new JTextField();
		JTextField phoneField = new JTextField();
		JTextField addressField = new JTextField();
		JTextField cityField = new JTextField();
		JTextField stateField = new JTextField();
		JTextField zipField = new JTextField();
		JTextField numberField = new JTextField();
		JTextField passwordField = new JTextField("provider");
		
		Object[] fields = {
			"First Name:", firstNameField,
			"Last Name:", lastNameField,
			"Phone:", phoneField,
			"Address:", addressField,
			"City:", cityField,
			"State:", stateField,
			"Zip Code:", zipField,
			"Provider Number:", numberField,
			"Password:", passwordField
		};
		
		int result = JOptionPane.showConfirmDialog(this, fields, "Add New Provider", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			String number = numberField.getText().trim();
			if (sys.providerNumberExists(number)) {
				JOptionPane.showMessageDialog(this, "Provider number already exists!", 
					"Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			sys.addProvider(
				firstNameField.getText().trim(),
				lastNameField.getText().trim(),
				phoneField.getText().trim(),
				addressField.getText().trim(),
				cityField.getText().trim(),
				stateField.getText().trim(),
				zipField.getText().trim(),
				number
			);
			
			// Set password
			Provider newProvider = sys.getProviderByNumber(number);
			if (newProvider != null) {
				newProvider.setPassword(passwordField.getText().trim());
			}
			
			JOptionPane.showMessageDialog(this, "Provider added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	private void showEditProviderDialog() {
		String providerNum = JOptionPane.showInputDialog(this, "Enter provider number to edit:");
		if (providerNum == null || providerNum.trim().isEmpty()) return;
		
		Provider provider = sys.getProviderByNumber(providerNum.trim());
		if (provider == null) {
			JOptionPane.showMessageDialog(this, "Provider not found!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		JTextField firstNameField = new JTextField(provider.getFirstName());
		JTextField lastNameField = new JTextField(provider.getLastName());
		JTextField phoneField = new JTextField(provider.getPhoneNumber());
		JTextField addressField = new JTextField(provider.getAddress());
		JTextField cityField = new JTextField(provider.getCity());
		JTextField stateField = new JTextField(provider.getState());
		JTextField zipField = new JTextField(provider.getZipCode());
		
		Object[] fields = {
			"First Name:", firstNameField,
			"Last Name:", lastNameField,
			"Phone:", phoneField,
			"Address:", addressField,
			"City:", cityField,
			"State:", stateField,
			"Zip Code:", zipField
		};
		
		int result = JOptionPane.showConfirmDialog(this, fields, "Edit Provider: " + provider.getFullName(), JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			sys.editProvider(
				providerNum.trim(),
				firstNameField.getText().trim(),
				lastNameField.getText().trim(),
				phoneField.getText().trim(),
				addressField.getText().trim(),
				cityField.getText().trim(),
				stateField.getText().trim(),
				zipField.getText().trim()
			);
			JOptionPane.showMessageDialog(this, "Provider updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	private void showDeleteProviderDialog() {
		String providerNum = JOptionPane.showInputDialog(this, "Enter provider number to delete:");
		if (providerNum == null || providerNum.trim().isEmpty()) return;
		
		Provider provider = sys.getProviderByNumber(providerNum.trim());
		if (provider == null) {
			JOptionPane.showMessageDialog(this, "Provider not found!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		int confirm = JOptionPane.showConfirmDialog(this, 
			"Are you sure you want to delete provider:\n" + provider.getFullName() + " (" + providerNum + ")?",
			"Confirm Delete", JOptionPane.YES_NO_OPTION);
		
		if (confirm == JOptionPane.YES_OPTION) {
			sys.deleteProvider(providerNum.trim());
			JOptionPane.showMessageDialog(this, "Provider deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	private void showAllMembers() {
		StringBuilder sb = new StringBuilder();
		sb.append("=== All Active Members ===\n\n");
		for (Member m : sys.getMembers()) {
			if (m != null) {
				sb.append(String.format("%s - #%s - %s\n", 
					m.getFullName(), m.getCard().getMemberNumber(), m.getEmail()));
			}
		}
		JOptionPane.showMessageDialog(this, sb.toString(), "All Members", JOptionPane.INFORMATION_MESSAGE);
	}
	
	private void showAllProviders() {
		StringBuilder sb = new StringBuilder();
		sb.append("=== All Providers ===\n\n");
		for (Provider p : sys.getProviders()) {
			if (p != null) {
				sb.append(String.format("%s - #%s\n", p.getFullName(), p.getProviderNumber()));
			}
		}
		JOptionPane.showMessageDialog(this, sb.toString(), "All Providers", JOptionPane.INFORMATION_MESSAGE);
	}
	
	private void showSuspendedMembers() {
		StringBuilder sb = new StringBuilder();
		sb.append("=== Suspended Members ===\n\n");
		Member[] suspended = sys.getSuspendedMembers();
		if (suspended.length == 0) {
			sb.append("No suspended members.");
		} else {
			for (Member m : suspended) {
				if (m != null) {
					sb.append(String.format("%s - #%s\n", m.getFullName(), m.getCard().getMemberNumber()));
				}
			}
		}
		JOptionPane.showMessageDialog(this, sb.toString(), "Suspended Members", JOptionPane.INFORMATION_MESSAGE);
	}
	
	// ==================== MAIN ACCOUNTING PROCEDURE ====================
	
	private void runMainAccountingProcedure() {
		System.out.println("\n");
		System.out.println("********************************************************");
		System.out.println("*        MAIN ACCOUNTING PROCEDURE                     *");
		System.out.println("*        Running Weekly Reports...                     *");
		System.out.println("********************************************************");
		System.out.println();
		
		// Summary Report
		System.out.println("\n=== SUMMARY REPORT ===\n");
		SummaryReport.printSummaryReport(sys);
		
		// Member Reports
		System.out.println("\n=== MEMBER SERVICE REPORTS ===\n");
		printAllMemberReports();
		
		// Provider Reports
		System.out.println("\n=== PROVIDER REPORTS ===\n");
		ProviderReport.printAllProviderReports(sys);
		
		// Export to files
		exportAllReportsToFile();
		
		// Generate EFT
		generateEFTData();
		
		System.out.println("\n********************************************************");
		System.out.println("*        ACCOUNTING PROCEDURE COMPLETE                 *");
		System.out.println("********************************************************\n");
		
		JOptionPane.showMessageDialog(this, 
			"Main Accounting Procedure Complete!\n\n" +
			"Generated:\n" +
			"- Summary Report\n" +
			"- Member Service Reports\n" +
			"- Provider Reports\n" +
			"- EFT Data\n\n" +
			"Check console output and reports/ folder.",
			"Accounting Complete", JOptionPane.INFORMATION_MESSAGE);
	}
	
	private void printAllMemberReports() {
		java.util.List<ServiceRecord> weeklyRecords = sys.getServiceRecordsForLastWeek();
		for (Member m : sys.getMembers()) {
			if (m != null) {
				MemberServiceReport.ReportGenerator.printMemberReport(m, weeklyRecords, sys);
				System.out.println();
			}
		}
	}
	
	// ==================== LOGIN VERIFICATION ====================
	
	private int verifyLoginWithPassword(String name, String number, String password) {
		// Check Member (uses PIN)
		for (Member m : sys.getMembers()) {
			if (m.getFullName().equals(name) && m.getCard().getMemberNumber().equals(number)) {
				String pin = m.getPin();
				if (pin == null || pin.isEmpty() || pin.equals(password)) {
					return 1;
				}
			}
		}
		
		// Check Provider
		for (Provider p : sys.getProviders()) {
			if (p.getFullName().equals(name) && p.getProviderNumber().equals(number)) {
				String pwd = p.getPassword();
				if (pwd == null || pwd.isEmpty() || pwd.equals(password)) {
					return 2;
				}
			}
		}
		
		// Check Manager
		for (Manager m : sys.getManagers()) {
			if (m.getFullName().equals(name) && m.getManagerNumber().equals(number)) {
				String pwd = m.getPassword();
				if (pwd == null || pwd.isEmpty() || pwd.equals(password)) {
					return 3;
				}
			}
		}
		
		// Check Operator
		for (Operator op : sys.getOperators()) {
			if (op.getFullName().equals(name) && op.getOperatorNumber().equals(number)) {
				String pwd = op.getPassword();
				if (pwd == null || pwd.isEmpty() || pwd.equals(password)) {
					return 4;
				}
			}
		}
		
		return 0;
	}
	
	private Member getMember(String name, String number) {
		for (Member m : sys.getMembers()) {
			if (m.getFullName().equals(name) && m.getCard().getMemberNumber().equals(number)) {
				return m;
			}
		}
		return null;
	}
	
	private Provider getProvider(String name, String number) {
		for (Provider p : sys.getProviders()) {
			if (p.getFullName().equals(name) && p.getProviderNumber().equals(number)) {
				return p;
			}
		}
		return null;
	}
	
	private Manager getManager(String name, String number) {
		for (Manager m : sys.getManagers()) {
			if (m.getFullName().equals(name) && m.getManagerNumber().equals(number)) {
				return m;
			}
		}
		return null;
	}
	
	private Operator getOperator(String name, String number) {
		for (Operator op : sys.getOperators()) {
			if (op.getFullName().equals(name) && op.getOperatorNumber().equals(number)) {
				return op;
			}
		}
		return null;
	}
	
	private void swapInterface(JPanel screen) {
		this.setContentPane(screen);
		revalidate();
        repaint();
	}
	
	// ==================== FILE GENERATION ====================
	
	private void generateEFTData() {
		try {
			java.io.PrintWriter out = new java.io.PrintWriter(new java.io.FileWriter("eft_data.txt"));
			
			out.println("=== EFT Data Report ===");
			out.println("Generated: " + java.time.LocalDateTime.now());
			out.println();
			
			java.util.List<ServiceRecord> records = sys.getServiceRecordsForLastWeek();
			java.util.Map<String, Double> providerFees = new java.util.HashMap<>();
			
			for (ServiceRecord sr : records) {
				String providerNum = sr.getProviderNumber();
				providerFees.put(providerNum, providerFees.getOrDefault(providerNum, 0.0) + sr.getServiceFee());
			}
			
			out.printf("%-20s %-15s %-15s\n", "Provider Name", "Provider #", "Amount Due");
			out.println("--------------------------------------------------");
			
			double total = 0;
			for (String providerNum : providerFees.keySet()) {
				Provider p = sys.getProviderByNumber(providerNum);
				String name = (p != null) ? p.getFullName() : "Unknown";
				double amount = providerFees.get(providerNum);
				out.printf("%-20s %-15s $%-14.2f\n", name, providerNum, amount);
				total += amount;
			}
			
			out.println("--------------------------------------------------");
			out.printf("Total EFT Amount: $%.2f\n", total);
			out.close();
			
			System.out.println("EFT data generated to eft_data.txt");
		} catch (java.io.IOException e) {
			System.err.println("Error generating EFT: " + e.getMessage());
		}
	}
	
	private void exportAllReportsToFile() {
		java.io.File reportsDir = new java.io.File("reports");
		if (!reportsDir.exists()) reportsDir.mkdir();
		
		String timestamp = java.time.LocalDateTime.now().format(
			java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
		
		// Export Summary Report
		try {
			java.io.PrintWriter out = new java.io.PrintWriter(
				new java.io.FileWriter("reports/summary_report_" + timestamp + ".txt"));
			
			out.println("=== Summary Report ===");
			out.println("Generated: " + java.time.LocalDateTime.now());
			out.println();
			
			java.util.List<ServiceRecord> records = sys.getServiceRecordsForLastWeek();
			java.util.Map<String, Integer> providerConsults = new java.util.HashMap<>();
			java.util.Map<String, Double> providerFees = new java.util.HashMap<>();
			
			for (ServiceRecord sr : records) {
				String num = sr.getProviderNumber();
				providerConsults.put(num, providerConsults.getOrDefault(num, 0) + 1);
				providerFees.put(num, providerFees.getOrDefault(num, 0.0) + sr.getServiceFee());
			}
			
			out.printf("%-20s %-15s %-15s %-15s\n", "Provider", "Number", "Consultations", "Total Fee");
			out.println("--------------------------------------------------------------");
			
			int totalConsults = 0;
			double totalFees = 0;
			
			for (String num : providerConsults.keySet()) {
				Provider p = sys.getProviderByNumber(num);
				String name = (p != null) ? p.getFullName() : "Unknown";
				int consults = providerConsults.get(num);
				double fees = providerFees.get(num);
				out.printf("%-20s %-15s %-15d $%-14.2f\n", name, num, consults, fees);
				totalConsults += consults;
				totalFees += fees;
			}
			
			out.println("--------------------------------------------------------------");
			out.println("Total providers: " + providerConsults.size());
			out.println("Total consultations: " + totalConsults);
			out.printf("Total fees: $%.2f\n", totalFees);
			out.close();
		} catch (Exception e) {}
		
		// Export Member Reports
		try {
			java.io.PrintWriter out = new java.io.PrintWriter(
				new java.io.FileWriter("reports/member_reports_" + timestamp + ".txt"));
			
			out.println("=== Member Service Reports ===");
			out.println("Generated: " + java.time.LocalDateTime.now());
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
						String provName = (p != null) ? p.getFullName() : "Unknown";
						String serviceName = "Unknown";
						try {
							int code = Integer.parseInt(sr.getServiceCode());
							if (code >= 1 && code <= sys.SERVICE_NAMES.length) {
								serviceName = sys.SERVICE_NAMES[code - 1];
							}
						} catch (Exception e) {}
						out.printf("  %s - %s - %s - $%.2f\n", 
							sr.getServiceDate(), provName, serviceName, sr.getServiceFee());
						hasServices = true;
					}
				}
				if (!hasServices) out.println("  No services this week");
				out.println();
			}
			out.close();
		} catch (Exception e) {}
		
		// Export Provider Reports
		for (Provider p : sys.getProviders()) {
			if (p != null) {
				ProviderReport.exportProviderReportToFile(p, sys, 
					"reports/provider_" + p.getProviderNumber() + "_" + timestamp + ".txt");
			}
		}
		
		System.out.println("Reports exported to reports/ folder");
	}
}
