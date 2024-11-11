
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import register_and_login.AuthService;
import rooms.Room;
import rooms.SmartSocket;

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

        // User authentication loop
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
            System.out.println("Select an option:");
            System.out.println("1. Add new room");
            System.out.println("2. Show list of rooms");
            System.out.println("3. Add SmartSocket to an existing room");
            System.out.println("4. Quit");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addNewRoom(scanner, rooms);
                    break;
                case 2:
                    showRooms(rooms);
                    break;
                case 3:
                    addSmartSocketToRoom(scanner, rooms);
                    break;
                case 4:
                    System.out.println("Exiting the program...");
                    saveRoomToFile(rooms);
                    return;
                default:
                    System.out.println("Invalid choice, please choose again.");
            }
        }
    }

    // Add a new room
    private static void addNewRoom(Scanner scanner, List<Room> rooms) {
        System.out.print("Enter new room name: ");
        String roomName = scanner.nextLine();

        // Validate room name
        if (!isValidRoomName(roomName)) {
            return; // Prompt user again if validation fails
        }

        // Check if the room already exists
        if (roomExists(rooms, roomName)) {
            System.out.println(" " + roomName + " already exists. Please choose another name.");
            return;
        }

        // Save the new room
        rooms.add(new Room(roomName));
        System.out.println(" " + roomName + " was added to the room list.");
                    saveRoomToFile(rooms);
        }

        // Show the list of rooms
        private static void showRooms(List<Room> rooms) {
            if (rooms.isEmpty()) {
                System.out.println("No rooms available.");
            } else {
                System.out.println("List of rooms:");
                for (Room room : rooms) {
                    System.out.println("- " + room.getName());
                }
            }
        }

        // Add a SmartSocket to an existing room
        private static void addSmartSocketToRoom(Scanner scanner, List<Room> rooms) {
            if (rooms.isEmpty()) {
                System.out.println("No rooms available to add a SmartSocket. Please add a room first.");
                return;
            }

            System.out.println("Select a room to add a SmartSocket:");
            for (int i = 0; i < rooms.size(); i++) {
                System.out.println((i + 1) + ". " + rooms.get(i).getName());
            }
            int roomChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (roomChoice < 1 || roomChoice > rooms.size()) {
                System.out.println("Invalid choice. Please select a valid room.");
                return;
            }

            Room selectedRoom = rooms.get(roomChoice - 1);

            System.out.print("Enter SmartSocket name: ");
            String socketName = scanner.nextLine();
            System.out.print("Enter SmartSocket number: ");
            int socketNumber = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            try {
                SmartSocket newSocket = new SmartSocket(socketNumber, socketName);
                selectedRoom.addSocket(newSocket);
                System.out.println("SmartSocket "  + socketName + " added to room " + selectedRoom.getName() +".");
                saveRoomToFile(rooms);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        // Validate room name based on specific criteria
        private static boolean isValidRoomName(String roomName) {
            if (roomName == null || roomName.trim().isEmpty()) {
                System.out.println("Room name cannot be empty.");
                return false;
            }
            if (roomName.length() < 3 || roomName.length() > 30) {
                System.out.println("Room name must be between 3 and 30 characters.");
                return false;
            }
            if (!roomName.matches("^[a-zA-Z0-9 ]+$")) { // alphanumeric and spaces only
                System.out.println("Room name can only contain letters, numbers, and spaces.");
                return false;
            }
            return true;
        }

        // User registration method
        private static void registerUser(Scanner scanner) {
            System.out.println("Enter your username:");
            String username = scanner.nextLine().trim();
            System.out.println("Enter your password:");
            String password = scanner.nextLine().trim();

            authService.registerUser(username, password);
        }

        // User login method
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
