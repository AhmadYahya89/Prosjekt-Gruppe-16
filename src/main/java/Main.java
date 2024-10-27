import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import register_and_login.AuthService;
import rooms.Room;

import java.lang.reflect.Type;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String FILE_PATH = "rooms.json";
    private static AuthService authService = new AuthService(); // Create an instance of register_and_login.AuthService

    public static void main(String[] args) {
        List<Room> rooms = loadExistingRooms();
        Scanner scanner = new Scanner(System.in);

        // register_and_login.User authentication loop
        boolean authenticated = false;
        while (!authenticated) {
            System.out.println("Select an option: ");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Quit");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    registerUser(scanner);
                    break;
                case 2:
                    authenticated = loginUser(scanner);
                    break;
                case 3:
                    System.out.println("Exiting the program...");
                    return; // Exit the program
                default:
                    System.out.println("Invalid choice, please choose again.");
            }
        }

        // Room management loop
        while (true) {
            System.out.print("Enter new room name (or type exit to quit): ");
            String roomName = scanner.nextLine();

            // Exit the program
            if (roomName.equalsIgnoreCase("exit")) {
                break;
            }

            // Check if the name is empty
            if (roomName.isEmpty()) {
                System.out.println("Room name cannot be empty.");
                continue;
            }

            // Check if the room already exists
            if (roomExists(rooms, roomName)) {
                System.out.println("\"" + roomName + "\" already exists. Please choose another name.");
                continue;
            }

            // Save the new room
            rooms.add(new Room(roomName));
            System.out.println("\"" + roomName + "\" was added to the room list.");
            saveRoomToFile(rooms);
        }

        // Print the room list
        System.out.println("List of rooms: ");
        for (Room room : rooms) {
            System.out.println(room.getName());
        }
    }

    // register_and_login.User registration method
    private static void registerUser(Scanner scanner) {
        System.out.println("Enter your username:");
        String username = scanner.nextLine().trim();
        System.out.println("Enter your password:");
        String password = scanner.nextLine().trim();

        authService.registerUser(username, password);
    }

    // register_and_login.User login method
    private static boolean loginUser(Scanner scanner) {
        System.out.println("Enter your username:");
        String username = scanner.nextLine().trim();
        System.out.println("Enter your password:");
        String password = scanner.nextLine().trim();

        return authService.loginUser(username, password);
    }

    // Check if a room name already exists
    private static boolean roomExists(List<Room> rooms, String roomName) {
        for (Room room : rooms) {
            if (room.getName().equalsIgnoreCase(roomName)) {
                return true;
            }
        }
        return false;
    }

    // Save rooms to JSON file
    private static void saveRoomToFile(List<Room> rooms) {
        Gson gson = new Gson();
        File file = new File(FILE_PATH);

        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(rooms, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load rooms from JSON file
    private static List<Room> loadExistingRooms() {
        Gson gson = new Gson();
        File file = new File(FILE_PATH);
        List<Room> rooms = new ArrayList<>();

        try (FileReader reader = new FileReader(file)) {
            Type roomListType = new TypeToken<ArrayList<Room>>() {}.getType();
            rooms = gson.fromJson(reader, roomListType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rooms;
    }
}
