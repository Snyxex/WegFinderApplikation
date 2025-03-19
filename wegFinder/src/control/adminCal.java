package control;
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

public class adminCal extends JFrame {

     /** CardLayout manager for switching between different panels */
     private CardLayout cardLayout;
    
     /** Main panel containing all sub-panels */
     private JPanel mainPanel;
 
     /** List component displaying all users */
     private JList<String> userList;
     
     /** Manager class for handling user data operations */
     private userData userDataManager;
 /**
     * Initializes and displays the admin dashboard.
     * Sets up the main window, menu bar, and all management panels.
     */
    protected void adminPage() {
        userDataManager = new userData();
        
        

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
        mainPanel.add(addRoomPanel(),"Add Room");
        mainPanel.add(updateRoomPanel(), "Update Room");
        mainPanel.add(deleteRoomPanel(), "Delete Room");
        mainPanel.add(lockRoomPanel(),"Lock Room");
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
        roomMenu.add(createMenuItem("Raum Hinzufügen", e -> switchToPanel("Add Room")));
        roomMenu.add(createMenuItem("Raum Updaten", e -> switchToPanel("Update Room")));
        roomMenu.add(createMenuItem("Raum Löschen", e -> switchToPanel("Delete Room")));
        roomMenu.add(createMenuItem("Raum Sperren", e -> switchToPanel("Lock Room")));
        roomMenu.add(createMenuItem("Flur Sperren", e -> switchToPanel("Lock Corridor")));
    
        // Einheitliche Schriftart und Farbe für Menüs
        
        
    
        dashboardMenu.setFont(new Font("Arial", Font.BOLD, 16));
        userMenu.setFont(new Font("Arial", Font.BOLD, 16));
        roomMenu.setFont(new Font("Arial", Font.BOLD, 16));
    
        
    
        // Menüs zur Menüleiste hinzufügen
        menuBar.add(dashboardMenu);
        menuBar.add(userMenu);
        menuBar.add(roomMenu);
    
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
        menuItem.setFont(new Font("Arial", Font.PLAIN, 16));
    
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
        panel.setBackground(Color.WHITE);
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
        panel.setBackground(Color.white);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
    
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
    
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
        userLabel.setFont(labelFont);
        passLabel.setFont(labelFont);
        roleLabel.setFont(labelFont);
        addUserButton.setFont(new Font("Arial", Font.BOLD, 14));

        userField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                keyboard(userField);
                userField.setText("");
            }
        });

        passField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                keyboard(passField);
                passField.setText("");
            }
        });
    
        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(userLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0; formPanel.add(userField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(passLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1; formPanel.add(passField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(roleLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 2; formPanel.add(roleBox, gbc);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; formPanel.add(addUserButton, gbc);
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; formPanel.add(statusLabel, gbc);
    
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
    JPanel panel = new JPanel(new BorderLayout(15, 15)); // Mehr Abstand für bessere Optik
    panel.setBackground(Color.white);
    panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

    JPanel formPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(8, 8, 8, 8); // Größere Abstände für bessere Lesbarkeit

    JLabel userLabel = new JLabel("Benutzername:");
    JTextField userField = new JTextField(20); // Größeres Eingabefeld

    userField.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            keyboard(userField);
        }
    });
    JLabel adminPassLabel = new JLabel("Admin Password:");
    JTextField adminPasswordJField = new JTextField(20);
        
    adminPasswordJField.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            keyboard(adminPasswordJField);
        }
    });
    JButton deleteButton = new JButton("Löschen");
    JLabel statusLabel = new JLabel();

    
    userLabel.setFont(new Font("Arial",Font.BOLD, 16));
    adminPassLabel.setFont(new Font("Arial",Font.BOLD,16));
    deleteButton.setFont(new Font("Arial",Font.BOLD, 16));

    gbc.gridx = 0; gbc.gridy = 0; formPanel.add(userLabel, gbc);
    gbc.gridx = 1; gbc.gridy = 0; formPanel.add(userField, gbc);
    gbc.gridx = 0; gbc.gridy = 1; formPanel.add(adminPassLabel, gbc);
    gbc.gridx = 1; gbc.gridy = 1; formPanel.add(adminPasswordJField, gbc);
    gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; formPanel.add(deleteButton, gbc);
    gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; formPanel.add(statusLabel, gbc);

    deleteButton.addActionListener(e -> {
        String selectedUser = userField.getText().trim();
        String  adminPassword = adminPasswordJField.getText().trim();
     
        if (!selectedUser.isEmpty()) {
            
            

            deleteButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e){
                    keyboard(adminPasswordJField);
                   
                    adminPasswordJField.setText("");
                }
            });

        String logedinAdmin = "jason";
        boolean isAdmin = userDataManager.loadAdminDataFromFile(logedinAdmin, adminPassword);
            System.out.print(isAdmin);
            System.out.println(adminPassword);
                if(isAdmin){
                    userDataManager.deleteUser(selectedUser);
                    statusLabel.setForeground(Color.GREEN);
                    statusLabel.setText("Benutzer gelöscht!");
                    userField.setText("");
                }else{
                    statusLabel.setText("Password nicht Richtig");
                }
                
                    
            
                
        } else {
            statusLabel.setForeground(Color.RED);
            statusLabel.setText("Bitte Benutzername eingeben!");
        }
    });

    JList<String> userList = new JList<>(userDataManager.getUserListModel());
    JScrollPane scrollPane = new JScrollPane(userList);
    scrollPane.setPreferredSize(new Dimension(250, 180));

    JPanel listPanel = new JPanel(new BorderLayout());
    JLabel listLabel = new JLabel("Benutzerliste:");
    listLabel.setFont(new Font("Arial",Font.BOLD, 16));
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
    panel.setBackground(Color.white);
    panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    JPanel formPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(5, 5, 5, 5);

    JLabel oldUserLabel = new JLabel("Alter Benutzername:");
    JTextField oldUserField = new JTextField(15);
    JLabel newUserLabel = new JLabel("Neuer Benutzername (leer lassen, um den alten zu behalten):");
    JTextField newUserField = new JTextField(15);
    JLabel passLabel = new JLabel("Neues Passwort (leer lassen, um das alte zu behalten):");
    JPasswordField passField = new JPasswordField(15);
    JLabel roleLabel = new JLabel("Neue Rolle:");
    String[] roles = {"Admin", "Mitarbeiter"};
    JComboBox<String> roleBox = new JComboBox<>(roles);
    JButton updateUserButton = new JButton("Aktualisieren");
    JLabel statusLabel = new JLabel();

    Font labelFont = new Font("Arial", Font.PLAIN, 14);
    oldUserLabel.setFont(labelFont);
    newUserLabel.setFont(labelFont);
    passLabel.setFont(labelFont);
    roleLabel.setFont(labelFont);
    updateUserButton.setFont(new Font("Arial", Font.BOLD, 14));

    gbc.gridx = 0; gbc.gridy = 0; formPanel.add(oldUserLabel, gbc);
    gbc.gridx = 1; gbc.gridy = 0; formPanel.add(oldUserField, gbc);
    gbc.gridx = 0; gbc.gridy = 1; formPanel.add(newUserLabel, gbc);
    gbc.gridx = 1; gbc.gridy = 1; formPanel.add(newUserField, gbc);
    gbc.gridx = 0; gbc.gridy = 2; formPanel.add(passLabel, gbc);
    gbc.gridx = 1; gbc.gridy = 2; formPanel.add(passField, gbc);
    gbc.gridx = 0; gbc.gridy = 3; formPanel.add(roleLabel, gbc);
    gbc.gridx = 1; gbc.gridy = 3; formPanel.add(roleBox, gbc);
    gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; formPanel.add(updateUserButton, gbc);
    gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2; formPanel.add(statusLabel, gbc);
    

    updateUserButton.addActionListener(e -> {
        String oldUser = oldUserField.getText().trim();
        String newUser = newUserField.getText().trim();
        String pass = new String(passField.getPassword()).trim();
        String role = (String) roleBox.getSelectedItem();

        try {
            userDataManager.updateUser(oldUser, newUser, pass, role);
            statusLabel.setForeground(Color.GREEN);
            statusLabel.setText("Benutzerdaten aktualisiert!");
        } catch (IllegalArgumentException ex) {
            statusLabel.setForeground(Color.RED);
            statusLabel.setText(ex.getMessage());
        }
    });

    panel.add(formPanel, BorderLayout.CENTER);
    return panel;
}
    
    

   
    private JPanel addRoomPanel(){
        JPanel panel = new JPanel(new BorderLayout());
        return panel;
    }
     
    private JPanel updateRoomPanel(){
        JPanel panel = new JPanel(new BorderLayout());
        return panel;
    }

    private JPanel deleteRoomPanel(){
        JPanel panel = new JPanel(new BorderLayout());
       return panel;
    }

    private JPanel lockRoomPanel(){
        JPanel panel = new JPanel(new BorderLayout());
        return panel;
    }

    private JPanel lockCoridoorPanel(){
        JPanel panel = new JPanel(new BorderLayout());
       return panel;
    }
   
    private JPanel helpPanel(){
        JPanel panel = new JPanel(new BorderLayout());
       return panel;
    }

     /**
     * Displays an on-screen keyboard for text input.
     * Supports uppercase/lowercase and basic text editing.
     * 
     * @param targetField The text field that will receive the keyboard input
     */
         private void keyboard(JTextField targetField) {
        JFrame keyboardFrame = new JFrame("Keyboard");
        keyboardFrame.setSize(700, 350);
        keyboardFrame.setLayout(new BorderLayout());
        keyboardFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    
        // Textfeld für Live-Eingabe
        JTextField keyboardInputField = new JTextField(targetField.getText(), 20);
        keyboardInputField.setFont(new Font("Arial", Font.BOLD, 16));
        keyboardInputField.setHorizontalAlignment(JTextField.CENTER);
        keyboardInputField.setEditable(false);
        keyboardFrame.add(keyboardInputField, BorderLayout.NORTH);
    
        // Haupt-Panel für die Tastatur
        JPanel mainPanel = new JPanel(new GridLayout(4, 1, 5, 5));
    
        // Zeilen mit separaten Panels
        JPanel row1 = new JPanel(new GridLayout(1, 10, 5, 5)); // Zahlenreihe
        JPanel row2 = new JPanel(new GridLayout(1, 9, 5, 5));  // Erste Buchstabenreihe
        JPanel row3 = new JPanel(new GridLayout(1, 10, 5, 5)); // Zweite Buchstabenreihe
        JPanel row4 = new JPanel(new GridLayout(1, 10, 5, 5)); // Dritte Buchstabenreihe
    
        // Variable für Caps-Lock-Status
        final boolean[] capsLock = {false};
    
        // Zahlenreihe
        String[] numbers = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
        for (String key : numbers) row1.add(createKeyButton(key, keyboardInputField, targetField, keyboardFrame, capsLock));
    
        // Erste Buchstabenreihe (Q verschoben)
        String[] row1Keys = {"", "Q", "W", "E", "R", "T", "Z", "U", "I", "O"};
        for (String key : row1Keys) row2.add(createKeyButton(key, keyboardInputField, targetField, keyboardFrame, capsLock));
    
        // Zweite Buchstabenreihe
        String[] row2Keys = {"P", "A", "S", "D", "F", "G", "H", "J", "K", "L"};
        for (String key : row2Keys) row3.add(createKeyButton(key, keyboardInputField, targetField, keyboardFrame, capsLock));
    
        // Dritte Buchstabenreihe mit Steuerungstasten
        String[] row3Keys = {"<-", "Y", "X", "C", "V", "B", "N", "M", ",", "."};
        for (String key : row3Keys) row4.add(createKeyButton(key, keyboardInputField, targetField, keyboardFrame, capsLock));
    
        // Steuerungstasten (ENTER, CAPS)
        JButton capsButton = new JButton("CAPS");
        capsButton.setFont(new Font("Arial", Font.BOLD, 14));
        capsButton.addActionListener(e -> {
            capsLock[0] = !capsLock[0]; // Umschalten von Caps-Lock
            capsButton.setBackground(capsLock[0] ? Color.LIGHT_GRAY : null);
        });
    
        row4.add(createKeyButton("ENTER", keyboardInputField, targetField, keyboardFrame, capsLock));
        row4.add(capsButton);
    
        // Panels zur Haupttastatur hinzufügen
        mainPanel.add(row1);
        mainPanel.add(row2);
        mainPanel.add(row3);
        mainPanel.add(row4);
        keyboardFrame.add(mainPanel, BorderLayout.CENTER);
    
        keyboardFrame.setLocationRelativeTo(null);
        keyboardFrame.setVisible(true);
    }
    
    /**
     * Creates a styled keyboard button with appropriate action listener.
     * 
     * @param key The key text to display
     * @param keyboardInputField Field showing current input
     * @param targetField Field that will receive final input
     * @param keyboardFrame Parent keyboard frame
     * @param capsLock Array holding caps lock state
     * @return Configured button for the on-screen keyboard
     */
    private JButton createKeyButton(String key, JTextField keyboardInputField, JTextField targetField, JFrame keyboardFrame, boolean[] capsLock) {
        JButton button = new JButton(key);
        button.setFont(new Font("Arial", Font.BOLD, 14));
    
        if (key.equals("")) {
            button.setVisible(false); // Platzhalter für "Q"
        } else if (key.equals("ENTER")) {
            button.addActionListener(e -> {
                targetField.setText(keyboardInputField.getText());
                keyboardFrame.dispose();
            });
        } else if (key.equals("<-")) {
            button.addActionListener(e -> {
                String text = keyboardInputField.getText();
                if (!text.isEmpty()) {
                    keyboardInputField.setText(text.substring(0, text.length() - 1));
                }
            });
        } else {
            button.addActionListener(e -> {
                String inputKey = capsLock[0] ? key.toUpperCase() : key.toLowerCase();
                keyboardInputField.setText(keyboardInputField.getText() + inputKey);
            });
        }
    
        return button;
    }
    
   
}