package com.wegapplikation.config.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RoomData {
    private int id;
    private String designation;
    private boolean locked;
    private static final String FILE_PATH = "/wegfinderProject/room.txt";

    public RoomData() {
    }

    public RoomData(int id, String designation, boolean locked) {
        this.id = id;
        this.designation = designation;
        this.locked = locked;
    }

    public int getId() {
        return id;
    }

    public String getDesignation() {
        return designation;
    }

    public boolean isLocked() {
        return locked;
    }

    public boolean isIdTaken(int id) {
        List<RoomData> rooms = getAllRooms();
        return rooms.stream().anyMatch(room -> room.getId() == id);
    }

    public boolean isDesignationTaken(String designation) {
        List<RoomData> rooms = getAllRooms();
        return rooms.stream().anyMatch(room -> room.getDesignation().equalsIgnoreCase(designation));
    }

    public String getDesignationById(int id) {
        List<RoomData> rooms = getAllRooms();
        return rooms.stream()
                .filter(room -> room.getId() == id)
                .findFirst()
                .map(RoomData::getDesignation)
                .orElse("");
    }

    public void addRoom(RoomData room) {
        List<RoomData> rooms = getAllRooms();
        rooms.add(room);
        saveRooms(rooms);
    }

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

    public void deleteRoom(int id) {
        List<RoomData> rooms = getAllRooms();
        rooms.removeIf(room -> room.getId() == id);
        saveRooms(rooms);
    }

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
                if (parts.length == 3) {
                    rooms.add(new RoomData(Integer.parseInt(parts[0]), parts[1], Boolean.parseBoolean(parts[2])));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    private void saveRooms(List<RoomData> rooms) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (RoomData room : rooms) {
                writer.write(room.getId() + "," + room.getDesignation() + "," + room.isLocked());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}