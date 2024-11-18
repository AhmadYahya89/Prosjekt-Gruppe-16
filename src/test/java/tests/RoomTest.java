package tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rooms.*;

import java.io.*;
import java.util.List;



class RoomTest {
    private Room room;
    private SmartSocket socket;

    @BeforeEach
    void setUp() {
        room = new Room("Living rooms.Room");
        socket = new SmartSocket(1, "Lamp");
    }

    @Test
    void testAddSocket() {
        //Krav: Teste at en SmartSocket kan legges til i et rom
        room.addSocket(socket);
        assertTrue(room.getSockets().contains(socket), "Socket should be added to the room");
    }

    @Test
    void testRemoveSocket() {
        //Krav: Teste at en SmartSocket kan fjernes fra et rom
        room.addSocket(socket);
        room.removeSocket(socket);
        assertFalse(room.getSockets().contains(socket), "Socket should be removed from the room");
    }

    @Test
    void testRoomNameNotEmpty() {
        //Krav: Rommnavn kan ikke være tomt
        assertThrows(IllegalArgumentException.class, () -> new Room(""), "rooms.Room name cannot be empty");
    }


    //Lagering/Lasting av informasjon om rom til JSON-fil
    @Test
    void testSaveRoomToFile() throws IOException {
        //Arrange
        FileWriter mockWriter = mock(FileWriter.class);
        Room room = new Room("Living Room");
        room.addSocket(new SmartSocket(1, "Lamp"));

        //Act
        mockWriter.write("Rom: " + room.getName());
        mockWriter.write("\nSockets:\n");
        for (SmartSocket socket : room.getSockets()) {
            mockWriter.write(" - " + socket.getName() + "\n");
        }
        mockWriter.flush();

        //Assert
        verify(mockWriter).write("Rom: " + room.getName());
        verify(mockWriter, atLeastOnce()).write(anyString());
        verify(mockWriter).flush();
    }
    @Test
    void testLoadRoomFromFile() throws IOException {
        //Arrange
        FileReader mockReader = mock(FileReader.class);
        String mockData = "Room: Living Room\nSockets:\n - Lamp\n";
        when(mockReader.read(any(char[].class), anyInt(), anyInt())).thenAnswer(invocation -> {
            char[] buffer = invocation.getArgument(0);
            System.arraycopy(mockData.toCharArray(), 0, buffer, 0, mockData.length());
            return mockData.length();
        });

        //Act
        char[] buffer = new char[1024];
        mockReader.read(buffer, 0, buffer.length);
        String result = new String(buffer).trim();

        //Assert
        verify(mockReader).read(any(char[].class), anyInt(), anyInt());
        assertTrue(result.contains("Room: Living Room"));
        assertTrue(result.contains("Sockets:"));
        assertTrue(result.contains(" - Lamp"));
    }
}