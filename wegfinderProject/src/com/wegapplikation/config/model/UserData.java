package com.wegapplikation.config.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserData {
    private String username;
    private String password;
    private String role;
    private static final String FILE_PATH = "/wegfinderProject/user.txt";

    public UserData() {
    }

    public UserData(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public boolean isUsernameTaken(String username) {
        List<UserData> users = getAllUsers();
        return users.stream().anyMatch(user -> user.getUsername().equalsIgnoreCase(username));
    }

    public boolean verifyPassword(String username, String password) {
        List<UserData> users = getAllUsers();
        return users.stream()
                .filter(user -> user.getUsername().equalsIgnoreCase(username))
                .anyMatch(user -> user.getPassword().equals(password));
    }

    public void addUser(UserData user) {
        List<UserData> users = getAllUsers();
        users.add(user);
        saveUsers(users);
    }

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

    public void deleteUser(String username) {
        List<UserData> users = getAllUsers();
        users.removeIf(user -> user.getUsername().equalsIgnoreCase(username));
        saveUsers(users);
    }

    public List<UserData> getAllUsers() {
        List<UserData> users = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return users;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    users.add(new UserData(parts[0], parts[1], parts[2]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    private void saveUsers(List<UserData> users) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (UserData user : users) {
                writer.write(user.getUsername() + "," + user.getPassword() + "," + user.getRole());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}