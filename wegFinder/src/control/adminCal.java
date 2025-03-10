package control;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;
import java.io.*;
import javax.swing.JMenu;
import java.util.HashMap;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class adminCal extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;
    private HashMap<String, String[]> users;
    private DefaultListModel<String> userListModel;
    private JList<String> userList;

    protected void adminPage() {
        users = new HashMap<>();
        userListModel = new DefaultListModel<>();
        loadUsersFromFile();

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
    
    
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar(); // Menüleiste erstellen

        // Dashboard als Dropdown-Menü (verhindert Design-Probleme)
        JMenu dashboardMenu = new JMenu("Dashboard");
        dashboardMenu.add(createMenuItem("Dashboard",  e -> switchToPanel("Admin Dashboard")));
        dashboardMenu.add(createMenuItem("Help", e -> switchToPanel("Help")));

        // User Management mit Dropdown
        JMenu userMenu = new JMenu("User Management");
        
        userMenu.add(createMenuItem("Add User",  e -> switchToPanel("Add User")));
        userMenu.add(createMenuItem("Update User",  e -> switchToPanel("Update User")));
        userMenu.add(createMenuItem("Delete User", e -> switchToPanel("Delete User")));

        // Room Management mit Dropdown
        JMenu roomMenu = new JMenu("Room Management");
        roomMenu.add(createMenuItem("Add Room",  e -> switchToPanel("Add Room")));
        roomMenu.add(createMenuItem("Update Room",  e -> switchToPanel("Update Room")));
        roomMenu.add(createMenuItem("Delete Room", e ->  switchToPanel("Delete Room")));
        roomMenu.add(createMenuItem("Lock Room", e ->  switchToPanel("Lock Room")));
        roomMenu.add(createMenuItem("Lock Coridoor", e ->  switchToPanel("Lock Coridoor")));
        
        dashboardMenu.setFont(createFont("Arial","Font.PLAIN","20"));
        userMenu.setFont(createFont("Arial","Font.PLAIN","20"));
        roomMenu.setFont(createFont("Arial","Font.PLAIN","20"));

        // Menü zur Menüleiste hinzufügen
        menuBar.add(dashboardMenu);
        menuBar.add(userMenu);
        menuBar.add(roomMenu);

        
       
        this.setJMenuBar(menuBar);
    }

   
    private JMenuItem createMenuItem(String text, ActionListener actionListener) {
        JMenuItem menuItem = new JMenuItem(text);

        menuItem.setFont(createFont("Arial","Font.PLAIN","20"));

        menuItem.setBackground(new Color(255,255,255));
        menuItem.addActionListener(actionListener);
        return menuItem;
    }


   
