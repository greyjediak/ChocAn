package chocan;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.Vector;

/**
 * Abstract class for JSON-based data persistence using Gson.
 * Refactored by Wheeler Knight on 12/5/2025 - Switched from text files to JSON using Gson.
 * Refactored by Wheeler Knight on 12/5/2025 - Moved data storage to database/ folder.
 */
public abstract class readAndWritable {
    
    // Database folder path - all JSON files stored here
    protected static final String DATABASE_PATH = "database/";
    
    // Gson instance with pretty printing and LocalDate support
    protected static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();
    
    public abstract void saveInfo();
    
    public abstract void writeInfo(String fileName);
    
    // ==================== WRITE METHODS ====================
    
    protected void writeMember(String fileName, Vector<Member> members) {
        writeToJson(DATABASE_PATH + fileName, members);
    }
    
    protected void writeProvider(String fileName, Vector<Provider> providers) {
        writeToJson(DATABASE_PATH + fileName, providers);
    }
    
    protected void writeManager(String fileName, Vector<Manager> managers) {
        writeToJson(DATABASE_PATH + fileName, managers);
    }
    
    protected void writeServiceRecords(String fileName, Vector<ServiceRecord> records) {
        writeToJson(DATABASE_PATH + fileName, records);
    }
    
    protected void writeServiceRequests(String fileName, Vector<ServiceRequest> requests) {
        writeToJson(DATABASE_PATH + fileName, requests);
    }
    
    // Written by Wheeler Knight on 12/5/2025 - Added Operator persistence
    protected void writeOperator(String fileName, Vector<Operator> operators) {
        writeToJson(DATABASE_PATH + fileName, operators);
    }
    
    /**
     * Generic method to write any object to JSON file
     */
    protected <T> void writeToJson(String path, T data) {
        try (FileWriter writer = new FileWriter(path)) {
            gson.toJson(data, writer);
            System.out.println("Data saved to: " + path);
        } catch (IOException e) {
            System.err.println("Error writing JSON to file: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // ==================== READ METHODS ====================
    
    protected Vector<Member> readMembers(String fileName) {
        Type type = new TypeToken<Vector<Member>>(){}.getType();
        Vector<Member> result = readFromJsonFile(DATABASE_PATH + fileName, type);
        return result != null ? result : new Vector<>();
    }
    
    protected Vector<Provider> readProviders(String fileName) {
        Type type = new TypeToken<Vector<Provider>>(){}.getType();
        Vector<Provider> result = readFromJsonFile(DATABASE_PATH + fileName, type);
        return result != null ? result : new Vector<>();
    }
    
    protected Vector<Manager> readManagers(String fileName) {
        Type type = new TypeToken<Vector<Manager>>(){}.getType();
        Vector<Manager> result = readFromJsonFile(DATABASE_PATH + fileName, type);
        return result != null ? result : new Vector<>();
    }
    
    protected Vector<ServiceRecord> readServiceRecords(String fileName) {
        Type type = new TypeToken<Vector<ServiceRecord>>(){}.getType();
        Vector<ServiceRecord> result = readFromJsonFile(DATABASE_PATH + fileName, type);
        return result != null ? result : new Vector<>();
    }
    
    protected Vector<ServiceRequest> readServiceRequests(String fileName) {
        Type type = new TypeToken<Vector<ServiceRequest>>(){}.getType();
        Vector<ServiceRequest> result = readFromJsonFile(DATABASE_PATH + fileName, type);
        return result != null ? result : new Vector<>();
    }
    
    // Written by Wheeler Knight on 12/5/2025 - Added Operator persistence
    protected Vector<Operator> readOperators(String fileName) {
        Type type = new TypeToken<Vector<Operator>>(){}.getType();
        Vector<Operator> result = readFromJsonFile(DATABASE_PATH + fileName, type);
        return result != null ? result : new Vector<>();
    }
    
    /**
     * Read JSON from a file path
     */
    protected <T> T readFromJsonFile(String path, Type type) {
        File file = new File(path);
        if (!file.exists()) {
            System.out.println("Database file not found: " + path);
            return null;
        }
        
        try (FileReader reader = new FileReader(file)) {
            return gson.fromJson(reader, type);
        } catch (IOException e) {
            System.err.println("Error reading JSON file: " + path);
            e.printStackTrace();
            return null;
        }
    }
    
    // ==================== TYPE ADAPTERS ====================
    
    /**
     * Custom TypeAdapter for LocalDate since Gson doesn't handle it natively
     */
    private static class LocalDateAdapter extends TypeAdapter<LocalDate> {
        @Override
        public void write(JsonWriter out, LocalDate value) throws IOException {
            if (value == null) {
                out.nullValue();
            } else {
                out.value(value.toString()); // ISO-8601 format: yyyy-MM-dd
            }
        }
        
        @Override
        public LocalDate read(JsonReader in) throws IOException {
            String dateStr = in.nextString();
            if (dateStr == null || dateStr.isEmpty()) {
                return null;
            }
            return LocalDate.parse(dateStr);
        }
    }
}
