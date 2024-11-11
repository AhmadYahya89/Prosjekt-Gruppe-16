
package rooms;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private String name;
    private List<SmartSocket> sockets;

    public Room(String name) {
        if (!isValidRoomName(name)) {
            throw new IllegalArgumentException("Invalid room name.");
        }
        this.name = name;
        this.sockets = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }

    public List<SmartSocket> getSockets() {
        return this.sockets;
    }

    public void addSocket(SmartSocket socket) {
        this.sockets.add(socket);
    }

    public void removeSocket(SmartSocket socket) {
        if (this.sockets.contains(socket)) {
            this.sockets.remove(socket);
            System.out.println("Socket " + socket.getName() + " removed from the room.");
        } else {
            System.out.println("Socket not found in the room.");
        }
    }

    // Validate room name
    private boolean isValidRoomName(String roomName) {
        if (roomName == null || roomName.trim().isEmpty()) {
            System.out.println("Room name cannot be empty.");
            return false;
        }
        if (roomName.length() < 3 || roomName.length() > 30) {
            System.out.println("Room name must be between 3 and 30 characters.");
            return false;
        }
        if (!roomName.matches("^[a-zA-Z0-9 ]+$")) {
            System.out.println("Room name can only contain letters, numbers, and spaces.");
            return false;
        }
        return true;
    }
}
