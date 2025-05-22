package com.wegapplikation.controller;

import com.wegapplikation.CustomKeyboard;
import com.wegapplikation.model.RoomData;
import com.wegapplikation.model.UserData;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

    public String logedinAdmin = "jason"; // This should ideally be passed in or managed more securely

    /**
     * Initializes and displays the admin dashboard.
     * Sets up the main window, menu bar, and all management panels.
     */
    protected void adminPage() {
        userDataManager = new UserData();
        roomDataManager = new RoomData();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700); // Slightly larger window
        setTitle("Admin Dashboard - WegApplikation");
        setLocationRelativeTo(null); // Center the window

        createMenuBar();

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(new Color(240, 240, 240)); // Light grey background for main content

        // Panels hinzufügen
        mainPanel.add(createAdminDashboardPanel(), "Admin Dashboard");
        mainPanel.add(createAddUserPanel(), "Add User");
        mainPanel.add(createDeleteUserPanel(), "Delete User");
        mainPanel.add(createUpdateUserPanel(), "Update User");
        mainPanel.add(createUpdateRoomPanel(), "Update Room");
        mainPanel.add(createLockCorridorPanel(), "Lock Corridor");
        mainPanel.add(createHelpPanel(), "Help");

        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    /**
     * Creates and configures the menu bar with all navigation options.
     * Sets up Dashboard, User Management, and Room Management menus.
     */
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(50, 60, 70)); // Darker background for menu bar
        menuBar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Dashboard-Menü
        JMenu dashboardMenu = createStyledMenu("Dashboard");
        dashboardMenu.add(createMenuItem("Dashboard Overview", e -> switchToPanel("Admin Dashboard")));
        dashboardMenu.add(createMenuItem("Help & Documentation", e -> switchToPanel("Help")));

        // User Management-Menü
        JMenu userMenu = createStyledMenu("User Management");
        userMenu.add(createMenuItem("Add New User", e -> switchToPanel("Add User")));
        userMenu.add(createMenuItem("Update User Details", e -> switchToPanel("Update User")));
        userMenu.add(createMenuItem("Delete User", e -> switchToPanel("Delete User")));

        // Room Management-Menü
        JMenu roomMenu = createStyledMenu("Room Management");
        roomMenu.add(createMenuItem("Update Room Details", e -> switchToPanel("Update Room")));
        roomMenu.add(createMenuItem("Lock Corridor Access", e -> switchToPanel("Lock Corridor")));

        // Sign Out Menu
        JMenu signOutMenu = createStyledMenu("Sign Out");
        signOutMenu.add(createMenuItem("Sign Out", e -> System.out.println("User logged out."))); // Placeholder for logout action

        // Add menus to the menu bar
        menuBar.add(dashboardMenu);
        menuBar.add(userMenu);
        menuBar.add(roomMenu);
        menuBar.add(Box.createHorizontalGlue()); // Pushes the Sign Out menu to the right
        menuBar.add(signOutMenu);

        setJMenuBar(menuBar);
    }

    /**
     * Creates a styled JMenu with a custom font and foreground color.
     * @param text The text for the menu.
     * @return A configured JMenu.
     */
    private JMenu createStyledMenu(String text) {
        JMenu menu = new JMenu(text);
        menu.setFont(new Font("Segoe UI", Font.BOLD, 15)); // Modern font
        menu.setForeground(Color.WHITE); // White text for dark menu bar
        return menu;
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
        menuItem.setFont(new Font("Segoe UI", Font.PLAIN, 14)); // Slightly smaller font for menu items
        menuItem.setBackground(new Color(255, 255, 255)); // White background
        menuItem.setForeground(new Color(30, 30, 30)); // Dark grey text
        menuItem.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15)); // More padding

        // Hover effect
        menuItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                menuItem.setBackground(new Color(70, 130, 180)); // Steel blue on hover
                menuItem.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                menuItem.setBackground(new Color(255, 255, 255));
                menuItem.setForeground(new Color(30, 30, 30));
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
    private JPanel createAdminDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout(20, 20)); // Increased spacing
        panel.setBackground(new Color(240, 240, 240)); // Light grey background
        panel.setBorder(new EmptyBorder(30, 30, 30, 30)); // More padding

        JLabel titleLabel = new JLabel("Admin Dashboard Overview", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 30)); // Larger, modern font
        titleLabel.setForeground(new Color(30, 30, 30)); // Dark text

        JTextArea infoArea = new JTextArea("This section will display an interactive map of the building, " +
                "showing real-time room statuses and corridor lock statuses. " +
                "Development for the map GUI is currently underway. \n\n" +
                "Stay tuned for updates!");
        infoArea.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        infoArea.setEditable(false);
        infoArea.setLineWrap(true);
        infoArea.setWrapStyleWord(true);
        infoArea.setBackground(new Color(255, 255, 255)); // White background for text area
        infoArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)), // Subtle border
                new EmptyBorder(15, 15, 15, 15) // Inner padding
        ));

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(new JScrollPane(infoArea), BorderLayout.CENTER); // Use JScrollPane for future map expansion

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
    private JPanel createAddUserPanel() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(20, 20, 20, 20)
        ));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10); // More generous spacing

        JLabel titleLabel = new JLabel("Add New User Account", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(30, 30, 30));

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = createStyledTextField(20);
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = createStyledPasswordField(20);
        JLabel roleLabel = new JLabel("Role:");
        String[] roles = {"Admin", "Employee"};
        JComboBox<String> roleBox = createStyledComboBox(roles);
        JButton addButton = createStyledButton("Add User");
        JLabel statusLabel = createStatusLabel();

        // Keyboard integration
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

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; formPanel.add(titleLabel, gbc);
        gbc.gridwidth = 1; // Reset gridwidth
        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(userLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1; formPanel.add(userField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(passLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 2; formPanel.add(passField, gbc);
        gbc.gridx = 0; gbc.gridy = 3; formPanel.add(roleLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 3; formPanel.add(roleBox, gbc);
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; formPanel.add(addButton, gbc);
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2; formPanel.add(statusLabel, gbc);

        userList = new JList<>(userDataManager.getUserListModel());
        JScrollPane scrollPane = createStyledListScrollPane(userList, "Current Users:");

        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(scrollPane, BorderLayout.EAST);

        addButton.addActionListener(e -> {
            String user = userField.getText().trim();
            String pass = new String(passField.getPassword()).trim();
            String role = (String) roleBox.getSelectedItem();

            try {
                userDataManager.addUser(user, pass, role);
                statusLabel.setForeground(new Color(46, 139, 87)); // Sea green for success
                statusLabel.setText("User added successfully!");
            } catch (IllegalArgumentException ex) {
                statusLabel.setForeground(new Color(220, 20, 60)); // Crimson for error
                statusLabel.setText(ex.getMessage());
            }
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
    private JPanel createDeleteUserPanel() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(20, 20, 20, 20)
        ));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Delete User Account", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(30, 30, 30));

        JLabel userLabel = new JLabel("Username to Delete:");
        JTextField userField = createStyledTextField(20);
        JLabel adminPassLabel = new JLabel("Admin Password:");
        JPasswordField adminPasswordField = createStyledPasswordField(20);
        JButton deleteButton = createStyledButton("Delete User");
        JLabel statusLabel = createStatusLabel();

        // Keyboard integration
        userField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new CustomKeyboard(userField);
            }
        });
        adminPasswordField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new CustomKeyboard(adminPasswordField);
            }
        });

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; formPanel.add(titleLabel, gbc);
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(userLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1; formPanel.add(userField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(adminPassLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 2; formPanel.add(adminPasswordField, gbc);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; formPanel.add(deleteButton, gbc);
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; formPanel.add(statusLabel, gbc);

        userList = new JList<>(userDataManager.getUserListModel());
        JScrollPane scrollPane = createStyledListScrollPane(userList, "Current Users:");

        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(scrollPane, BorderLayout.EAST);

        deleteButton.addActionListener(e -> {
            String selectedUser = userField.getText().trim();
            String adminPassword = new String(adminPasswordField.getPassword()).trim();

            if (!selectedUser.isEmpty()) {
                boolean isAdmin = userDataManager.loadAdminDataFromFile(logedinAdmin, adminPassword);
                if (isAdmin) {
                    userDataManager.deleteUser(selectedUser);
                    statusLabel.setForeground(new Color(46, 139, 87));
                    statusLabel.setText("User deleted successfully!");
                } else {
                    statusLabel.setForeground(new Color(220, 20, 60));
                    statusLabel.setText("Incorrect Admin Password.");
                }
            } else {
                statusLabel.setForeground(new Color(220, 20, 60));
                statusLabel.setText("Please enter a username to delete.");
            }
            userField.setText("");
            adminPasswordField.setText("");
        });

        return panel;
    }

    /**
     * Creates panel for updating existing user information.
     * Allows modification of username, password and role.
     *
     * @return Configured panel for updating users
     */
    private JPanel createUpdateUserPanel() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(20, 20, 20, 20)
        ));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Update User Details", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(30, 30, 30));

        JLabel oldUserLabel = new JLabel("Current Username:");
        JTextField oldUserField = createStyledTextField(20);
        JLabel newUserLabel = new JLabel("New Username (Optional):");
        JTextField newUserField = createStyledTextField(20);
        JLabel passLabel = new JLabel("New Password (Optional):");
        JPasswordField passField = createStyledPasswordField(20);
        JLabel roleLabel = new JLabel("New Role (Optional):");
        String[] roles = {"Admin", "Employee"};
        JComboBox<String> roleBox = createStyledComboBox(roles);
        JLabel adminPassLabel = new JLabel("Admin Password:");
        JPasswordField adminPasswordField = createStyledPasswordField(20);
        JButton updateButton = createStyledButton("Update User");
        JLabel statusLabel = createStatusLabel();

        // Keyboard integration
        oldUserField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new CustomKeyboard(oldUserField);
            }
        });
        newUserField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new CustomKeyboard(newUserField);
            }
        });
        passField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new CustomKeyboard(passField);
            }
        });
        adminPasswordField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new CustomKeyboard(adminPasswordField);
            }
        });

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; formPanel.add(titleLabel, gbc);
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(oldUserLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1; formPanel.add(oldUserField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(newUserLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 2; formPanel.add(newUserField, gbc);
        gbc.gridx = 0; gbc.gridy = 3; formPanel.add(passLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 3; formPanel.add(passField, gbc);
        gbc.gridx = 0; gbc.gridy = 4; formPanel.add(roleLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 4; formPanel.add(roleBox, gbc);
        gbc.gridx = 0; gbc.gridy = 5; formPanel.add(adminPassLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 5; formPanel.add(adminPasswordField, gbc);
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2; formPanel.add(updateButton, gbc);
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2; formPanel.add(statusLabel, gbc);

        userList = new JList<>(userDataManager.getUserListModel());
        JScrollPane scrollPane = createStyledListScrollPane(userList, "Current Users:");

        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(scrollPane, BorderLayout.EAST);

        updateButton.addActionListener(e -> {
            String adminPassword = new String(adminPasswordField.getPassword()).trim();
            String oldUser = oldUserField.getText().trim();
            String newUser = newUserField.getText().trim();
            String pass = new String(passField.getPassword()).trim();
            String role = (String) roleBox.getSelectedItem();

            boolean isAdmin = userDataManager.loadAdminDataFromFile(logedinAdmin, adminPassword);

            if (isAdmin) {
                try {
                    userDataManager.updateUser(oldUser, newUser, pass, role);
                    statusLabel.setForeground(new Color(46, 139, 87));
                    statusLabel.setText("User data updated successfully!");
                } catch (IllegalArgumentException ex) {
                    statusLabel.setForeground(new Color(220, 20, 60));
                    statusLabel.setText(ex.getMessage());
                }
            } else {
                statusLabel.setForeground(new Color(220, 20, 60));
                statusLabel.setText("Incorrect Admin Password.");
            }
            oldUserField.setText("");
            newUserField.setText("");
            passField.setText("");
            adminPasswordField.setText("");
        });
        return panel;
    }

    private JPanel createUpdateRoomPanel() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(20, 20, 20, 20)
        ));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Erstellen der Komponenten
        JLabel titleLabel = new JLabel("Update Room Information", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(30, 30, 30));

        JLabel roomNumberLabel = new JLabel("Room Number:");
        JTextField roomNumberField = createStyledTextField(15);
        JLabel textLabel = new JLabel("Room Description:");
        JTextField textField = createStyledTextField(15);
        JLabel setterLabel = new JLabel("Status:");
        String[] status = {"Open", "Locked"};
        JComboBox<String> setterBox = createStyledComboBox(status);
        JButton updateRoomButton = createStyledButton("Update Room");
        JLabel statusLabel = createStatusLabel();

        // Keyboard integration (assuming CustomKeyboard works with JTextField)
        roomNumberField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new CustomKeyboard(roomNumberField);
            }
        });
        textField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new CustomKeyboard(textField);
            }
        });

        // Hinzufügen der Komponenten zum Panel
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; formPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(roomNumberLabel, gbc);
        gbc.gridx = 1; formPanel.add(roomNumberField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(textLabel, gbc);
        gbc.gridx = 1; formPanel.add(textField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; formPanel.add(setterLabel, gbc);
        gbc.gridx = 1; formPanel.add(setterBox, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; formPanel.add(updateRoomButton, gbc);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2; formPanel.add(statusLabel, gbc);

        // Raumliste erstellen
        roomList = new JList<>(roomDataManager.getRoomListModel());
        JScrollPane scrollPane = createStyledListScrollPane(roomList, "Current Rooms:");

        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(scrollPane, BorderLayout.EAST);

        // Action Listener für den Update-Button
        updateRoomButton.addActionListener(e -> {
            String roomNumber = roomNumberField.getText().trim();
            String text = textField.getText().trim();
            boolean isLocked = setterBox.getSelectedItem().equals("Locked");

            try {
                roomDataManager.updateRoom(roomNumber, text, isLocked);
                statusLabel.setForeground(new Color(46, 139, 87));
                statusLabel.setText("Room updated successfully!");

                // Eingabefelder zurücksetzen
                roomNumberField.setText("");
                textField.setText("");
                setterBox.setSelectedIndex(0);
            } catch (IllegalArgumentException ex) {
                statusLabel.setForeground(new Color(220, 20, 60));
                statusLabel.setText(ex.getMessage());
            }
        });

        return panel;
    }

    private JPanel createLockCorridorPanel() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Lock Corridor Access", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(30, 30, 30));
        panel.add(titleLabel, BorderLayout.NORTH);

        JTextArea infoArea = new JTextArea("This section will allow the administrator to lock or unlock access to specific corridors. " +
                "This could involve selecting a corridor from a list or clicking on a map. " +
                "Further implementation is required here.");
        infoArea.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        infoArea.setEditable(false);
        infoArea.setLineWrap(true);
        infoArea.setWrapStyleWord(true);
        infoArea.setBackground(new Color(255, 255, 255));
        infoArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(15, 15, 15, 15)
        ));
        panel.add(new JScrollPane(infoArea), BorderLayout.CENTER);

        return panel;
    }

    private JPanel createHelpPanel() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(new EmptyBorder(30, 30, 30, 30));

        // Überschrift
        JLabel titleLabel = new JLabel("Help & Documentation", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(30, 30, 30));
        panel.add(titleLabel, BorderLayout.NORTH);

        // Hilfetext erstellen
        String helpText = "## User Management:\n" +
                "------------------\n" +
                "• **Add New User:** Create new user accounts with a username, password, and role (Admin/Employee).\n" +
                "• **Update User Details:** Modify existing user accounts (change password or role). " +
                "   Leave the 'New Username' and 'New Password' fields empty if you don't wish to change them.\n" +
                "• **Delete User:** Remove user accounts from the system. This action requires administrator password verification.\n\n" +
                "## Room Management:\n" +
                "-------------------\n" +
                "• **Update Room Details:** Modify room information such as the room number, description, and its locked/open status.\n" +
                "• **Lock Corridor Access:** This feature will allow you to control access to specific corridors. " +
                "   (Implementation in progress, future updates will provide a more detailed interface).\n\n" +
                "## Navigation:\n" +
                "-----------\n" +
                "• **Dashboard Overview:** Provides a summary of the system status and main functions.\n" +
                "• **Menu Bar:** Quick access to all management functions.\n" +
                "• **Sign Out:** Securely log out from the system.\n\n" +
                "## Virtual Keyboard:\n" +
                "---------------\n" +
                "• A virtual keyboard will appear for secure input when you click on text fields.\n" +
                "• Use the **CAPS** key for uppercase letters.\n" +
                "• Confirm your input by pressing **ENTER** on the virtual keyboard.";

        // Text in JTextArea anzeigen
        JTextArea helpTextArea = new JTextArea(helpText);
        helpTextArea.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        helpTextArea.setEditable(false);
        helpTextArea.setMargin(new Insets(15, 15, 15, 15));
        helpTextArea.setBackground(new Color(255, 255, 255));
        helpTextArea.setLineWrap(true);
        helpTextArea.setWrapStyleWord(true);

        // ScrollPane für den Text
        JScrollPane scrollPane = new JScrollPane(helpTextArea);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(5, 5, 5, 5)
        ));
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    // --- Helper Methods for consistent styling ---

    private JTextField createStyledTextField(int columns) {
        JTextField field = new JTextField(columns);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(150, 150, 150), 1),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        return field;
    }

    private JPasswordField createStyledPasswordField(int columns) {
        JPasswordField field = new JPasswordField(columns);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(150, 150, 150), 1),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        return field;
    }

    private <T> JComboBox<T> createStyledComboBox(T[] items) {
        JComboBox<T> comboBox = new JComboBox<>(items);
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        comboBox.setBackground(Color.WHITE);
        comboBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(150, 150, 150), 1),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        return comboBox;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(new Color(70, 130, 180)); // Steel Blue
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25)); // More padding
        button.setFocusPainted(false); // Remove focus border

        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(50, 90, 130)); // Darker blue on hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(70, 130, 180));
            }
        });
        return button;
    }

    private JLabel createStatusLabel() {
        JLabel label = new JLabel("", SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setBorder(new EmptyBorder(5, 0, 0, 0)); // Padding above
        return label;
    }

    private JScrollPane createStyledListScrollPane(JList<String> list, String title) {
        list.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        list.setBackground(Color.WHITE);
        list.setSelectionBackground(new Color(173, 216, 230)); // Light blue selection
        list.setSelectionForeground(Color.BLACK);
        list.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Padding inside list

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setPreferredSize(new Dimension(250, 0)); // Fixed width, flexible height
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(200, 200, 200)),
                        title,
                        TitledBorder.LEFT,
                        TitledBorder.TOP,
                        new Font("Segoe UI", Font.BOLD, 16),
                        new Color(30, 30, 30)
                ),
                new EmptyBorder(10, 10, 10, 10) // Padding around the titled border
        ));
        return scrollPane;
    }
}