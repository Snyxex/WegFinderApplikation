package com.wegapplikation.config.controller;

import java.io.*;
import java.util.Arrays; // Wichtig für den sicheren Passwortvergleich

import com.wegapplikation.config.view.AdminGUI;
import com.wegapplikation.config.view.LoginGUI;

public class LoginCal {
    
    // Flagge, um zu verfolgen, ob ein Login erfolgreich war
    private static boolean loginSuccessful = false; 

    public static void cal() {
        // Eingaben aus der GUI abrufen
        String inputUsername = LoginGUI.textField.getText();
        char[] inputPassword = LoginGUI.passwordField.getPassword();
        loginSuccessful = false; // Zurücksetzen vor dem Start
        
        try (BufferedReader reader = new BufferedReader(new FileReader("wegfinderProject/src/files/user.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String fileUsername = parts[0].trim();
                    String filePasswordString = parts[1].trim(); // Passwort aus der Datei (String)
                    String fileRole = parts[2].trim();
                    
                    // 1. Benutzername prüfen
                    if (fileUsername.equals(inputUsername)) {
                        
                        // 2. Passwort sicher prüfen (Konvertiert Dateipasswort-String zu char[])
                        char[] filePasswordChars = filePasswordString.toCharArray();
                        
                        // Sichere Überprüfung mit Arrays.equals()
                        if (Arrays.equals(inputPassword, filePasswordChars)) {
                            
                            // *** LOGIN ERFOLGREICH ***
                            System.out.println("Angemeldet als " + fileRole);
                            
                            AdminGUI adminGUI = new AdminGUI(); 
                            adminGUI.AdminGUI();
                            adminGUI.setVisible(true); 
                            
                            LoginGUI.Errormsg.setVisible(false);
                            LoginGUI.frmMitarbeiterLogin.dispose();
                            loginSuccessful = true;
                            
                            // Wichtig: Passwort-Arrays löschen
                            Arrays.fill(inputPassword, '0');
                            Arrays.fill(filePasswordChars, '0'); 
                            
                            break; // Verlassen der Schleife nach erfolgreichem Login
                        }
                        
                        // Wichtig: Passwort-Array löschen, auch wenn es nicht erfolgreich war
                        Arrays.fill(filePasswordChars, '0');
                    }
                }
            }
            
            // Wenn die Schleife durchgelaufen ist und kein Erfolg vorliegt
            if (!loginSuccessful) {
                 LoginGUI.Errormsg.setVisible(true);
            }

        } catch (IOException e) {
            e.printStackTrace();
            // Optionale Meldung, wenn die Datei nicht gefunden wird
            LoginGUI.Errormsg.setText("Fehler: Benutzerdatei nicht gefunden!");
            LoginGUI.Errormsg.setVisible(true);
        } finally {
            // Wichtig: Eingabepasswort am Ende immer löschen
            Arrays.fill(inputPassword, '0');
        }
    }
}