package com.wegapplikation.config.view;

import com.wegapplikation.config.CustomKeyboard;
import com.wegapplikation.config.controller.AdminCal;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class AdminGUI extends JFrame {
    private AdminCal adminCal;
    private JTable userTable, roomTable;
    private JTextField usernameField, roomIdField, roomDesignationField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;
    private JCheckBox lockedCheckBox;
    private JButton addUserButton, updateUserButton, deleteUserButton, clearUserButton;
    private JButton addRoomButton, updateRoomButton, deleteRoomButton, clearRoomButton;
    private JPanel contentPanel;
    private CardLayout cardLayout;
    private JButton homeButton, userManagementButton, roomManagementButton, floorManagementButton,helpButton;
    private String loggedInUser = LoginGUI.textField.getText(); // Standardwert, bis Login implementiert ist

    public void  AdminGUI() {
        adminCal = new AdminCal();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Admin Dashboard");
        setSize(900, 600);
        setMinimumSize(new Dimension(700, 500));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 242, 245));

        // Sidebar
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(26, 36, 47));
        sidebar.setPreferredSize(new Dimension(200, 0));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(new EmptyBorder(20, 10, 20, 10));

        JLabel logoLabel = new JLabel("Admin Dashboard");
        logoLabel.setForeground(Color.WHITE);
        logoLabel.setFont(new Font("Arial", Font.BOLD, 20));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(logoLabel);
        sidebar.add(Box.createVerticalStrut(30));

        homeButton = createNavButton("Startseite");
        userManagementButton = createNavButton("Benutzerverwaltung");
        roomManagementButton = createNavButton("Raumverwaltung");
        floorManagementButton = createNavButton("Etagenverwaltung");
        helpButton = createNavButton("Hilfe");
        userManagementButton.setBackground(new Color(46, 204, 113));
        sidebar.add(homeButton);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(userManagementButton);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(roomManagementButton);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(floorManagementButton);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(helpButton);
        sidebar.add(Box.createVerticalGlue());

        JLabel versionLabel = new JLabel("v5.2.1");
        versionLabel.setForeground(new Color(150, 150, 150));
        versionLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        versionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(versionLabel);

        // Content Panel mit CardLayout
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(new Color(240, 242, 245));
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // User Management Panel
        contentPanel.add(createUserManagementPanel(), "Benutzerverwaltung");

        // Room Management Panel
        contentPanel.add(createRoomManagementPanel(), "Raumverwaltung");

        // Floor Management Panel
        contentPanel.add(createFloorManagementPanel(), "Etagenverwaltung");

        // Home Panel
        JPanel homePanel = new JPanel(new BorderLayout());
        homePanel.setBackground(Color.WHITE);
        homePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(20, 20, 20, 20)));
        JLabel welcomeLabel = new JLabel("Willkommen im Admin Dashboard", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        homePanel.add(welcomeLabel, BorderLayout.CENTER);
        contentPanel.add(homePanel, "Startseite");

        // Help Panel
        JPanel helpPanel = new JPanel(new BorderLayout());
        helpPanel.setBackground(Color.WHITE);
        helpPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(20, 20, 20, 20)));
        JTextArea helpText = new JTextArea(
                "Admin-Dashboard-Hilfe\n\n" +
                "1. Startseite: Übersicht über das Dashboard.\n" +
                "2. Benutzerverwaltung: Hinzufügen, Aktualisieren oder Löschen von Benutzern mit Rollen (Admin oder Mitarbeiter).\n" +
                "3. Raumverwaltung: Hinzufügen, Aktualisieren oder Löschen von Räumen mit Bezeichnung und Sperrstatus.\n" +
                "4. Hilfe: Dieser Abschnitt bietet Anleitungen.\n\n" +
                "Hinweis: Für Aktionen wie Hinzufügen, Aktualisieren oder Löschen ist Ihr Benutzerpasswort erforderlich.\n" +
                "Für Unterstützung wenden Sie sich an den Administrator."
        );
        helpText.setFont(new Font("Arial", Font.PLAIN, 14));
        helpText.setEditable(false);
        helpText.setBackground(Color.WHITE);
        helpPanel.add(new JScrollPane(helpText), BorderLayout.CENTER);
        contentPanel.add(helpPanel, "Hilfe");

        // Layout zusammenfügen
        mainPanel.add(sidebar, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Navigation Aktionen
        homeButton.addActionListener(e -> {
            setActiveButton(homeButton);
            cardLayout.show(contentPanel, "Startseite");
        });
        userManagementButton.addActionListener(e -> {
            setActiveButton(userManagementButton);
            cardLayout.show(contentPanel, "Benutzerverwaltung");
            refreshUserTable();
        });
        roomManagementButton.addActionListener(e -> {
            setActiveButton(roomManagementButton);
            cardLayout.show(contentPanel, "Raumverwaltung");
            refreshRoomTable();
        });
        floorManagementButton.addActionListener(e -> {
            setActiveButton(floorManagementButton);
            cardLayout.show(contentPanel, "Etagenverwaltung");
            refreshFloorTable();
        });
        helpButton.addActionListener(e -> {
            setActiveButton(helpButton);
            cardLayout.show(contentPanel, "Hilfe");
        });

        add(mainPanel);
    }

    private JPanel createUserManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(240, 242, 245));

        // Eingabepanel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(15, 15, 15, 15)));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel usernameLabel = new JLabel("Benutzername:");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField = new JTextField(20);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(5, 5, 5, 5)));
                usernameField.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                       new  CustomKeyboard(usernameField);
                    }
                });

        JLabel passwordLabel = new JLabel("Passwort:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(5, 5, 5, 5)));

                passwordField.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                       new  CustomKeyboard(passwordField);
                    }
                });

        JLabel roleLabel = new JLabel("Rolle:");
        roleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        String[] roles = {"Admin", "Mitarbeiter"};
        roleComboBox = new JComboBox<>(roles);
        roleComboBox.setFont(new Font("Arial", Font.PLAIN, 14));

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(usernameLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(usernameField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(passwordField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(roleLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(roleComboBox, gbc);

        // Button-Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        buttonPanel.setBackground(new Color(240, 242, 245));
        addUserButton = createStyledButton("Hinzufügen", new Color(46, 204, 113), "add.png");
        updateUserButton = createStyledButton("Aktualisieren", new Color(52, 152, 219), "update.png");
        deleteUserButton = createStyledButton("Löschen", new Color(231, 76, 60), "delete.png");
        clearUserButton = createStyledButton("Zurücksetzen", new Color(149, 165, 166), "clear.png");
        buttonPanel.add(addUserButton);
        buttonPanel.add(updateUserButton);
        buttonPanel.add(deleteUserButton);
        buttonPanel.add(clearUserButton);

        // Tabelle
        String[] columns = {"Benutzername", "Passwort", "Rolle"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        userTable = new JTable(tableModel);
        userTable.setRowHeight(30);
        userTable.setFont(new Font("Arial", Font.PLAIN, 14));
        userTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        userTable.getTableHeader().setBackground(new Color(26, 36, 47));
        userTable.getTableHeader().setForeground(Color.WHITE);
        userTable.setGridColor(new Color(200, 200, 200));
        userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        userTable.setIntercellSpacing(new Dimension(10, 10));
        refreshUserTable();
        JScrollPane tableScrollPane = new JScrollPane(userTable);
        tableScrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        // User Management Layout
        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(tableScrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Button-Aktionen
        addUserButton.addActionListener(e -> {
            if (!promptUserPassword()) {
                JOptionPane.showMessageDialog(this, "Falsches Benutzerpasswort.", "Authentifizierung fehlgeschlagen", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            String role = (String) roleComboBox.getSelectedItem();
            if (!username.isEmpty() && !password.isEmpty()) {
                try {
                    adminCal.addUser(username, password, role);
                    System.out.println("Benutzer " + loggedInUser + " hat Benutzer hinzugefügt: " + username);
                    refreshUserTable();
                    clearUserFields();
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(this, "Fehler: " + ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Bitte füllen Sie alle Felder aus", "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        });

        updateUserButton.addActionListener(e -> {
            if (!promptUserPassword()) {
                JOptionPane.showMessageDialog(this, "Falsches Benutzerpasswort.", "Authentifizierung fehlgeschlagen", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int selectedRow = userTable.getSelectedRow();
            if (selectedRow >= 0) {
                String oldUsername = userTable.getValueAt(selectedRow, 0).toString();
                String username = usernameField.getText().trim();
                String password = new String(passwordField.getPassword()).trim();
                String role = (String) roleComboBox.getSelectedItem();
                if (!username.isEmpty() && !password.isEmpty()) {
                    try {
                        adminCal.updateUser(oldUsername, username, password, role);
                        System.out.println("Benutzer " + loggedInUser + " hat Benutzer aktualisiert: " + username);
                        refreshUserTable();
                        clearUserFields();
                    } catch (IllegalArgumentException ex) {
                        JOptionPane.showMessageDialog(this, "Fehler: " + ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Bitte füllen Sie alle Felder aus", "Fehler", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Bitte wählen Sie einen Benutzer zum Aktualisieren aus", "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        });

        deleteUserButton.addActionListener(e -> {
            if (!promptUserPassword()) {
                JOptionPane.showMessageDialog(this, "Falsches Benutzerpasswort.", "Authentifizierung fehlgeschlagen", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int selectedRow = userTable.getSelectedRow();
            if (selectedRow >= 0) {
                String username = userTable.getValueAt(selectedRow, 0).toString();
                int confirm = JOptionPane.showConfirmDialog(this, "Sind Sie sicher, dass Sie diesen Benutzer löschen möchten?", "Löschen bestätigen", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    adminCal.deleteUser(username);
                    System.out.println("Benutzer " + loggedInUser + " hat Benutzer gelöscht: " + username);
                    refreshUserTable();
                    clearUserFields();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Bitte wählen Sie einen Benutzer zum Löschen aus", "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        });

        clearUserButton.addActionListener(e -> clearUserFields());

        userTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = userTable.getSelectedRow();
            if (selectedRow >= 0) {
                String username = userTable.getValueAt(selectedRow, 0).toString();
                Object[] user = adminCal.getUserByUsername(username);
                if (user != null) {
                    usernameField.setText((String) user[0]);
                    passwordField.setText((String) user[1]); // Echtes Passwort
                    roleComboBox.setSelectedItem((String) user[2]);
                }
            }
        });

        return panel;
    }

    private JPanel createRoomManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(240, 242, 245));

        // Eingabepanel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(15, 15, 15, 15)));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel idLabel = new JLabel("Raum-ID:");
        idLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        roomIdField = new JTextField(20);
        roomIdField.setFont(new Font("Arial", Font.PLAIN, 14));
        roomIdField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(5, 5, 5, 5)));

                roomIdField.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                       new  CustomKeyboard(roomIdField);
                    }
                });

        JLabel designationLabel = new JLabel("Bezeichnung:");
        designationLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        roomDesignationField = new JTextField(20);
        roomDesignationField.setFont(new Font("Arial", Font.PLAIN, 14));
        roomDesignationField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(5, 5, 5, 5)));

                roomDesignationField.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                       new  CustomKeyboard(roomDesignationField);
                    }
                });

        JLabel lockedLabel = new JLabel("Gesperrt:");
        lockedLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        lockedCheckBox = new JCheckBox();
        lockedCheckBox.setFont(new Font("Arial", Font.PLAIN, 14));
        lockedCheckBox.setBackground(Color.WHITE);

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(idLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(roomIdField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(designationLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(roomDesignationField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(lockedLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(lockedCheckBox, gbc);

        // Button-Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        buttonPanel.setBackground(new Color(240, 242, 245));
        addRoomButton = createStyledButton("Hinzufügen", new Color(46, 204, 113), "add.png");
        updateRoomButton = createStyledButton("Aktualisieren", new Color(52, 152, 219), "update.png");
        deleteRoomButton = createStyledButton("Löschen", new Color(231, 76, 60), "delete.png");
        clearRoomButton = createStyledButton("Zurücksetzen", new Color(149, 165, 166), "clear.png");
        buttonPanel.add(addRoomButton);
        buttonPanel.add(updateRoomButton);
        buttonPanel.add(deleteRoomButton);
        buttonPanel.add(clearRoomButton);

        // Tabelle
        String[] columns = {"ID", "Bezeichnung", "Gesperrt"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        roomTable = new JTable(tableModel);
        roomTable.setRowHeight(30);
        roomTable.setFont(new Font("Arial", Font.PLAIN, 14));
        roomTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        roomTable.getTableHeader().setBackground(new Color(26, 36, 47));
        roomTable.getTableHeader().setForeground(Color.WHITE);
        roomTable.setGridColor(new Color(200, 200, 200));
        roomTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        roomTable.setIntercellSpacing(new Dimension(10, 10));
        refreshRoomTable();
        JScrollPane tableScrollPane = new JScrollPane(roomTable);
        tableScrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        // Room Management Layout
        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(tableScrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Button-Aktionen
        addRoomButton.addActionListener(e -> {
            if (!promptUserPassword()) {
                JOptionPane.showMessageDialog(this, "Falsches Benutzerpasswort.", "Authentifizierung fehlgeschlagen", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String idStr = roomIdField.getText().trim();
            String designation = roomDesignationField.getText().trim();
            boolean locked = lockedCheckBox.isSelected();
            if (!idStr.isEmpty() && !designation.isEmpty()) {
                try {
                    int id = Integer.parseInt(idStr);
                    if (id <= 0) throw new IllegalArgumentException("Raum-ID muss eine positive Ganzzahl sein.");
                    adminCal.addRoom(id, designation, locked);
                    System.out.println("Benutzer " + loggedInUser + " hat Raum hinzugefügt: " + designation);
                    refreshRoomTable();
                    clearRoomFields();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Raum-ID muss eine gültige Zahl sein.", "Fehler", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(this, "Fehler: " + ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Bitte füllen Sie alle Felder aus", "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        });

        updateRoomButton.addActionListener(e -> {
            if (!promptUserPassword()) {
                JOptionPane.showMessageDialog(this, "Falsches Benutzerpasswort.", "Authentifizierung fehlgeschlagen", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int selectedRow = roomTable.getSelectedRow();
            if (selectedRow >= 0) {
                int oldId = Integer.parseInt(roomTable.getValueAt(selectedRow, 0).toString());
                String idStr = roomIdField.getText().trim();
                String designation = roomDesignationField.getText().trim();
                boolean locked = lockedCheckBox.isSelected();
                if (!idStr.isEmpty() && !designation.isEmpty()) {
                    try {
                        int newId = Integer.parseInt(idStr);
                        if (newId <= 0) throw new IllegalArgumentException("Raum-ID muss eine positive Ganzzahl sein.");
                        adminCal.updateRoom(oldId, newId, designation, locked);
                        System.out.println("Benutzer " + loggedInUser + " hat Raum aktualisiert: " + designation);
                        refreshRoomTable();
                        clearRoomFields();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Raum-ID muss eine gültige Zahl sein.", "Fehler", JOptionPane.ERROR_MESSAGE);
                    } catch (IllegalArgumentException ex) {
                        JOptionPane.showMessageDialog(this, "Fehler: " + ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Bitte füllen Sie alle Felder aus", "Fehler", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Bitte wählen Sie einen Raum zum Aktualisieren aus", "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        });

        deleteRoomButton.addActionListener(e -> {
            if (!promptUserPassword()) {
                JOptionPane.showMessageDialog(this, "Falsches Benutzerpasswort.", "Authentifizierung fehlgeschlagen", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int selectedRow = roomTable.getSelectedRow();
            if (selectedRow >= 0) {
                int id = Integer.parseInt(roomTable.getValueAt(selectedRow, 0).toString());
                String designation = roomTable.getValueAt(selectedRow, 1).toString();
                int confirm = JOptionPane.showConfirmDialog(this, "Sind Sie sicher, dass Sie diesen Raum löschen möchten?", "Löschen bestätigen", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    adminCal.deleteRoom(id);
                    System.out.println("Benutzer " + loggedInUser + " hat Raum gelöscht: " + designation);
                    refreshRoomTable();
                    clearRoomFields();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Bitte wählen Sie einen Raum zum Löschen aus", "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        });

        clearRoomButton.addActionListener(e -> clearRoomFields());

        roomTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = roomTable.getSelectedRow();
            if (selectedRow >= 0) {
                roomIdField.setText(roomTable.getValueAt(selectedRow, 0).toString());
                roomDesignationField.setText(roomTable.getValueAt(selectedRow, 1).toString());
                lockedCheckBox.setSelected((Boolean) roomTable.getValueAt(selectedRow, 2));
            }
        });

        return panel;
    }
    
    private JPanel createFloorManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(240, 242, 245));
    
        // Eingabepanel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(15, 15, 15, 15)));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
    
        JLabel idLabel = new JLabel("Flur-ID:");
        idLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        JTextField floorIdField = new JTextField(20);
        floorIdField.setFont(new Font("Arial", Font.PLAIN, 14));
        floorIdField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(5, 5, 5, 5)));
    
        JLabel lockedLabel = new JLabel("Gesperrt:");
        lockedLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        JCheckBox lockedCheckBox = new JCheckBox();
        lockedCheckBox.setFont(new Font("Arial", Font.PLAIN, 14));
        lockedCheckBox.setBackground(Color.WHITE);
    
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(idLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(floorIdField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(lockedLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(lockedCheckBox, gbc);
    
        // Button-Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        buttonPanel.setBackground(new Color(240, 242, 245));
        JButton addFloorButton = createStyledButton("Hinzufügen", new Color(46, 204, 113), "add.png");
        JButton updateFloorButton = createStyledButton("Aktualisieren", new Color(52, 152, 219), "update.png");
        JButton deleteFloorButton = createStyledButton("Löschen", new Color(231, 76, 60), "delete.png");
        JButton clearFloorButton = createStyledButton("Zurücksetzen", new Color(149, 165, 166), "clear.png");
        buttonPanel.add(addFloorButton);
        buttonPanel.add(updateFloorButton);
        buttonPanel.add(deleteFloorButton);
        buttonPanel.add(clearFloorButton);
    
        // Tabelle
        String[] columns = {"ID", "Gesperrt"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable floorTable = new JTable(tableModel);
        floorTable.setRowHeight(30);
        floorTable.setFont(new Font("Arial", Font.PLAIN, 14));
        floorTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        floorTable.getTableHeader().setBackground(new Color(26, 36, 47));
        floorTable.getTableHeader().setForeground(Color.WHITE);
        floorTable.setGridColor(new Color(200, 200, 200));
        floorTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        floorTable.setIntercellSpacing(new Dimension(10, 10));
        refreshFloorTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(floorTable);
        tableScrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
    
        // Floor Management Layout
        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(tableScrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
    
        // Button-Aktionen
        addFloorButton.addActionListener(e -> {
            String idStr = floorIdField.getText().trim();
            boolean locked = lockedCheckBox.isSelected();
            if (!idStr.isEmpty()) {
                try {
                    int id = Integer.parseInt(idStr);
                    adminCal.addFloor(id, locked); // Hier die Bezeichnung weggelassen
                    refreshFloorTable(tableModel);
                    clearFloorFields(floorIdField, lockedCheckBox);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Flur-ID muss eine gültige Zahl sein.", "Fehler", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(this, "Fehler: " + ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Bitte füllen Sie alle Felder aus", "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        });
    
        updateFloorButton.addActionListener(e -> {
            int selectedRow = floorTable.getSelectedRow();
            if (selectedRow >= 0) {
                int oldId = Integer.parseInt(floorTable.getValueAt(selectedRow, 0).toString());
                String idStr = floorIdField.getText().trim();
                boolean locked = lockedCheckBox.isSelected();
                if (!idStr.isEmpty()) {
                    try {
                        int newId = Integer.parseInt(idStr);
                        adminCal.updateFloor(oldId, newId, locked); // Hier die Bezeichnung weggelassen
                        refreshFloorTable(tableModel);
                        clearFloorFields(floorIdField, lockedCheckBox);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Flur-ID muss eine gültige Zahl sein.", "Fehler", JOptionPane.ERROR_MESSAGE);
                    } catch (IllegalArgumentException ex) {
                        JOptionPane.showMessageDialog(this, "Fehler: " + ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Bitte füllen Sie alle Felder aus", "Fehler", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Bitte wählen Sie einen Flur zum Aktualisieren aus", "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        });
    
        deleteFloorButton.addActionListener(e -> {
            int selectedRow = floorTable.getSelectedRow();
            if (selectedRow >= 0) {
                int id = Integer.parseInt(floorTable.getValueAt(selectedRow, 0).toString());
                int confirm = JOptionPane.showConfirmDialog(this, "Sind Sie sicher, dass Sie diesen Flur löschen möchten?", "Löschen bestätigen", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    adminCal.deleteFloor(id);
                    refreshFloorTable(tableModel);
                    clearFloorFields(floorIdField, lockedCheckBox);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Bitte wählen Sie einen Flur zum Löschen aus", "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        });
    
        clearFloorButton.addActionListener(e -> clearFloorFields(floorIdField, lockedCheckBox));
    
        floorTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = floorTable.getSelectedRow();
            if (selectedRow >= 0) {
                floorIdField.setText(floorTable.getValueAt(selectedRow, 0).toString());
                lockedCheckBox.setSelected((Boolean) floorTable.getValueAt(selectedRow, 1));
            }
        });
    
        return panel;
    }
    
    private void clearFloorFields(JTextField floorIdField, JCheckBox lockedCheckBox) {
        floorIdField.setText("");
        lockedCheckBox.setSelected(false);
    }
    private void refreshFloorTable(DefaultTableModel model) {
        model.setRowCount(0); // Vorherige Zeilen löschen
        List<Object[]> floors = adminCal.getAllFloors();
        for (Object[] floor : floors) {
            model.addRow(floor);
        }
    }
    
    private void clearFloorFields(JTextField floorIdField, JTextField floorDesignationField, JCheckBox lockedCheckBox) {
        floorIdField.setText("");
        floorDesignationField.setText("");
        lockedCheckBox.setSelected(false);
    }

    private boolean promptUserPassword() {
        // Neues JFrame für die Passwortabfrage
        JFrame frame = new JFrame("Benutzer-Authentifizierung");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        
        // Passwortfeld
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new CustomKeyboard(passwordField);
            }
        });
        
        // Panel für die Eingabe
        JPanel panel = new JPanel();
        panel.add(new JLabel("Ihr Passwort eingeben:"));
        panel.add(passwordField);
        frame.add(panel, BorderLayout.CENTER);
        
        // Button-Panel
        JPanel buttonPanel = new JPanel();
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Abbrechen");
        
        // Aktion für OK-Button
        okButton.addActionListener(e -> {
            String enteredPassword = new String(passwordField.getPassword());
            boolean valid = adminCal.verifyPassword(loggedInUser, enteredPassword);
            System.out.println("Passwortprüfung für " + loggedInUser + ": " + (valid ? "Erfolgreich" : "Fehlgeschlagen"));
            frame.dispose(); // Schließe das JFrame
        });
        
        // Aktion für Abbrechen-Button
        cancelButton.addActionListener(e -> frame.dispose());
        
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        
        // JFrame-Einstellungen
        frame.pack();
        frame.setLocationRelativeTo(this);
        frame.setVisible(true);
        
        // Rückgabe basierend auf dem Ergebnis
        return okButton.isEnabled(); // Annahme: Wenn OK geklickt wurde, ist der Dialog geschlossen
    }

    private JButton createNavButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(26, 36, 47));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (button.getBackground().equals(new Color(26, 36, 47))) {
                    button.setBackground(new Color(46, 56, 67));
                }
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (button.getBackground().equals(new Color(46, 56, 67))) {
                    button.setBackground(new Color(26, 36, 47));
                }
            }
        });
        return button;
    }

    private JButton createStyledButton(String text, Color bgColor, String iconPath) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
        return button;
    }

    private void setActiveButton(JButton activeButton) {
        homeButton.setBackground(new Color(26, 36, 47));
        userManagementButton.setBackground(new Color(26, 36, 47));
        roomManagementButton.setBackground(new Color(26, 36, 47));
        floorManagementButton.setBackground(new Color(26, 36, 47));
        
        helpButton.setBackground(new Color(26, 36, 47));
        activeButton.setBackground(new Color(46, 204, 113));
    }

    private void refreshUserTable() {
        DefaultTableModel model = (DefaultTableModel) userTable.getModel();
        model.setRowCount(0); // Vorherige Zeilen löschen
        List<Object[]> users = adminCal.getAllUsers();
        System.out.println("AdminGUI: Anzahl Nutzer für Tabelle: " + users.size());
        for (Object[] user : users) {
            String username = (String) user[0];
            String password = (String) user[1]; // Bereits maskiert von AdminCal
            String role = (String) user[2];
            model.addRow(new Object[]{username, password, role});
            System.out.println("AdminGUI: Zeile hinzugefügt: " + username + "," + password + "," + role);
        }
    }

    private void refreshRoomTable() {
        DefaultTableModel model = (DefaultTableModel) roomTable.getModel();
        model.setRowCount(0);
        List<Object[]> rooms = adminCal.getAllRooms();
        for (Object[] room : rooms) {
            model.addRow(room);
        }
    }

    private void refreshFloorTable() {
       
    }

    private void clearUserFields() {
        usernameField.setText("");
        passwordField.setText("");
        roleComboBox.setSelectedIndex(0);
    }

    private void clearRoomFields() {
        roomIdField.setText("");
        roomDesignationField.setText("");
        lockedCheckBox.setSelected(false);
    }


}