package com.wegapplikation.controller;
import com.wegapplikation.CustomKeyboard;
import com.wegapplikation.model.*;
import java.awt.event.*;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Font;

public class AdminCal extends JFrame {

     /** CardLayout manager for switching between different panels */
     private CardLayout cardLayout;
    
     /** Main panel containing all sub-panels */
     private JPanel mainPanel;
 
     /** List component displaying all users */
     private JList<String> userList;
     private JList<String> roomList;
     
     /** Manager class for handling user data operations */
     private UserData userDataManager;
     private RoomData roomDataManager;


    public String logedinAdmin = "jason";
 /**
     * Initializes and displays the admin dashboard.
     * Sets up the main window, menu bar, and all management panels.
     */
    protected void adminPage() {
        /** Neues Objekt Für UserData  */
        userDataManager = new UserData();
        roomDataManager = new RoomData();
        
      
        

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setTitle("Admin Dashboard");

        // Menüleiste erstellen
        createMenuBar();

        // CardLayout für Panel-Wechsel
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        
        // Panels hinzufügen
        mainPanel.add(adminDashboardPanel(), "Admin Dashboard");
        mainPanel.add(addUserPanel(), "Add User");
        mainPanel.add(deleteUserPanel(), "Delete User");
        mainPanel.add(updateUserPanel(),"Update User");
        mainPanel.add(updateRoomPanel(), "Update Room");
        mainPanel.add(lockCoridoorPanel(),"Lock Coridoor");
        mainPanel.add(helpPanel(),"Help");

        this.add(mainPanel, BorderLayout.CENTER);
        this.setVisible(true);
    }
    
    /**
     * Creates and configures the menu bar with all navigation options.
     * Sets up Dashboard, User Management, and Room Management menus.
     */
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(255, 255, 255));
    
        // Dashboard-Menü
        JMenu dashboardMenu = new JMenu("Dashboard");
        dashboardMenu.add(createMenuItem("Dashboard", e -> switchToPanel("Admin Dashboard")));
        dashboardMenu.add(createMenuItem("Hilfe", e -> switchToPanel("Help")));
    
        // User Management-Menü
        JMenu userMenu = new JMenu("Nutzer Management");
        userMenu.add(createMenuItem("Nutzer Hinzufügen", e -> switchToPanel("Add User")));
        userMenu.add(createMenuItem("Nutzer Updaten", e -> switchToPanel("Update User")));
        userMenu.add(createMenuItem("Nutzer Löschen", e -> switchToPanel("Delete User")));
    
        // Room Management-Menü
        JMenu roomMenu = new JMenu("Raum Management");
        roomMenu.add(createMenuItem("Raum Updaten", e -> switchToPanel("Update Room")));
        roomMenu.add(createMenuItem("Flur Sperren", e -> switchToPanel("Lock Corridor")));
    

        // SigOut Nav-Menü
        JMenu signOut = new JMenu("Sign Out");
        signOut.add(createMenuItem("Sign Out", e -> System.out.print("abgmeldet")));

       


        // Einheitliche Schriftart und Farbe für Menüs
        
        
    
        dashboardMenu.setFont(new Font("Arial", Font.BOLD, 15));
        userMenu.setFont(new Font("Arial", Font.BOLD, 15));
        roomMenu.setFont(new Font("Arial", Font.BOLD, 15));
        signOut.setFont(new Font("Arial",Font.BOLD,15));
        
    
        // Menüs zur Menüleiste hinzufügen
        menuBar.add(dashboardMenu);
        menuBar.add(userMenu);
        menuBar.add(roomMenu);
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(signOut);
    
        this.setJMenuBar(menuBar);
    }
     /**
     * Creates a styled menu item with hover effects.
     * 
     * @param text The text to display on the menu item
     * @param actionListener The action to perform when clicked
     * @return Configured JMenuItem
     */
    private JMenuItem createMenuItem(String text, ActionListener actionListener) {
        JMenuItem menuItem = new JMenuItem(text);
        menuItem.setFont(new Font("Arial", Font.PLAIN, 15));
    
        // Hintergrund- und Schriftfarbe setzen
        menuItem.setOpaque(true);
      
        menuItem.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    
        // Hover-Effekt
        menuItem.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                menuItem.setBackground(new Color(70, 70, 70));
            }
    
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                menuItem.setBackground(new Color(255, 255, 255));
            }
        });
    
        menuItem.addActionListener(actionListener);
        return menuItem;
    }
    

   
