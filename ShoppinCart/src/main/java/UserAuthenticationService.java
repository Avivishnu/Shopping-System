import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class UserAuthenticationService {
    private static UserAuthenticationService instance;
    private final Map<String, String> userCredentials;
    private final String filePath = "userCredentials.txt"; // Path to the text file

    private UserAuthenticationService() {
        this.userCredentials = new HashMap<>();
        loadUserCredentials();
    }

    public static synchronized UserAuthenticationService getInstance() {
        if (instance == null) {
            instance = new UserAuthenticationService();
        }
        return instance;
    }

    private void loadUserCredentials() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    userCredentials.put(parts[0].trim(), parts[1].trim());
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Credentials file not found, will create a new one upon registration.");
        } catch (IOException e) {
            System.out.println("Error reading credentials file: " + e.getMessage());
        }
    }

    private void saveUserCredentials() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Map.Entry<String, String> entry : userCredentials.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving credentials: " + e.getMessage());
        }
    }

    public boolean registerUser(String username, String password) {
        if (userCredentials.containsKey(username)) {
            System.out.println("Registration failed: Username already exists.");
            return false;
        }

        String hashedPassword = hashPassword(password);
        if (hashedPassword != null) {
            userCredentials.put(username, hashedPassword);
            saveUserCredentials(); // Save updated credentials to file
            System.out.println("User registered successfully.");
            return true;
        } else {
            System.out.println("Registration failed: Error hashing password.");
            return false;
        }
    }

    public boolean loginUser(String username, String password) {
        String storedHashedPassword = userCredentials.get(username);
        String inputHashedPassword = hashPassword(password);
        if (storedHashedPassword != null && storedHashedPassword.equals(inputHashedPassword)) {
            System.out.println("Login successful.");
            return true;
        } else {
            System.out.println("Login failed: Incorrect username or password.");
            return false;
        }
    }

    private String hashPassword(String password) {
        try {
            // Create MessageDigest instance for SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Perform the hashing
            byte[] hashBytes = digest.digest(password.getBytes());

            // Convert the byte array to a hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
