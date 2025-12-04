package chocan;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Vector;

public abstract class readAndWritable {
    
    public abstract void saveInfo() ;
    
    public abstract void writeInfo(String fileName) ;
    
    protected void writeMember(String path, Vector<Member> members) {

        try (FileWriter fw = new FileWriter(path, false);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
        	
        	for(int i = 0; i < members.size(); i++) {

        		out.println(members.get(i).returnInfo());
        	}

        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    protected void writeProvider(String path, Vector<Provider> providers) {

        try (FileWriter fw = new FileWriter(path, false);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
        	
        	for(int i = 0; i < providers.size(); i++) {

        		out.println(providers.get(i).returnInfo());
        	}

        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    protected void writeManager(String path, Vector<Manager> managers) {

        try (FileWriter fw = new FileWriter(path, false);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
        	
        	for(int i = 0; i < managers.size(); i++) {

        		out.println(managers.get(i).returnInfo());
        	}

        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    protected Vector<Member> readMembers(String fileName){
    	
    	Vector<Member> members = new Vector<>();
    	
    	try (InputStream input = readAndWritable.class.getResourceAsStream(fileName)) {
            // Check if the input stream was found
            if (input == null) {
                System.out.println("Error 1: The file was not found in the same package.");
                return null;
            }

            // Use Scanner to read the content line by line
            Scanner scanner = new Scanner(input);
            while (scanner.hasNextLine()) {
            	members.add(readMemberLine(scanner.nextLine()));
            }
            scanner.close();
            
            return members;

        } catch (IOException e) {
            // Handle potential IO exceptions
            e.printStackTrace();
        }
    	
    	return null;
    }
    
    protected Vector<Provider> readProviders(String fileName){
    	
    	Vector<Provider> provider = new Vector<>();
    	
    	try (InputStream input = readAndWritable.class.getResourceAsStream(fileName)) {
            // Check if the input stream was found
            if (input == null) {
                System.out.println("Error 1: The file was not found in the same package.");
                return null;
            }

            // Use Scanner to read the content line by line
            Scanner scanner = new Scanner(input);
            while (scanner.hasNextLine()) {
            	provider.add(readProviderLine(scanner.nextLine()));
            }
            scanner.close();
            
            return provider;

        } catch (IOException e) {
            // Handle potential IO exceptions
            e.printStackTrace();
        }
    	
    	return null;
    }
    
    protected Vector<Manager> readManagers(String fileName){
    	
    	Vector<Manager> manager = new Vector<>();
    	
    	try (InputStream input = readAndWritable.class.getResourceAsStream(fileName)) {
            // Check if the input stream was found
            if (input == null) {
                System.out.println("Error 1: The file was not found in the same package.");
                return null;
            }

            // Use Scanner to read the content line by line
            Scanner scanner = new Scanner(input);
            while (scanner.hasNextLine()) {
            	manager.add(readManagerLine(scanner.nextLine()));
            }
            scanner.close();
            
            return manager;

        } catch (IOException e) {
            // Handle potential IO exceptions
            e.printStackTrace();
        }
    	
    	return null;
    }
    
    protected Member readMemberLine(String line) {
    	
    	String[] parts = line.split("_");
    	
    	if(parts.length == 9){
    		Member returnMember = new Member(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6], parts[7], parts[8]);
    		return returnMember;
    	}
		
		return null;
    	
    }
    
    protected Provider readProviderLine(String line) {
    	
    	String[] parts = line.split("_");
    	
    	if(parts.length == 7){
    		Provider returnProvider = new Provider(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6], parts[7]);
    		return returnProvider;
    	}
		
		return null;
    	
    }
    
    protected Manager readManagerLine(String line) {
    	
    	String[] parts = line.split("_");
    	
    	if(parts.length == 7){
    		Manager returnManager = new Manager(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6], parts[7]);
    		return returnManager;
    	}
		
		return null;
    	
    }

}
