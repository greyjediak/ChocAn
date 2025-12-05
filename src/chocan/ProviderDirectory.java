package chocan;

/* TODO:
- make sure that it loads in service data from the data center
- reformat the output for the cli and ui correctly
*/

public class ProviderDirectory {

    private String[] serviceNames;
    private Double[] serviceFees;
    private String[] serviceCodes;
    private int serviceCount;

    // Optionally, you can make a Service inner class, but here we use parallel arrays for simplicity and alignment with DataCenter data

    public ProviderDirectory(DataCenter dataCenter) {
        // Assume that the number of services is the length of SERVICE_NAMES in DataCenter
        this.serviceNames = dataCenter.SERVICE_NAMES;
        this.serviceFees = dataCenter.SERVICE_FEE_RATES;
        // Generate service codes as 6-digit zero-padded numbers (e.g., "000001", "000002", etc.)
        this.serviceCount = serviceNames.length;
        this.serviceCodes = new String[serviceCount];
        for (int i = 0; i < serviceCount; i++) {
            this.serviceCodes[i] = String.format("%06d", i + 1);
        }
    }

    public void displayProviderDirectory() {
        System.out.println("Provider Directory:");
        System.out.printf("%-20s %-10s %-10s\n", "Service Name", "Code", "Fee ($)");
        // Sort and display alphabetically by serviceName
        int[] orderedIndices = getAlphabeticalOrderIndices();
        for (int i = 0; i < serviceCount; i++) {
            int idx = orderedIndices[i];
            System.out.printf("%-20s %-10s $%-10.2f\n", serviceNames[idx], serviceCodes[idx], serviceFees[idx]);
        }
    }

    public void saveProviderDirectoryToFile(String path) {
        try (java.io.FileWriter fw = new java.io.FileWriter(path, false);
             java.io.BufferedWriter bw = new java.io.BufferedWriter(fw);
             java.io.PrintWriter out = new java.io.PrintWriter(bw)) {
            out.printf("%-20s %-10s %-10s\n", "Service Name", "Code", "Fee ($)");
            int[] orderedIndices = getAlphabeticalOrderIndices();
            for (int i = 0; i < serviceCount; i++) {
                int idx = orderedIndices[i];
                out.printf("%-20s %-10s $%-10.2f\n", serviceNames[idx], serviceCodes[idx], serviceFees[idx]);
            }
        } catch (java.io.IOException e) {
            System.err.println("Error writing provider directory to file: " + e.getMessage());
        }
    }

    // Helper: returns indices so that names/codes/fees are sorted alphabetically by name
    private int[] getAlphabeticalOrderIndices() {
        Integer[] indices = new Integer[serviceCount];
        for (int i = 0; i < serviceCount; i++) {
            indices[i] = i;
        }
        java.util.Arrays.sort(indices, (i1, i2) -> serviceNames[i1].compareToIgnoreCase(serviceNames[i2]));
        int[] out = new int[serviceCount];
        for (int i = 0; i < serviceCount; i++) {
            out[i] = indices[i];
        }
        return out;
    }

    // If a provider requests the directory, you can call:
    // directory.displayProviderDirectory();
    // and/or
    // directory.saveProviderDirectoryToFile("src/chocan/provider_directory.txt");


    
}
