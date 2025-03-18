package control;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import javax.swing.DefaultListModel;

/**
 * Manages user data and persistence for the WegFinder application.
 * Handles CRUD operations for users and authentication.
 */
public class userData {
    private static final String USER_FILE = "users.txt";
    private static final String DELIMITER = ",";
    private static final String ADMIN_ROLE = "Admin";
    
    private final Map<String, String[]> users;
    private final DefaultListModel<String> userListModel;
    private final Path userFilePath;
    
    public userData() {
        users = new HashMap<>();
        userListModel = new DefaultListModel<>();
        userFilePath = Paths.get(USER_FILE);
        loadUsersFromFile();
    }

    public void addUser(String username, String password, String role) {
        validateUserInput(username, password);
        if (users.containsKey(username)) {
            throw new IllegalArgumentException("Benutzer existiert bereits!");
        }
        
        users.put(username, new String[]{password, role});
        saveUsers();
        refreshUserList();
    }

    public void deleteUser(String username) {
        if (!users.containsKey(username)) {
            throw new IllegalArgumentException("Benutzer nicht gefunden!");
        }
        
        users.remove(username);
        saveUsers();
        refreshUserList();
    }

    public void updateUser(String oldUsername, String newUsername, String newPassword, String newRole) {
        validateUpdateInput(oldUsername, newUsername, newRole);
        
        String[] existingData = users.get(oldUsername);
        if (existingData == null) {
            throw new IllegalArgumentException("Benutzer existiert nicht!");
        }

        String finalUsername = newUsername.isEmpty() ? oldUsername : newUsername;
        String finalPassword = newPassword.isEmpty() ? existingData[0] : newPassword;

        users.remove(oldUsername);
        users.put(finalUsername, new String[]{finalPassword, newRole});
        saveUsers();
        refreshUserList();
    }

    public boolean validateAdmin(String username, String password) {
        String[] userData = users.get(username);
        return userData != null && 
               userData[0].equals(password) && 
               userData[1].equals(ADMIN_ROLE);
    }

    private void validateUserInput(String username, String password) {
        if (username == null || password == null || 
            username.trim().isEmpty() || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Benutzername und Passwort dürfen nicht leer sein!");
        }
    }

    private void validateUpdateInput(String oldUsername, String newUsername, String newRole) {
        if (oldUsername == null || oldUsername.trim().isEmpty()) {
            throw new IllegalArgumentException("Alter Benutzername darf nicht leer sein!");
        }
        if (newRole == null || newRole.trim().isEmpty()) {
            throw new IllegalArgumentException("Rolle darf nicht leer sein!");
        }
        if (!newUsername.isEmpty() && users.containsKey(newUsername)) {
            throw new IllegalArgumentException("Neuer Benutzername existiert bereits!");
        }
    }

    private void saveUsers() {
        try {
            List<String> lines = new ArrayList<>();
            for (Map.Entry<String, String[]> entry : users.entrySet()) {
                lines.add(String.format("%s%s%s%s%s", 
                    entry.getKey(), DELIMITER, 
                    entry.getValue()[0], DELIMITER, 
                    entry.getValue()[1]));
            }
            Files.write(userFilePath, lines);
        } catch (IOException e) {
            throw new RuntimeException("Fehler beim Speichern der Benutzerdaten: " + e.getMessage());
        }
    }

    private void loadUsersFromFile() {
        try {
            if (Files.exists(userFilePath)) {
                Files.readAllLines(userFilePath).forEach(this::processUserLine);
            }
        } catch (IOException e) {
            System.err.println("Warnung: Benutzerdatei konnte nicht geladen werden: " + e.getMessage());
        }
    }

    private void processUserLine(String line) {
        String[] parts = line.split(DELIMITER);
        if (parts.length == 3) {
            users.put(parts[0], new String[]{parts[1], parts[2]});
        }
    }

    private void refreshUserList() {
        userListModel.clear();
        users.forEach((key, value) -> 
            userListModel.addElement(String.format("%s (%s)", key, value[1])));
    }

    public DefaultListModel<String> getUserListModel() {
        return userListModel;
    }

    public Map<String, String[]> getUsers() {
        return Collections.unmodifiableMap(users);
    }
}