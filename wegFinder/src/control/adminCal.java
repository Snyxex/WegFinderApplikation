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

    private JList<String> userList;
    private userData userDataManager;
    

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
            userDataManager.deleteUser(selectedUser);
            System.out.print(selectedUser);
        } else{
            statusLabel.setText("Bitte Benutzername eingeben!");
        }
    });

    //add components to Panel
    formPanel.add(userLabel);
    formPanel.add(userField);
    formPanel.add(deleteButton);
    formPanel.add(statusLabel);

    // Benutzerliste mit Scrollpane
    JList<String> userList = new JList<>(userDataManager.getUserListModel());
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

   
   
   
    private Font createFont(String art, String font, String size){
        if(art.equals("Arial") && font.equals("Font.PLAIN") && size.equals("20")){
            return new Font("Arial",Font.PLAIN,20);
        }else if(art.equals("Arial") && font.equals("Font.BOLD") && size.equals("20")){
            return new Font("Arial",Font.BOLD,20);
        }else if(art.equals("Arial") && font.equals("Font.BOLD") && size.equals("14")){

        }
        return null;
    }

    
}
     

    

