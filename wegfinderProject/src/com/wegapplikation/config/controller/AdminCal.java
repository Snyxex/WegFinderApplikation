package com.wegapplikation.config.controller;

import com.wegapplikation.config.model.RoomData;
import com.wegapplikation.config.model.UserData;

import java.util.List;

public class AdminCal {
    private UserData userData;
    private RoomData roomData;

    public AdminCal() {
        userData = new UserData();
        roomData = new RoomData();
    }

    // User Management
    public void addUser(UserData user) {
        if (userData.isUsernameTaken(user.getUsername())) {
            throw new IllegalArgumentException("Benutzername '" + user.getUsername() + "' ist bereits vergeben.");
        }
        userData.addUser(user);
    }

    public void updateUser(String oldUsername, UserData user) {
        if (!oldUsername.equals(user.getUsername()) && userData.isUsernameTaken(user.getUsername())) {
            throw new IllegalArgumentException("Benutzername '" + user.getUsername() + "' ist bereits von einem anderen Benutzer vergeben.");
        }
        userData.updateUser(oldUsername, user);
    }

    public void deleteUser(String username) {
        userData.deleteUser(username);
    }

    public List<UserData> getAllUsers() {
        return userData.getAllUsers();
    }

    // Room Management
    public void addRoom(RoomData room) {
        if (roomData.isIdTaken(room.getId())) {
            throw new IllegalArgumentException("Raum-ID '" + room.getId() + "' ist bereits vergeben.");
        }
        if (roomData.isDesignationTaken(room.getDesignation())) {
            throw new IllegalArgumentException("Bezeichnung '" + room.getDesignation() + "' ist bereits vergeben.");
        }
        roomData.addRoom(room);
    }

    public void updateRoom(int oldId, RoomData room) {
        if (oldId != room.getId() && roomData.isIdTaken(room.getId())) {
            throw new IllegalArgumentException("Raum-ID '" + room.getId() + "' ist bereits von einem anderen Raum vergeben.");
        }
        if (!roomData.getDesignationById(oldId).equals(room.getDesignation()) && 
            roomData.isDesignationTaken(room.getDesignation())) {
            throw new IllegalArgumentException("Bezeichnung '" + room.getDesignation() + "' ist bereits von einem anderen Raum vergeben.");
        }
        roomData.updateRoom(oldId, room);
    }

    public void deleteRoom(int id) {
        roomData.deleteRoom(id);
    }

    public List<RoomData> getAllRooms() {
        return roomData.getAllRooms();
    }
}