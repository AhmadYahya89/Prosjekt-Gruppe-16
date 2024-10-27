package register_and_login;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AuthService {
    private HashMap<String, String> users = new HashMap<>();
    private static final String USER_FILE_PATH = "users.json"; // File path for user data

    public AuthService() {
        loadUsers(); // Load users from file at startup
    }

    public boolean registerUser(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("Username and password cannot be empty.");
            return false;
        }
        if (users.containsKey(username)) {
            System.out.println("register_and_login.User already exists.");
            return false;
        } else {
            users.put(username, password);
            System.out.println("Registration successful.");
            saveUsers(); // Save users to file after registration
            return true;
        }
    }

    public boolean loginUser(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("Username and password cannot be empty.");
            return false;
        }
        if (users.containsKey(username) && users.get(username).equals(password)) {
            System.out.println("Login successful.");
            return true;
        } else {
            System.out.println("Invalid username or password.");
            return false;
        }
    }

    // Load users from a JSON file
    private void loadUsers() {
        File file = new File(USER_FILE_PATH);
        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                Gson gson = new Gson();
                Type userMapType = new TypeToken<HashMap<String, String>>() {}.getType();
                users = gson.fromJson(reader, userMapType);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Save users to a JSON file
    private void saveUsers() {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(USER_FILE_PATH)) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
