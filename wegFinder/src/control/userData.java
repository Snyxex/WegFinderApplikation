package control;

import java.io.*;
import java.util.HashMap;
import javax.swing.DefaultListModel;

public class userData {

        private HashMap<String, String[]> users;
        private DefaultListModel<String> userListModel;
    
        public userData() {
            users = new HashMap<>();
            userListModel = new DefaultListModel<>();
            loadUsersFromFile();
        }
    
       
        public void deleteUser(String usernameValue) {
            File inputFile = new File("users.txt");
            File tempFile = new File("users_temp.txt");
    
            boolean userDeleted = false;
    
            try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                 BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
    
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 3) {
                        String username = parts[0];
                        if (!username.equals(usernameValue)) {
                            writer.write(line);
                            writer.newLine();
                        } else {
                            users.remove(username);
                            userDeleted = true;
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Fehler beim Lesen/Schreiben der Datei: " + e.getMessage());
            }
    
            if (!inputFile.delete()) {
                System.out.println("Fehler beim Löschen der Originaldatei!");
            }
            if (!tempFile.renameTo(inputFile)) {
                System.out.println("Fehler beim Umbenennen der temporären Datei!");
            }
    
            if (userDeleted) {
                refreshUserList();
            }
        }
        public void addUser(String username, String password, String role) {
            if (username.isEmpty() || password.isEmpty()) {
                throw new IllegalArgumentException("Benutzername und Passwort dürfen nicht leer sein!");
            }
        
            if (!users.containsKey(username)) {
                users.put(username, new String[]{password, role});
                saveUserToFile(username, password, role);
                refreshUserList();
            } else {
                throw new IllegalArgumentException("Benutzer existiert bereits!");
            }
        }
        
    
    
        private void saveUserToFile(String username, String password, String role) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", true))) {
                writer.write(username + "," + password + "," + role);
                writer.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
    
    
        private void loadUsersFromFile() {
            try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 3) {
                        users.put(parts[0], new String[]{parts[1], parts[2]});
                        userListModel.addElement(parts[0] + "," + parts[1] + " (" + parts[2] + ")");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    
        public boolean loadAdminDataFromFile(String adminUsername, String password) {
            boolean admintrue = false;
            String roleadmin = "Admin";
            try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 3) {
                            if (adminUsername.equals(parts[0]) && password.equals(parts[1]) && parts[2].equals(roleadmin)) {
                                admintrue = true;
                                break; // Beenden Sie die Schleife, wenn der Admin gefunden wurde
                            
                        }else{
                            System.out.println("no admin");
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace(); // Fehlerbehandlung hinzufügen
            }
            return admintrue;      
        }
        
        public void updateUser(String oldUsername, String newUsername, String newPassword, String newRole) {
            if (oldUsername.isEmpty()) {
                throw new IllegalArgumentException("Alter Benutzername darf nicht leer sein!");
            }
        
            if (users.containsKey(oldUsername)) {
                // Benutzer existiert, also aktualisieren wir die Daten
                String[] existingData = users.get(oldUsername);
                
                // Wenn der neue Benutzername leer ist, verwenden wir den alten Benutzernamen
                if (newUsername.isEmpty()) {
                    newUsername = oldUsername;
                } else {
                    // Entfernen des alten Benutzernamens, wenn ein neuer Benutzername angegeben wird
                    users.remove(oldUsername);
                }
        
                // Wenn das neue Passwort leer ist, verwenden wir das alte Passwort
                if (newPassword.isEmpty()) {
                    newPassword = existingData[0];
                }
        
                // Hinzufügen des neuen Benutzernamens und der neuen Daten
                users.put(newUsername, new String[]{newPassword, newRole});
        
                // Speichern der aktualisierten Benutzerdaten in der Datei
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt"))) {
                    for (String userKey : users.keySet()) {
                        String[] data = users.get(userKey);
                        writer.write(userKey + "," + data[0] + "," + data[1]);
                        writer.newLine();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                refreshUserList(); // Benutzerliste aktualisieren
            } else {
                throw new IllegalArgumentException("Benutzer existiert nicht!");
            }
        }
        
    private void refreshUserList() {
        userListModel.removeAllElements();
        users.forEach((key, value) -> userListModel.addElement(key + "," + value[0] + " (" + value[1] + ")"));
    }
    
    
    public DefaultListModel<String> getUserListModel() {
        return userListModel;
    }

    public HashMap<String, String[]> getUsers() {
        return users;
    }
}
