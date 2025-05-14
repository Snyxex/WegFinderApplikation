package com.wegapplikation.model;

import java.io.*;
import java.util.HashMap;
import javax.swing.DefaultListModel;

public class RoomData {
    private static final String ROOM_FILE = "wegfinderProject/room.txt";
    private HashMap<String, Room> rooms;
    private DefaultListModel<String> roomListModel;

    public RoomData() {
        rooms = new HashMap<>();
        roomListModel = new DefaultListModel<>();
        loadRoomsFromFile();
        refreshRoomList();
    }

    // Lädt die Räume aus der Datei
    private void loadRoomsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ROOM_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
            
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String roomNumber = parts[0];
                    String text = parts[1];
                    Boolean setter = Boolean.parseBoolean(parts[2]);
                    rooms.put(roomNumber, new Room(roomNumber, text, setter));
                    System.out.println("Raum hinzugefügt: " + roomNumber); // Debug-Ausgabe
                }
            }
        } catch (IOException e) {
            System.out.println("Fehler beim Laden der Datei: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Aktualisiert einen Raum
    public void updateRoom(String roomNumber, String text, Boolean setter) {
        if (roomNumber == null || roomNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Raum Nummer darf nicht leer sein!");
        }

        // Prüfen ob der Raum existiert
        Room existingRoom = rooms.get(roomNumber);
        if (existingRoom == null) {
            throw new IllegalArgumentException("Raum existiert nicht!");
        }

        // Wenn text leer ist, den alten Text behalten
        if (text == null || text.trim().isEmpty()) {
            text = existingRoom.getText();
        }

        // Wenn setter null ist, den alten Setter behalten
        if (setter == null) {
            setter = existingRoom.getSetter();
        }

        // Erstellt ein neues Room-Objekt
        Room room = new Room(roomNumber, text, setter);
        // Fügt es zur HashMap hinzu
        rooms.put(roomNumber, room);
        
        // Speichert in der Datei
        saveAllRoomsToFile();
        refreshRoomList();
    }

    // Speichert alle Räume in der Datei
    private void saveAllRoomsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ROOM_FILE))) {
            for (Room room : rooms.values()) {
                writer.write(room.getRoomNumber() + "," + room.getText() + "," + room.getSetter());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Aktualisiert die Liste
    private void refreshRoomList() {
        roomListModel.removeAllElements();
        for (Room room : rooms.values()) {
            String status = room.getSetter() ? "Gesperrt" : "Offen";
            roomListModel.addElement(room.getRoomNumber() + "," + room.getText() + " (Status: " + status + ")");
        }
    }

    // Getter für die Liste
    public DefaultListModel<String> getRoomListModel() {
        return roomListModel;
    }

    // Getter für die Räume
    public HashMap<String, Room> getRooms() {
        return rooms;
    }

    // Innere Klasse für die Raumdaten
    public static class Room {
        private final String roomNumber;
        private final String text;
        private final Boolean setter;

        public Room(String roomNumber, String text, Boolean setter) {
            this.roomNumber = roomNumber;
            this.text = text;
            this.setter = setter;
        }

        public String getRoomNumber() { return roomNumber; }
        public String getText() { return text; }
        public Boolean getSetter() { return setter; }
    }
}