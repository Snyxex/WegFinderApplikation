package com.wegapplikation.config.controller;

import java.io.*;

import javax.swing.SwingUtilities;

import com.wegapplikation.config.view.AdminGUI;
import com.wegapplikation.config.view.LoginGUI;

public class LoginCal {
	
		public static void cal() {
            try (BufferedReader reader = new BufferedReader(new FileReader("wegfinderProject/src/files/user.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                	
                    String[] parts = line.split(",");
                    if (parts.length == 3) {
                     if (parts[0].equals(LoginGUI.textField.getText())) {
                    	 if(parts[0].equals(LoginGUI.textField.getText()) && parts[1].equals(String.valueOf(LoginGUI.passwordField.getPassword()))) {
                    	 System.out.println("Angemeldet als " + parts[2]);
                         AdminGUI AdminGUI = new AdminGUI();
                         AdminGUI.AdminGUI();
                         AdminGUI.setVisible(true);
                    	 LoginGUI.Errormsg.setVisible(false);
                    	 LoginGUI.frmMitarbeiterLogin.dispose();
                 		
                    	 break;
                    	 }
                     	} else {
                     		LoginGUI.Errormsg.setVisible(true);
                     	}
                    }
                }	
                
            } catch (IOException e) {
                e.printStackTrace();
            }
  }


}