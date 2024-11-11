
package register_and_login;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;

public class AuthService {
    private HashMap<String, String> users = new HashMap<>();
    private static final String USER_FILE_PATH = "users.json";

    public AuthService() {
        this.loadUsers();
    }

    // Register a user with validation and log response time
    public boolean registerUser(String username, String password) {
        long startTime = System.nanoTime();

        if (!isValidUsername(username) || !isValidPassword(password)) {
            return false;
        }
        if (this.users.containsKey(username)) {
            System.out.println("User already exists.");
            logResponseTime("registerUser", startTime);
            return false;
        }
        this.users.put(username, password);
        System.out.println("Registration successful.");
        this.saveUsers();

        logResponseTime("registerUser", startTime);
        return true;
    }

    // Login a user with validation and log response time
    public boolean loginUser(String username, String password) {
        long startTime = System.nanoTime();

        if (!isValidUsername(username) || !isValidPassword(password)) {
            logResponseTime("loginUser", startTime);
            return false;
        }
        if (this.users.containsKey(username) && this.users.get(username).equals(password)) {
            System.out.println("Login successful.");
            logResponseTime("loginUser", startTime);
            return true;
        } else {
            System.out.println("Invalid username or password.");
            logResponseTime("loginUser", startTime);
            return false;
        }
    }

    // Load users from JSON file
    private void loadUsers() {
        File file = new File(USER_FILE_PATH);
        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                Gson gson = new Gson();
                Type userMapType = new TypeToken<HashMap<String, String>>() {}.getType();
                this.users = gson.fromJson(reader, userMapType);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Save users to JSON file
    private void saveUsers() {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(USER_FILE_PATH)) {
            gson.toJson(this.users, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Validate username
    private boolean isValidUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            System.out.println("Username cannot be empty.");
            return false;
        }
        if (username.length() < 5 || username.length() > 15) {
            System.out.println("Username must be between 5 and 15 characters.");
            return false;
        }
        return username.matches("^[a-zA-Z0-9]+$");
    }

    // Validate password
    private boolean isValidPassword(String password) {
        if (password == null || password.length() < 8) {
            System.out.println("Password must be at least 8 characters.");
            return false;
        }
        return true;
    }

    // Log response time for methods
    private void logResponseTime(String methodName, long startTime) {
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println("Execution time for " + methodName + " (ns): " + duration);
    }
}
