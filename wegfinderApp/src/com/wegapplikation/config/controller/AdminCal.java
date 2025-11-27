package com.wegapplikation.config.controller;

import com.wegapplikation.config.model.FloorData;
import com.wegapplikation.config.model.RoomData;
import com.wegapplikation.config.model.UserData;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code AdminCal} class provides administrative functionalities
 * for managing users, rooms, and floors within the application.
 * It acts as a controller, interacting with the underlying data models
 * (UserData, RoomData, FloorData).
 */
public class AdminCal {
    private UserData userData;
    private RoomData roomData;
    private FloorData floorData;

    /**
     * Constructs an {@code AdminCal} object and initializes the
     * data model objects for users, rooms, and floors.
     */
    public AdminCal() {
        userData = new UserData();
        roomData = new RoomData();
        floorData = new FloorData(); // Initialisierung der FloorData (Initialization of FloorData)
    }

    // Benutzerverwaltung (User Management)

    /**
     * Adds a new user to the system.
     *
     * @param username The username of the new user.
     * @param password The password for the new user.
     * @param role The role assigned to the new user (e.g., "Admin", "User").
     * @throws IllegalArgumentException if the provided username is already taken.
     */
    public void addUser(String username, String password, String role) {
        UserData user = new UserData(username, password, role);
        if (userData.isUsernameTaken(username)) {
            throw new IllegalArgumentException("Benutzername '" + username + "' ist bereits vergeben.");
        }
        userData.addUser(user);
    }

    /**
     * Updates an existing user's details.
     *
     * @param oldUsername The current username of the user to be updated.
     * @param username The new username.
     * @param password The new password.
     * @param role The new role.
     * @throws IllegalArgumentException if the new username is different from the old one
     * and is already taken by another user.
     */
    public void updateUser(String oldUsername, String username, String password, String role) {
        UserData user = new UserData(username, password, role);
        if (!oldUsername.equals(username) && userData.isUsernameTaken(username)) {
            throw new IllegalArgumentException("Benutzername '" + username + "' ist bereits von einem anderen Benutzer vergeben.");
        }
        userData.updateUser(oldUsername, user);
    }

    /**
     * Deletes a user from the system based on their username.
     *
     * @param username The username of the user to delete.
     */
    public void deleteUser(String username) {
        if(username != null && !username.isEmpty()) {
            userData.deleteUser(username);
        }
    }

    /**
     * Retrieves a list of all users, with the password masked as "*****" if the user's
     * role is "Admin".
     *
     * @return A list of Object arrays, where each array contains
     * [username, masked_password, role].
     */
    public List<Object[]> getAllUsers() {
        List<UserData> users = userData.getAllUsers();
        List<Object[]> userArray = new ArrayList<>();
        for (UserData user : users) {
            String password = user.getPassword();
            // Maskiere das Passwort, wenn die Rolle "Admin" ist
            // Mask the password if the role is "Admin"
            if (user.getRole().equals("Admin")) {
                password = "*****";
            }
            userArray.add(new Object[]{user.getUsername(), password, user.getRole()});
        }
        return userArray;
    }

    /**
     * Retrieves the unmasked data for a specific user.
     *
     * @param username The username of the user to retrieve.
     * @return An array containing [username, password, role] for the user,
     * or {@code null} if the user is not found.
     */
    public Object[] getUserByUsername(String username) {
        List<UserData> users = userData.getAllUsers();
        for (UserData user : users) {
            if (user.getUsername().equals(username)) {
                return new Object[]{user.getUsername(), user.getPassword(), user.getRole()};
            }
        }
        return null; // Nutzer nicht gefunden (User not found)
    }

    // Raumverwaltung (Room Management)

    /**
     * Updates an existing room's details. Note: The {@code addRoom} method is missing,
     * but this method handles updates.
     *
     * @param oldId The current ID of the room to be updated.
     * @param newId The new ID for the room.
     * @param designation The new designation (name/description) for the room.
     * @param locked The new lock status for the room.
     * @throws IllegalArgumentException if the new ID is different from the old one
     * and is already taken by another room.
     */
    public void updateRoom(int oldId, int newId, String designation, boolean locked) {
        RoomData room = new RoomData(newId, designation, locked);
        if (oldId != newId && roomData.isIdTaken(newId)) {
            throw new IllegalArgumentException("Raum-ID '" + newId + "' ist bereits von einem anderen Raum vergeben.");
        }
        roomData.updateRoom(oldId, room);
    }

    /**
     * Retrieves a list of all rooms in the system.
     *
     * @return A list of Object arrays, where each array contains
     * [id, designation, locked].
     */
    public List<Object[]> getAllRooms() {
        List<RoomData> rooms = roomData.getAllRooms();
        List<Object[]> roomArray = new ArrayList<>();
        for (RoomData room : rooms) {
            roomArray.add(new Object[]{room.getId(), room.getDesignation(), room.isLocked()});
        }
        return roomArray;
    }

    /**
     * Verifies if the provided password matches the stored password for the given username.
     *
     * @param username The username to verify.
     * @param password The password to check.
     * @return {@code true} if the password is correct, {@code false} otherwise.
     */
    public boolean verifyPassword(String username, String password) {
        return userData.verifyPassword(username, password);
    }

    // Flurverwaltung (Floor Management)

    /**
     * Adds a new floor to the system.
     *
     * @param id The unique ID of the new floor.
     * @param locked The initial lock status of the new floor.
     */
    public void addFloor(int id, boolean locked) {
        floorData.addFloor(id, locked);
    }

    /**
     * Updates an existing floor's ID and lock status.
     *
     * @param oldId The current ID of the floor to be updated.
     * @param newId The new ID for the floor.
     * @param locked The new lock status for the floor.
     */
    public void updateFloor(int oldId, int newId, boolean locked) {
        floorData.updateFloor(oldId, newId, locked);
    }

    /**
     * Deletes a floor from the system based on its ID.
     *
     * @param id The ID of the floor to delete.
     */
    public void deleteFloor(int id) {
        floorData.deleteFloor(id);
    }

    /**
     * Retrieves a list of all floors in the system.
     *
     * @return A list of Object arrays, where each array contains
     * [id, locked].
     */
    public List<Object[]> getAllFloors() {
        List<FloorData.Floor> floors = floorData.getAllFloors();
        List<Object[]> floorArray = new ArrayList<>();
        for (FloorData.Floor floor : floors) {
            floorArray.add(new Object[]{floor.getId(), floor.isLocked()});
        }
        return floorArray;
    }

    /**
     * Placeholder method for handling the logout timer functionality.
     * Its implementation is currently empty.
     */
    public void LogoutTimer(){

    }
}