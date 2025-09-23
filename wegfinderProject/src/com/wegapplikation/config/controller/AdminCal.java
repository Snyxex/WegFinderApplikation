package com.wegapplikation.config.controller;

import com.wegapplikation.config.model.FloorData;
import com.wegapplikation.config.model.RoomData;
import com.wegapplikation.config.model.UserData;

import java.util.ArrayList;
import java.util.List;

public class AdminCal {
    private UserData userData;
    private RoomData roomData;
    private FloorData floorData;

    public AdminCal() {
        userData = new UserData();
        roomData = new RoomData();
        floorData = new FloorData(); // Initialisierung der FloorData
    }

    // Benutzerverwaltung
    public void addUser(String username, String password, String role) {
        UserData user = new UserData(username, password, role);
        if (userData.isUsernameTaken(username)) {
            throw new IllegalArgumentException("Benutzername '" + username + "' ist bereits vergeben.");
        }
        userData.addUser(user);
    }

    public void updateUser(String oldUsername, String username, String password, String role) {
        UserData user = new UserData(username, password, role);
        if (!oldUsername.equals(username) && userData.isUsernameTaken(username)) {
            throw new IllegalArgumentException("Benutzername '" + username + "' ist bereits von einem anderen Benutzer vergeben.");
        }
        userData.updateUser(oldUsername, user);
    }

    public void deleteUser(String username) {
        if(username != null && !username.isEmpty()) {
            userData.deleteUser(username);
        }
    }

    public List<Object[]> getAllUsers() {
        List<UserData> users = userData.getAllUsers();
        List<Object[]> userArray = new ArrayList<>();
        for (UserData user : users) {
            String password = user.getPassword();
            // Maskiere das Passwort, wenn die Rolle "Admin" ist
            if (user.getRole().equals("Admin")) {
                password = "*****";
            }
            userArray.add(new Object[]{user.getUsername(), password, user.getRole()});
        }
        return userArray;
    }

    // Neue Methode, um die unmaskierten Daten eines Nutzers zu holen
    public Object[] getUserByUsername(String username) {
        List<UserData> users = userData.getAllUsers();
        for (UserData user : users) {
            if (user.getUsername().equals(username)) {
                return new Object[]{user.getUsername(), user.getPassword(), user.getRole()};
            }
        }
        return null; // Nutzer nicht gefunden
    }

    // Raumverwaltung
    public void addRoom(int id, String designation, boolean locked) {
        RoomData room = new RoomData(id, designation, locked);
        if (roomData.isIdTaken(id)) {
            throw new IllegalArgumentException("Raum-ID '" + id + "' ist bereits vergeben.");
        }
        if (roomData.isDesignationTaken(designation)) {
            throw new IllegalArgumentException("Bezeichnung '" + designation + "' ist bereits vergeben.");
        }
        roomData.addRoom(room);
    }

    public void updateRoom(int oldId, int newId, String designation, boolean locked) {
        RoomData room = new RoomData(newId, designation, locked);
        if (oldId != newId && roomData.isIdTaken(newId)) {
            throw new IllegalArgumentException("Raum-ID '" + newId + "' ist bereits von einem anderen Raum vergeben.");
        }
        if (!roomData.getDesignationById(oldId).equals(designation) && 
            roomData.isDesignationTaken(designation)) {
            throw new IllegalArgumentException("Bezeichnung '" + designation + "' ist bereits von einem anderen Raum vergeben.");
        }
        roomData.updateRoom(oldId, room);
    }

    public void deleteRoom(int id) {
        roomData.deleteRoom(id);
    }

    public List<Object[]> getAllRooms() {
        List<RoomData> rooms = roomData.getAllRooms();
        List<Object[]> roomArray = new ArrayList<>();
        for (RoomData room : rooms) {
            roomArray.add(new Object[]{room.getId(), room.getDesignation(), room.isLocked()});
        }
        return roomArray;
    }

    public boolean verifyPassword(String username, String password) {
        return userData.verifyPassword(username, password);
    }

    // Flurverwaltung
    public void addFloor(int id, boolean locked) {
        floorData.addFloor(id, locked);
    }

    public void updateFloor(int oldId, int newId, boolean locked) {
        floorData.updateFloor(oldId, newId, locked);
    }

    public void deleteFloor(int id) {
        floorData.deleteFloor(id);
    }

    public List<Object[]> getAllFloors() {
        List<FloorData.Floor> floors = floorData.getAllFloors();
        List<Object[]> floorArray = new ArrayList<>();
        for (FloorData.Floor floor : floors) {
            floorArray.add(new Object[]{floor.getId(), floor.isLocked()});
        }
        return floorArray;
    }



    public void LogoutTimer(){
        
    }
}