//Panels for all Gui on Dashboard
    private JPanel adminDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        JLabel titleLabel = new JLabel("Admin Dashboard", SwingConstants.CENTER);
        
       
        JTextField textLabel = new JTextField("Hier kommt noch das MapGui hien task!!!!",SwingConstants.CENTER);
        
        textLabel.setEditable(false);
        textLabel.setBounds(0, 0, 60, 40);

        titleLabel.setFont(createFont("Arial","Font.BOLD","20"));
        textLabel.setFont(createFont("Arial","Font.BOLD","20"));
        
      
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(textLabel);
        return panel;
    }

    private void switchToPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
    }


    

    private JPanel addUserPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10)); // Abstand hinzugefügt
        panel.setBackground(Color.white);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Rand für besseren Look
    
        // Formular-Panel mit GridBagLayout für flexiblere Anordnung
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5); // Abstand zwischen Komponenten
        
        JLabel userLabel = new JLabel("Benutzername:");
        JTextField userField = new JTextField(15);
        JLabel passLabel = new JLabel("Passwort:");
        JPasswordField passField = new JPasswordField(15);
        JLabel roleLabel = new JLabel("Rolle:");
        String[] roles = {"Admin", "Mitarbeiter"};
        JComboBox<String> roleBox = new JComboBox<>(roles);
        JButton addUserButton = new JButton("Hinzufügen");
        JLabel statusLabel = new JLabel();
        
        // Fonts setzen
        Font labelFont = new Font("Arial", Font.PLAIN, 14);
        userLabel.setFont(labelFont);
        passLabel.setFont(labelFont);
        roleLabel.setFont(labelFont);
        addUserButton.setFont(new Font("Arial", Font.BOLD, 14));
        
        // Komponenten zum formPanel hinzufügen
        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(userLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0; formPanel.add(userField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(passLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1; formPanel.add(passField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(roleLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 2; formPanel.add(roleBox, gbc);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; formPanel.add(addUserButton, gbc);
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; formPanel.add(statusLabel, gbc);
        
        // Benutzerliste mit ScrollPane
        userList = new JList<>(userListModel);
        JScrollPane scrollPane = new JScrollPane(userList);
        scrollPane.setPreferredSize(new Dimension(200, 150));
        
        JPanel listPanel = new JPanel(new BorderLayout());
        JLabel listLabel = new JLabel("Benutzerliste:");
        listLabel.setFont(createFont("Arial", "Font.BOLD","14"));
        listPanel.add(listLabel, BorderLayout.NORTH);
        listPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Hauptpanel Anordnung
        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(listPanel, BorderLayout.EAST);
        
        // ActionListener für den Button
        addUserButton.addActionListener(e -> {
            String user = userField.getText().trim();
            String pass = new String(passField.getPassword()).trim();
            String role = (String) roleBox.getSelectedItem();
    
            if (user.isEmpty() || pass.isEmpty()) {
                statusLabel.setForeground(Color.RED);
                statusLabel.setText("Benutzername und Passwort dürfen nicht leer sein!");
                return;
            }
    
            if (!users.containsKey(user)) {
                users.put(user, new String[]{pass, role});
                saveUserToFile(user, pass, role);
                userListModel.addElement(user + " (" + role + ")");
                statusLabel.setForeground(Color.GREEN);
                statusLabel.setText("Benutzer hinzugefügt!");
            } else {
                statusLabel.setForeground(Color.RED);
                statusLabel.setText("Benutzer existiert bereits!");
            }
        });
        
        return panel;
    }
    
    
private JPanel deleteUserPanel() {

    JPanel panel = new JPanel(new BorderLayout()); // Hauptpanel mit BorderLayout
    panel.setBackground(Color.white);

    // Formular-Panel mit GridLayout (Eingabefelder)
    JPanel formPanel = new JPanel(new GridLayout(4, 2));
    JLabel userLabel = new JLabel("Benutzername:");

    JTextField userField = new JTextField();
    JButton deleteButton = new JButton("Löschen");
    JLabel statusLabel = new JLabel();
    //font
    userLabel.setFont(createFont("Arial", "Font.PLAIN", "20"));

    deleteButton.setFont(createFont("Arial", "Font.PLAIN","20"));

    
    deleteButton.addActionListener(e -> {
        String selectedUser = userField.getText();
        if (selectedUser != null) {
            deleteUserFunction(selectedUser);
            System.out.print(selectedUser);
        }
    });

    //add components to Panel
    formPanel.add(userLabel);
    formPanel.add(userField);
    formPanel.add(deleteButton);
    formPanel.add(statusLabel);

    // Benutzerliste mit Scrollpane
    userList = new JList<>(userListModel);
    JScrollPane scrollPane = new JScrollPane(userList);

    // Panel für Benutzerliste rechts
    JPanel listPanel = new JPanel(new BorderLayout());
    listPanel.add(new JLabel("Benutzerliste:"), BorderLayout.NORTH);
    listPanel.add(scrollPane, BorderLayout.CENTER);

    // Hauptpanel Anordnung
    panel.add(formPanel, BorderLayout.CENTER); // Formular in die Mitte
    panel.add(listPanel, BorderLayout.EAST);   // Liste nach rechts

    return panel;
}

    private JPanel updateUserPanel(){
        JPanel panel = new JPanel(new BorderLayout());
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
                    userListModel.addElement(parts[0] +", " +  parts[1] +", (" + parts[2] + ")");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   
    private Font createFont(String art, String font, String size){
        if(art.equals("Arial") && font.equals("Font.PLAIN") && size.equals("20")){
            return new Font("Arial",Font.PLAIN,20);
        }else if(art.equals("Arial") && font.equals("Font.BOLD") && size.equals("20")){
            return new Font("Arial",Font.BOLD,20);
        }else if(art.equals("Arial") && font.equals("Font.BOLD") && size.equals("14")){

        }
        return null;
    }

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
     

    

