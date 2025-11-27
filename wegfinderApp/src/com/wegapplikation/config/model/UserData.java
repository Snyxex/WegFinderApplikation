package com.wegapplikation.config.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code UserData} class represents a single user entity (username, password, role)
 * and provides methods for managing the persistent storage of all user data
 * (CRUD operations) in a file.
 *
 * NOTE: Passwords are handled in plaintext in this implementation. For production
 * environments, passwords should always be hashed and salted.
 */
public class UserData {
    private String username;
    private String password; // Stored in plaintext in this model
    private String role;

    private static final String FILE_PATH = "files/user.txt";

    /**
     * Default constructor for creating a user object without initial data.
     */
    public UserData() {}

    /**
     * Constructs a {@code UserData} object with specified properties.
     *
     * @param username The unique username of the user.
     * @param password The password of the user (plaintext).
     * @param role The role assigned to the user (e.g., "Admin", "User").
     */
    public UserData(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    /**
     * Gets the username of the user.
     * @return The username.
     */
    public String getUsername() { return username; }

    /**
     * Gets the password of the user.
     * @return The password (plaintext).
     */
    public String getPassword() { return password; }

    /**
     * Gets the role of the user.
     * @return The user's role.
     */
    public String getRole() { return role; }

    /**
     * Checks if a given username is already taken in the system (case-insensitive).
     *
     * @param username The username to check.
     * @return {@code true} if the username is already in use, {@code false} otherwise.
     */
    public boolean isUsernameTaken(String username) {
        List<UserData> users = getAllUsers();
        return users.stream().anyMatch(user -> user.getUsername().equalsIgnoreCase(username));
    }

    /**
     * Verifies if the provided password matches the stored password for the given username.
     * The verification is case-insensitive for the username and case-sensitive for the password.
     *
     * @param username The username to verify.
     * @param password The password to check.
     * @return {@code true} if the username exists and the password matches, {@code false} otherwise.
     */
    public boolean verifyPassword(String username, String password) {
        List<UserData> users = getAllUsers();
        return users.stream()
                .filter(user -> user.getUsername().equalsIgnoreCase(username))
                .anyMatch(user -> user.getPassword().equals(password));
    }

    /**
     * Adds a new user to the list and saves the updated list to the file.
     *
     * @param user The {@code UserData} object representing the user to add.
     */
    public void addUser(UserData user) {
        List<UserData> users = getAllUsers();
        users.add(user);
        saveUsers(users);
    }

    /**
     * Updates an existing user's data based on the old username.
     * It finds the user by {@code oldUsername} (case-insensitive), replaces the data
     * with {@code updatedUser}, and saves the entire list.
     *
     * @param oldUsername The current username of the user to update.
     * @param updatedUser The {@code UserData} object containing the new information.
     */
    public void updateUser(String oldUsername, UserData updatedUser) {
        List<UserData> users = getAllUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equalsIgnoreCase(oldUsername)) {
                users.set(i, updatedUser);
                break;
            }
        }
        saveUsers(users);
    }

    /**
     * Deletes a user from the list based on their username (case-insensitive) and saves the updated list.
     *
     * @param username The username of the user to delete.
     */
    public void deleteUser(String username) {
        List<UserData> users = getAllUsers();
        users.removeIf(user -> user.getUsername().equalsIgnoreCase(username));
        saveUsers(users);
    }

    /**
     * Retrieves all user data stored in the file.
     * The method includes print statements for debugging the file reading process.
     *
     * @return A {@code List} of {@code UserData} objects. Returns an empty list if the file does not exist or an error occurs.
     */
    public List<UserData> getAllUsers() {
        List<UserData> users = new ArrayList<>();
        File file = new File(FILE_PATH);
        System.out.println("Lese user.txt von: " + file.getAbsolutePath());
        if (!file.exists()) {
            System.out.println("user.txt existiert nicht!");
            return users;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                System.out.println("Verarbeite Zeile " + lineNumber + ": " + line);
                if (line.trim().isEmpty()) {
                    System.out.println("Leere Zeile in Zeile " + lineNumber + ", überspringe.");
                    continue;
                }
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    users.add(new UserData(parts[0].trim(), parts[1].trim(), parts[2].trim()));
                    System.out.println("Nutzer hinzugefügt: " + parts[0]);
                } else {
                    System.out.println("Ungültiges Format in Zeile " + lineNumber + ": " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Fehler beim Lesen von user.txt: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Geladene Nutzer: " + users.size());
        return users;
    }

    /**
     * Writes the entire list of users back to the storage file, overwriting the
     * existing content.
     *
     * @param users The list of {@code UserData} objects to save.
     */
    private void saveUsers(List<UserData> users) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (UserData user : users) {
                writer.write(user.getUsername() + "," + user.getPassword() + "," + user.getRole());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Fehler beim Schreiben in user.txt: " + e.getMessage());
            e.printStackTrace();
        }
    }
}