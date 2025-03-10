package control;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class userData {



    private void deleteUserFunction(String usernameValue) {
        File inputFile = new File("users.txt");
        File tempFile = new File("users_temp.txt");

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

        userListModel.removeAllElements();
        users.forEach((key, value) -> userListModel.addElement(key + " (" + value[1] + ")"));
    }
}
