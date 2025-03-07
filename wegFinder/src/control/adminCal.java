package control;
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
import java.awt.Color;
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
        
        dashboardMenu.setFont(createFont("Arial","Font.PLAIN"));
        userMenu.setFont(createFont("Arial","Font.PLAIN"));
        roomMenu.setFont(createFont("Arial","Font.PLAIN"));

        // Menü zur Menüleiste hinzufügen
        menuBar.add(dashboardMenu);
        menuBar.add(userMenu);
        menuBar.add(roomMenu);

        
       
        this.setJMenuBar(menuBar);
    }

   
    private JMenuItem createMenuItem(String text, ActionListener actionListener) {
        JMenuItem menuItem = new JMenuItem(text);

        menuItem.setFont(createFont("Arial","Font.PLAIN"));

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

        titleLabel.setFont(createFont("Arial","Font.BOLD"));
        textLabel.setFont(createFont("Arial","Font.BOLD"));
        
      
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(textLabel);
        return panel;
    }

    private void switchToPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
    }


    

    private JPanel addUserPanel() {
        JPanel panel = new JPanel(new BorderLayout()); // Hauptpanel mit BorderLayout
        panel.setBackground(Color.white);
       
    
        // Formular-Panel mit GridLayout (Eingabefelder)
        JPanel formPanel = new JPanel(new GridLayout(4, 2));
        JLabel userLabel = new JLabel("Benutzername:");
      
        JTextField userField = new JTextField();
        JLabel passLabel = new JLabel("Passwort:");
        
        JPasswordField passField = new JPasswordField();
        JLabel roleLabel = new JLabel("Rolle:");
        
        String[] roles = {"Admin", "Mitarbeiter"};
        JComboBox<String> roleBox = new JComboBox<>(roles);
        JButton addUserButton = new JButton("Hinzufügen");
        JLabel statusLabel = new JLabel();
        //font
        userLabel.setFont(createFont("Arial","Font.PLAIN"));
        passLabel.setFont(createFont("Arial","Font.PLAIN"));
        roleLabel.setFont(createFont("Arial","Font.PLAIN"));
        addUserButton.setFont(createFont("Arial","Font.PLAIN"));

        //add components to Panel
        formPanel.add(userLabel);
        formPanel.add(userField);
        formPanel.add(passLabel);
        formPanel.add(passField);
        formPanel.add(roleLabel);
        formPanel.add(roleBox);
        formPanel.add(addUserButton);
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
    
        addUserButton.addActionListener(e -> {
            String user = userField.getText();
            String pass = new String(passField.getPassword());
            String role = (String) roleBox.getSelectedItem();
    
            if (!users.containsKey(user)) {
                users.put(user, new String[]{pass, role});
                saveUserToFile(user, pass, role);
                userListModel.addElement(user + " (" + role + ")");
                statusLabel.setText("Benutzer hinzugefügt!");
            } else {
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
    userLabel.setFont(createFont("Arial", "Font.PLAIN"));

    deleteButton.setFont(createFont("Arial", "Font.PLAIN"));

    
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
                    userListModel.addElement(parts[0] + " (" + parts[2] + ")");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   
    private Font createFont(String art, String font){
        if(art.equals("Arial") && font.equals("Font.PLAIN")){
            return new Font("Arial",Font.PLAIN,20);
        }else if(art.equals("Arial") && font.equals("Font.BOLD")){
            return new Font("Arial",Font.BOLD,20);
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
                        //startTimer();
                        userListModel.removeElement(usernameValue);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Fehler beim Lesen/Schreiben der Datei: " + e.getMessage());
        }
    
        // Ersetze die Originaldatei mit der neuen Datei
        if (!inputFile.delete()) {
            System.out.println("Fehler beim Löschen der Originaldatei!");
        }
        if (!tempFile.renameTo(inputFile)) {
            System.out.println("Fehler beim Umbenennen der temporären Datei!");
        }
        
    }
    
    private void startTimer() {
        int time = 3; // 3 Sekunden warten
    
        while (time > 0) {
            try {
                Thread.sleep(1000); // 1 Sekunde warten
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            time--; // Zeit verringern
            System.out.println("Wartezeit: " + time + " Sekunden...");
        }
    
        // Nach 3 Sekunden wird loadUsersFromFile() aufgerufen
        //
        System.out.println("Benutzerdaten geladen!");
    }
    

    
}
     

    