/**
     * Creates the main dashboard panel with map display.
     * 
     * @return JPanel configured as the main dashboard view
     */
    private JPanel adminDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.white);
        JLabel titleLabel = new JLabel("Admin Dashboard", SwingConstants.CENTER);
        
       
        JTextField textLabel = new JTextField("Hier kommt noch das MapGui hien task!!!!",SwingConstants.CENTER);
        
        textLabel.setEditable(false);
        textLabel.setBounds(0, 0, 60, 40);

        titleLabel.setFont(new Font("Arial",Font.BOLD,20));
        textLabel.setFont(new Font("Arial",Font.BOLD,20));
        
      
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(textLabel);
        return panel;
    }
  /**
     * Switches the display to the specified panel.
     * 
     * @param panelName Name of the panel to display
     */
    private void switchToPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
    }
/**
     * Creates the user creation panel with input form and user list.
     * 
     * @return Configured panel for adding new users
     */
    private JPanel addUserPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10)); 
        panel.setBackground(Color.lightGray);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
    
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
    
        JLabel titleLabel = new JLabel("Benutzer Hinzufügen", SwingConstants.CENTER);

        JLabel userLabel = new JLabel("Benutzername:");
        JTextField userField = new JTextField(15);
        JLabel passLabel = new JLabel("Passwort:");
        JPasswordField passField = new JPasswordField(15);
        JLabel roleLabel = new JLabel("Rolle:");
        String[] roles = {"Admin", "Mitarbeiter"};
        JComboBox<String> roleBox = new JComboBox<>(roles);
        JButton addUserButton = new JButton("Hinzufügen");
        JLabel statusLabel = new JLabel();
    
        Font labelFont = new Font("Arial", Font.PLAIN, 14);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        userLabel.setFont(labelFont);
        passLabel.setFont(labelFont);
        roleLabel.setFont(labelFont);
        addUserButton.setFont(new Font("Arial", Font.BOLD, 14));

        userField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new CustomKeyboard(userField);
               
                userField.setText("");
            }
        });

        passField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new CustomKeyboard(passField);
                passField.setText("");
            }
        });
        gbc .gridx = 0; gbc.gridy = 0; formPanel.add(titleLabel, gbc);
        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(userLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1; formPanel.add(userField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(passLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 2; formPanel.add(passField, gbc);
        gbc.gridx = 0; gbc.gridy = 3; formPanel.add(roleLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 3; formPanel.add(roleBox, gbc);
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; formPanel.add(addUserButton, gbc);
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2; formPanel.add(statusLabel, gbc);
    
        userList = new JList<>(userDataManager.getUserListModel());
        JScrollPane scrollPane = new JScrollPane(userList);
        scrollPane.setPreferredSize(new Dimension(200, 150));
    
        JPanel listPanel = new JPanel(new BorderLayout());
        JLabel listLabel = new JLabel("Benutzerliste:");
        listLabel.setFont(new Font("Arial", Font.BOLD, 14));
        listPanel.add(listLabel, BorderLayout.NORTH);
        listPanel.add(scrollPane, BorderLayout.CENTER);
    
        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(listPanel, BorderLayout.EAST);
    
        addUserButton.addActionListener(e -> {
            String user = userField.getText().trim();
            String pass = new String(passField.getPassword()).trim();
            String role = (String) roleBox.getSelectedItem();
    
            try {
                userDataManager.addUser(user, pass, role);
                statusLabel.setForeground(Color.GREEN);
                statusLabel.setText("Benutzer hinzugefügt!");
               
            } catch (IllegalArgumentException ex) {
                statusLabel.setForeground(Color.RED);
                statusLabel.setText(ex.getMessage());
            }
            // Eingabefelder zurücksetzen
            userField.setText("");
            passField.setText("");
        });
    
        return panel;
    }


/**
     * Creates the user deletion panel with confirmation workflow.
     * Requires admin password verification before deletion.
     * 
     * @return Configured panel for deleting users
     */
    private JPanel deleteUserPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10)); // Mehr Abstand für bessere Optik
        panel.setBackground(Color.lightGray);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5); // Größere Abstände für bessere Lesbarkeit
    
        JLabel titleLabel = new JLabel("Benutzer Löschen", SwingConstants.CENTER);
        JLabel userLabel = new JLabel("Benutzername:");
        JTextField userField = new JTextField(20); // Größeres Eingabefeld
    
        userField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                 new CustomKeyboard(userField);
            }
        });
        JLabel adminPassLabel = new JLabel("Admin Password:");
        JTextField adminPasswordJField = new JTextField(20);
            
        adminPasswordJField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new CustomKeyboard(adminPasswordJField);
            }
        });
        JButton deleteButton = new JButton("Löschen");
        JLabel statusLabel = new JLabel();
    
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        userLabel.setFont(new Font("Arial",Font.BOLD, 16));
        adminPassLabel.setFont(new Font("Arial",Font.BOLD,16));
        deleteButton.setFont(new Font("Arial",Font.BOLD, 16));
    
        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(titleLabel, gbc);
        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(userLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1; formPanel.add(userField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(adminPassLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 2; formPanel.add(adminPasswordJField, gbc);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; formPanel.add(deleteButton, gbc);
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; formPanel.add(statusLabel, gbc);
        
    
        deleteButton.addActionListener(e -> {
            String selectedUser = userField.getText().trim();
            String  adminPassword = adminPasswordJField.getText().trim();
         
            if (!selectedUser.isEmpty()) {
                
                

    
         
            boolean isAdmin = userDataManager.loadAdminDataFromFile(logedinAdmin, adminPassword);
                System.out.print(isAdmin);
                System.out.println(adminPassword);
                    if(isAdmin){
                        userDataManager.deleteUser(selectedUser);
                        statusLabel.setForeground(Color.GREEN);
                        statusLabel.setText("Benutzer gelöscht!");
                    }else{
                        statusLabel.setText("Password nicht Richtig");
                    }
                    
                        
                
                    
            } else {
                statusLabel.setForeground(Color.RED);
                statusLabel.setText("Bitte Benutzername eingeben!");
            }
            // Eingabefelder zurücksetzen
            userField.setText("");
            adminPasswordJField.setText("");
        });
    
        JList<String> userList = new JList<>(userDataManager.getUserListModel());
        JScrollPane scrollPane = new JScrollPane(userList);
        scrollPane.setPreferredSize(new Dimension(200, 150));
    
        JPanel listPanel = new JPanel(new BorderLayout());
        JLabel listLabel = new JLabel("Benutzerliste:");
        listLabel.setFont(new Font("Arial",Font.BOLD, 15));
        listPanel.add(listLabel, BorderLayout.NORTH);
        listPanel.add(scrollPane, BorderLayout.CENTER);
    
        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(listPanel, BorderLayout.EAST);
    
        return panel;
    }
     /**
         * Creates panel for updating existing user information.
         * Allows modification of username, password and role.
         * 
         * @return Configured panel for updating users
         */
    private JPanel updateUserPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.lightGray);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
    
        JLabel oldUserLabel = new JLabel("Alter Benutzername:");
        JTextField oldUserField = new JTextField(15);
        oldUserField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new CustomKeyboard(oldUserField);
            }
        });
        JLabel newUserLabel = new JLabel("Neuer Benutzername:");
        JTextField newUserField = new JTextField(15);
       newUserField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new CustomKeyboard(newUserField);
            }
        });
        JLabel passLabel = new JLabel("Neues Passwort:");
        JPasswordField passField = new JPasswordField(15);
        passField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
               new CustomKeyboard(passField);
            }
        });
        JLabel adminPassLabel = new JLabel("Admin Password:");
        JTextField adminPasswordJField = new JTextField(20);
            
        adminPasswordJField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
               new  CustomKeyboard(adminPasswordJField);
            }
        });

        JLabel titleLabel = new JLabel("Benutzer Updaten", SwingConstants.CENTER); 
        JLabel roleLabel = new JLabel("Neue Rolle:");
        String[] roles = {"Admin", "Mitarbeiter"};
        JComboBox<String> roleBox = new JComboBox<>(roles);
        JButton updateUserButton = new JButton("Aktualisieren");
        JLabel statusLabel = new JLabel();
    
        Font labelFont = new Font("Arial", Font.PLAIN, 15);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        adminPassLabel.setFont(labelFont);
        oldUserLabel.setFont(labelFont);
        newUserLabel.setFont(labelFont);
        passLabel.setFont(labelFont);
        roleLabel.setFont(labelFont);
        updateUserButton.setFont(new Font("Arial", Font.BOLD, 15));
    
        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(titleLabel, gbc);
        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(oldUserLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1; formPanel.add(oldUserField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(newUserLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 2; formPanel.add(newUserField, gbc);
        gbc.gridx = 0; gbc.gridy = 3; formPanel.add(passLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 3; formPanel.add(passField, gbc);
        gbc.gridx = 0; gbc.gridy = 4; formPanel.add(roleLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 4; formPanel.add(roleBox, gbc);
        gbc.gridx = 0; gbc.gridy = 5; formPanel.add(adminPassLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 5; formPanel.add(adminPasswordJField,gbc);
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2; formPanel.add(updateUserButton, gbc);
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2; formPanel.add(statusLabel, gbc);
       
        userList = new JList<>(userDataManager.getUserListModel());
        JScrollPane scrollPane = new JScrollPane(userList);
        scrollPane.setPreferredSize(new Dimension(200, 150));
    
        JPanel listPanel = new JPanel(new BorderLayout());
        JLabel listLabel = new JLabel("Benutzerliste:");
        listLabel.setFont(new Font("Arial", Font.BOLD, 14));
        listPanel.add(listLabel, BorderLayout.NORTH);
        listPanel.add(scrollPane, BorderLayout.CENTER);
    
        panel.add(listPanel, BorderLayout.EAST);
    
        updateUserButton.addActionListener(e -> {
            String  adminPassword = adminPasswordJField.getText().trim();
            String oldUser = oldUserField.getText().trim();
            String newUser = newUserField.getText().trim();
            String pass = new String(passField.getPassword()).trim();
            String role = (String) roleBox.getSelectedItem();
    
    
            boolean isAdmin = userDataManager.loadAdminDataFromFile(logedinAdmin, adminPassword);
                
                System.out.println(adminPassword);
                System.out.print(isAdmin);
                    if(isAdmin){
                        try {
                            userDataManager.updateUser(oldUser, newUser, pass, role);
                            statusLabel.setForeground(Color.GREEN);
                            statusLabel.setText("Benutzerdaten aktualisiert!");

                        } catch (IllegalArgumentException ex) {
                            statusLabel.setForeground(Color.RED);
                            statusLabel.setText(ex.getMessage());
                        }
                        
                        
                    }else{
                        statusLabel.setForeground(Color.RED);
                        statusLabel.setText("Password nicht Richtig");
                    }
            // Eingabefelder zurücksetzen
            oldUserField.setText("");
            newUserField.setText("");
            passField.setText("");
            adminPasswordJField.setText("");
        });
       
        
        panel.add(formPanel, BorderLayout.CENTER);
        return panel;
    }
    

   
    private JPanel updateRoomPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.lightGray);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
    
        // Erstellen der Komponenten
        JLabel titleLabel = new JLabel("Raum Aktualisieren", SwingConstants.CENTER);
        JLabel roomNumberLabel = new JLabel("Raum Nummer:");
        JTextField roomNumberField = new JTextField(15);
        JLabel textLabel = new JLabel("Raum Bezeichnung:");
        JTextField textField = new JTextField(15);
        JLabel setterLabel = new JLabel("Status:");
        String[] status = {"Offen", "Gesperrt"};
        JComboBox<String> setterBox = new JComboBox<>(status);
        JButton updateRoomButton = new JButton("Aktualisieren");
        JLabel statusLabel = new JLabel();
    
        // Font-Einstellungen
        Font labelFont = new Font("Arial", Font.PLAIN, 15);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        roomNumberLabel.setFont(labelFont);
        textLabel.setFont(labelFont);
        setterLabel.setFont(labelFont);
        updateRoomButton.setFont(new Font("Arial", Font.BOLD, 15));
    
        // Hinzufügen der Komponenten zum Panel
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        formPanel.add(titleLabel, gbc);
        
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(roomNumberLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(roomNumberField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(textLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(textField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(setterLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(setterBox, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        formPanel.add(updateRoomButton, gbc);
        
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        formPanel.add(statusLabel, gbc);
    
        // Raumliste erstellen
        JList<String> roomList = new JList<>(roomDataManager.getRoomListModel());
        JScrollPane scrollPane = new JScrollPane(roomList);
        scrollPane.setPreferredSize(new Dimension(200, 150));
    
        JPanel listPanel = new JPanel(new BorderLayout());
        JLabel listLabel = new JLabel("Raumliste:");
        listLabel.setFont(new Font("Arial", Font.BOLD, 14));
        listPanel.add(listLabel, BorderLayout.NORTH);
        listPanel.add(scrollPane, BorderLayout.CENTER);
    
        panel.add(listPanel, BorderLayout.EAST);
    
        // Action Listener für den Update-Button
        updateRoomButton.addActionListener(e -> {
            String roomNumber = roomNumberField.getText().trim();
            String text = textField.getText().trim();
            Boolean setter = setterBox.getSelectedItem().equals("Gesperrt");
    
            try {
                roomDataManager.updateRoom(roomNumber, text, setter);
                statusLabel.setForeground(Color.GREEN);
                statusLabel.setText("Raum erfolgreich aktualisiert!");
    
                // Eingabefelder zurücksetzen
                roomNumberField.setText("");
                textField.setText("");
                setterBox.setSelectedIndex(0);
            } catch (IllegalArgumentException ex) {
                statusLabel.setForeground(Color.RED);
                statusLabel.setText(ex.getMessage());
            }
        });
    
        panel.add(formPanel, BorderLayout.CENTER);
        return panel;
    }

    
    private JPanel lockCoridoorPanel(){
        JPanel panel = new JPanel(new BorderLayout());
       return panel;
    }
   
    private JPanel helpPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.lightGray);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    
        // Überschrift
        JLabel titleLabel = new JLabel("Hilfe & Dokumentation", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(titleLabel, BorderLayout.NORTH);
    
        // Hilfetext erstellen
        String helpText = "Nutzer Management:\n" +
                         "------------------\n" +
                         "• Nutzer Hinzufügen: Erstellen Sie neue Benutzerkonten mit Benutzername, Passwort und Rolle (Admin/Mitarbeiter).\n" +
                         "• Nutzer Updaten: Aktualisieren Sie bestehende Benutzerkonten (Passwort oder Rolle ändern),\n(für Alten Benutzer Namen feld (Neuer Nutzer leer lassen),(für Altes Password Neues Password Feld leer lassen)).\n" +
                         "• Nutzer Löschen: Entfernen Sie Benutzerkonten aus dem System (erfordert Admin-Berechtigung).\n\n" +
                         "Raum Management:\n" +
                         "---------------\n" +
                         "• Raum Updaten: Aktualisieren Sie Rauminformationen.\n" +
                         "• Flur Sperren: Sperren Sie Verbindungswege zwischen Räumen.\n\n" +
                         "Navigation:\n" +
                         "----------\n" +
                         "• Dashboard: Übersicht über das System und Hauptfunktionen.\n" +
                         "• Menüleiste: Schneller Zugriff auf alle Funktionen.\n" +
                         "• Sign Out: Sicheres Abmelden vom System.\n\n" +
                         "Tastatur:\n" +
                         "---------\n" +
                         "• Virtuelle Tastatur für sichere Eingabe.\n" +
                         "• CAPS-Taste für Großbuchstaben.\n" +
                         "• Eingabe bestätigen mit ENTER.";
    
        // Text in JTextArea anzeigen
        JTextArea helpTextArea = new JTextArea(helpText);
        helpTextArea.setFont(new Font("Arial", Font.PLAIN, 14));
        helpTextArea.setEditable(false);
        helpTextArea.setMargin(new Insets(10, 10, 10, 10));
        helpTextArea.setBackground(new Color(245, 245, 245));
        helpTextArea.setLineWrap(true);
        helpTextArea.setWrapStyleWord(true);
    
        // ScrollPane für den Text
        JScrollPane scrollPane = new JScrollPane(helpTextArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(scrollPane, BorderLayout.CENTER);
    
        return panel;
    }
        
     
    
   
}