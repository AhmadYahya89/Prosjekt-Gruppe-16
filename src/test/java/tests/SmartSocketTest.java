package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rooms.Room;
import rooms.SmartSocket;


class SmartSocketTest {
    private SmartSocket socket;
    private SmartSocket socket2;
    private SmartSocket socket3;
    private Room room;


    @BeforeEach
    void setUp() {
        socket = new SmartSocket(1, "Heater");
    }

    @Test
    void testToggleState() {
        assertFalse(socket.isStatusOn(), "Initial state should be off");
        socket.toggleSmartSocketState();
        assertTrue(socket.isStatusOn(), "State should be on after toggling");
        socket.toggleSmartSocketState();
        assertFalse(socket.isStatusOn(), "State should be off after toggling again");
    }

    @Test
    void testRenameSocket() {
        socket.setName("Fan");
        assertEquals("Fan", socket.getName(), "Socket name should be updated to 'Fan'");
    }

    @Test
    void testRenameSocketEmpty() {
        assertThrows(IllegalArgumentException.class, () -> socket.setName(""), "Name cannot be empty");
    }

    @Test
    void testDuplicateSocket(){
        room.addSocket(socket2);
        assertThrows(IllegalArgumentException.class, () -> room.addSocket(socket2),
                "Socket cannot be duplicated");
    }

    @Test
    void testDuplicateSocketName(){
        room.addSocket(socket3);
        SmartSocket duplicateSocketName = new SmartSocket(4, "Lamp");
        assertThrows(IllegalArgumentException.class, () -> room.addSocket(duplicateSocketName),
                "Sockets in the same room should not have the same name");
    }
}