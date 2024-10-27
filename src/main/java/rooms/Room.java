package rooms;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private String name;
    private List<SmartSocket> sockets; // Liste over stikkontakter

    public Room(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Room name cannot be empty.");
        }
        this.name = name;
        this.sockets = new ArrayList<>();
    }


    public String getName() {
        return name;
    }

    //Viser liste over stikkontakter knyttet til rommet

    public List<SmartSocket> getSockets() {
        return sockets;
    }

    // Metode for å legge stikkontakt i liste over stikkontakter for rommet

    public void addSocket(SmartSocket socket) {
        sockets.add(socket);
    }


    public void removeSocket(SmartSocket socket) {
        if (sockets.contains(socket)) {
            sockets.remove(socket);
            System.out.println("Socket " + socket.getName() + " removed from the room.");
        } else {
            System.out.println("Socket not found in the room.");
        }
    }
}
