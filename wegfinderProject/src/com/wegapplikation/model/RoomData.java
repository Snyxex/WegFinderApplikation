package com.wegapplikation.model;

import java.io.*;
import java.util.HashMap;
import javax.swing.DefaultListModel;

public class RoomData {
    File ROOM_FILE = new File("wegfinderProject/room.txt");
    
    private HashMap<String, String[]> rooms; // Verwendung von String[]
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
                    rooms.put(roomNumber, new String[]{text, setter.toString()}); // Speichern als String[]
                    System.out.println("Raum hinzugefügt: " + roomNumber); // Debug-Ausgabe
                } else {
                    System.out.println("Ungültige Zeile in der Datei: " + line);
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
            System.out.println("Fehler: Raum Nummer darf nicht leer sein!");
            throw new IllegalArgumentException("Raum Nummer darf nicht leer sein!");
        }

        String[] existingRoom = rooms.get(roomNumber);
        if (existingRoom == null) {
            System.out.println("Fehler: Raum existiert nicht!");
            throw new IllegalArgumentException("Raum existiert nicht!");
        }

        if (text == null || text.trim().isEmpty()) {
            text = existingRoom[0]; // Vorhandenen Text verwenden
        }

        if (setter == null) {
            setter = Boolean.parseBoolean(existingRoom[1]); // Vorhandenen Status verwenden
        }

        rooms.put(roomNumber, new String[]{text, setter.toString()});
        
        if (!saveAllRoomsToFile()) { // Speichern der Änderungen
            System.out.println("Fehler beim Speichern der Räume.");
        }
        refreshRoomList();
    }

    // Speichert alle Räume in der Datei
    private boolean saveAllRoomsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ROOM_FILE))) {
            for (String roomNumber : rooms.keySet()) {
                String[] roomData = rooms.get(roomNumber);
                writer.write(roomNumber + "," + roomData[0] + "," + roomData[1]);
                writer.newLine();
            }
            return true; // Erfolgreiches Speichern
        } catch (IOException e) {
            System.out.println("Fehler beim Speichern der Datei: " + e.getMessage());
            e.printStackTrace();
            return false; // Fehler beim Speichern
        }
    }

    // Aktualisiert die Liste
    private void refreshRoomList() {
        roomListModel.removeAllElements();
        for (String roomNumber : rooms.keySet()) {
            String[] roomData = rooms.get(roomNumber);
            String status = Boolean.parseBoolean(roomData[1]) ? "Gesperrt" : "Offen";
            roomListModel.addElement(roomNumber + "," + roomData[0] + " (Status: " + status + ")");
        }
    }

    // Getter für die Liste
    public DefaultListModel<String> getRoomListModel() {
        return roomListModel;
    }

    // Getter für die Räume
    public HashMap<String, String[]> getRooms() { // Verwendung von String[]
        return rooms;
    }
}