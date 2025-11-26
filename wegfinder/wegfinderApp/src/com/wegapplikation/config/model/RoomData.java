package com.wegapplikation.config.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * The {@code RoomData} class represents a single room entity and provides
 * static methods for managing the persistent storage of all room data
 * (CRUD operations) in a file.
 */
public class RoomData {
    private int id;
    private String designation;
    private boolean locked;
    private int y;
    private int x;
    private static final String FILE_PATH = "files/room.txt";

    /**
     * Default constructor for creating a room object without initial data.
     * Typically used when the class methods are called statically (though the current
     * implementation mixes instance and static-like methods).
     */
    public RoomData() {
    }

    /**
     * Constructs a {@code RoomData} object with specified properties.
     *
     * @param id The unique identifier of the room.
     * @param designation The descriptive name or label of the room.
     * @param locked The lock status of the room (true if locked, false otherwise).
     * @param y The y Position on the Map
     * @param x The x Position on the Map
     */
    public RoomData(int id, String designation, boolean locked, int y, int x) {
        this.id = id;
        this.designation = designation;
        this.locked = locked;
        this.y = y;
        this.x = x;
    }
    
    public RoomData(int id, String designation, boolean locked) {
        this.id = id;
        this.designation = designation;
        this.locked = locked;
    }

    /**
     * Gets the unique identifier of the room.
     *
     * @return The room ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the descriptive name or label of the room.
     *
     * @return The room designation.
     */
    public String getDesignation() {
        return designation;
    }

    /**
     * Checks the lock status of the room.
     *
     * @return {@code true} if the room is locked, {@code false} otherwise.
     */
    public boolean isLocked() {
        return locked;
    }
    
    
    /**
     * Gets the unique identifier of the room.
     *
     * @return The y Position.
     */
    public int getY() {
        return y;
    }
    
    /**
     * Gets the unique identifier of the room.
     *
     * @return The x Position.
     */
    public int getX() {
        return x;
    }

    /**
     * Checks if a given room ID is already in use by any room in the file.
     *
     * @param id The ID to check for existence.
     * @return {@code true} if the ID is taken, {@code false} otherwise.
     */
    public boolean isIdTaken(int id) {
        List<RoomData> rooms = getAllRooms();
        return rooms.stream().anyMatch(room -> room.getId() == id);
    }

    /**
     * Checks if a given room designation is already in use by any room in the file.
     * The comparison is case-insensitive.
     *
     * @param designation The designation to check for existence.
     * @return {@code true} if the designation is taken, {@code false} otherwise.
     */
    public boolean isDesignationTaken(String designation) {
        List<RoomData> rooms = getAllRooms();
        return rooms.stream().anyMatch(room -> room.getDesignation().equalsIgnoreCase(designation));
    }

    /**
     * Retrieves the designation of a room based on its ID.
     *
     * @param id The ID of the room whose designation is requested.
     * @return The designation string, or an empty string if the room ID is not found.
     */
    public String getDesignationById(int id) {
        List<RoomData> rooms = getAllRooms();
        return rooms.stream()
                .filter(room -> room.getId() == id)
                .findFirst()
                .map(RoomData::getDesignation)
                .orElse("");
    }

    /**
     * Updates an existing room in the storage file. It finds the room by {@code oldId},
     * replaces it with the data from {@code updatedRoom}, and saves the entire list.
     *
     * @param oldId The ID of the room to be updated.
     * @param updatedRoom A {@code RoomData} object containing the new information for the room.
     */
    public void updateRoom(int oldId, RoomData updatedRoom) {
        List<RoomData> rooms = getAllRooms();
        for (int i = 0; i < rooms.size(); i++) {
            if (rooms.get(i).getId() == oldId) {
                rooms.set(i, updatedRoom);
                break;
            }
        }
        saveRooms(rooms);
    }

    /**
     * Retrieves all room data stored in the file.
     *
     * @return A {@code List} of {@code RoomData} objects. Returns an empty list if the file does not exist or an error occurs.
     */
    public List<RoomData> getAllRooms() {
        List<RoomData> rooms = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return rooms;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    try {
                        int id = Integer.parseInt(parts[0].trim());
                        String designation = parts[1].trim();
                        boolean locked = Boolean.parseBoolean(parts[2].trim());
                        int y = Integer.parseInt(parts[3].trim());
                        int x =	Integer.parseInt(parts[4].trim());
                        rooms.add(new RoomData(id, designation, locked, y, x));
                    } catch (NumberFormatException e) {
                        System.err.println("Error parsing room data: " + line);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    /**
     * Writes the entire list of rooms back to the storage file, overwriting the
     * existing content.
     *
     * @param rooms The list of {@code RoomData} objects to save.
     */
    private void saveRooms(List<RoomData> rooms) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (RoomData room : rooms) {
                writer.write(room.getId() + "," + room.getDesignation() + "," + room.isLocked()+ "," + room.getY() + "," + room.getX());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}