package control;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * Administrative interface for the WegFinder application.
 * Provides user and room management functionality.
 */
public class adminCal extends JFrame {
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;
    private static final Font MENU_FONT = new Font("Arial", Font.BOLD, 16);
    private static final Font CONTENT_FONT = new Font("Arial", Font.PLAIN, 14);
    private static final Color PRIMARY_COLOR = new Color(70, 130, 180);
    private static final Color HOVER_COLOR = new Color(100, 149, 237);
    
    private final CardLayout cardLayout;
    private final JPanel mainPanel;
    private final userData userData;
    private final Map<String, JPanel> panels;

    public adminCal() {
        userData = new userData();
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        panels = new HashMap<>();
        
        setupFrame();
        createPanels();
        createMenuBar();
        
        setVisible(true);
    }

    private void setupFrame() {
        setTitle("Admin Dashboard");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        mainPanel.setBackground(Color.WHITE);
        add(mainPanel);
    }

    private void createPanels() {
        addPanel("Dashboard", createDashboardPanel());
        addPanel("Add User", createUserManagementPanel(PanelType.ADD));
        addPanel("Update User", createUserManagementPanel(PanelType.UPDATE));
        addPanel("Delete User", createUserManagementPanel(PanelType.DELETE));
        addPanel("Add Room", createRoomPanel("Add Room"));
        addPanel("Update Room", createRoomPanel("Update Room"));
        addPanel("Delete Room", createRoomPanel("Delete Room"));
        addPanel("Lock Room", createRoomPanel("Lock Room"));
        addPanel("Lock Corridor", createRoomPanel("Lock Corridor"));
        addPanel("Help", createHelpPanel());
    }

    private void addPanel(String name, JPanel panel) {
        panels.put(name, panel);
        mainPanel.add(panel, name);
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(Color.WHITE);

        menuBar.add(createMenu("Dashboard", 
            new String[]{"Dashboard", "Help"}));
        menuBar.add(createMenu("User Management", 
            new String[]{"Add User", "Update User", "Delete User"}));
        menuBar.add(createMenu("Room Management", 
            new String[]{"Add Room", "Update Room", "Delete Room", 
                        "Lock Room", "Lock Corridor"}));

        setJMenuBar(menuBar);
    }

    private JMenu createMenu(String title, String[] items) {
        JMenu menu = new JMenu(title);
        menu.setFont(MENU_FONT);
        
        for (String item : items) {
            menu.add(createMenuItem(item));
        }
        
        return menu;
    }

    private JMenuItem createMenuItem(String text) {
        JMenuItem item = new JMenuItem(text);
        item.setFont(CONTENT_FONT);
        item.setBackground(Color.WHITE);
        
        item.addActionListener(e -> switchToPanel(text));
        
        item.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                item.setBackground(HOVER_COLOR);
                item.setForeground(Color.WHITE);
            }
            
            public void mouseExited(MouseEvent e) {
                item.setBackground(Color.WHITE);
                item.setForeground(Color.BLACK);
            }
        });
        
        return item;
    }

    private enum PanelType { ADD, UPDATE, DELETE }

    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        
        JLabel title = createTitleLabel("Admin Dashboard");
        JPanel mapPanel = createMapPanel();
        JPanel statsPanel = createStatsPanel();
        
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(mapPanel, BorderLayout.CENTER);
        centerPanel.add(statsPanel, BorderLayout.EAST);
        
        panel.add(title, BorderLayout.NORTH);
        panel.add(centerPanel, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel createStatsPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 5, 5));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setPreferredSize(new Dimension(200, 0));

        panel.add(createStatCard("Total Users", String.valueOf(userData.getUsers().size())));
        panel.add(createStatCard("Active Rooms", "--"));
        panel.add(createStatCard("Locked Areas", "--"));

        return panel;
    }

    private JPanel createStatCard(String label, String value) {
        JPanel card = new JPanel(new BorderLayout(5, 5));
        card.setBackground(PRIMARY_COLOR);
        card.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel(label);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(CONTENT_FONT);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setForeground(Color.WHITE);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 24));

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }

    private JPanel createUserManagementPanel(PanelType type) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        String title;
        switch (type) {
            case ADD:
                title = "Add User";
                break;
            case UPDATE:
                title = "Update User";
                break;
            case DELETE:
                title = "Delete User";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
        
        panel.add(createTitleLabel(title), BorderLayout.NORTH);
        panel.add(createUserForm(type), BorderLayout.CENTER);
        panel.add(createUserList(), BorderLayout.EAST);
        
        return panel;
    }

    private JPanel createUserForm(PanelType type) {
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Username field
        gbc.gridy = 0;
        form.add(new JLabel("Username:"), gbc);
        JTextField usernameField = new JTextField(20);
        gbc.gridx = 1;
        form.add(usernameField, gbc);

        // Password field (for ADD and UPDATE)
        if (type != PanelType.DELETE) {
            gbc.gridy = 1;
            gbc.gridx = 0;
            form.add(new JLabel("Password:"), gbc);
            JPasswordField passwordField = new JPasswordField(20);
            gbc.gridx = 1;
            form.add(passwordField, gbc);
        }

        // Role selection
        if (type != PanelType.DELETE) {
            gbc.gridy = 2;
            gbc.gridx = 0;
            form.add(new JLabel("Role:"), gbc);
            JComboBox<String> roleCombo = new JComboBox<>(new String[]{"User", "Admin"});
            gbc.gridx = 1;
            form.add(roleCombo, gbc);
        }

        // Action button
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton actionButton = new JButton(type.toString());
        actionButton.setBackground(PRIMARY_COLOR);
        actionButton.setForeground(Color.WHITE);
        actionButton.addActionListener(e -> handleUserAction(type, usernameField.getText(), 
            type != PanelType.DELETE ? new String(((JPasswordField)form.getComponent(3)).getPassword()) : "",
            type != PanelType.DELETE ? ((JComboBox<?>)form.getComponent(5)).getSelectedItem().toString() : ""));
        form.add(actionButton, gbc);

        return form;
    }

    private void handleUserAction(PanelType type, String username, String password, String role) {
        try {
            switch (type) {
                case ADD: 
                    userData.addUser(username, password, role);
                    break;
                case UPDATE: 
                    userData.updateUser(username, "", password, role);
                    break;
                case DELETE: 
                    userData.deleteUser(username);
                    break;
            }
            JOptionPane.showMessageDialog(this, "Operation successful!", "Success", 
                JOptionPane.INFORMATION_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel createUserList() {
        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.setPreferredSize(new Dimension(200, 0));
        
        JList<String> userList = new JList<>(userData.getUserListModel());
        userList.setFont(CONTENT_FONT);
        userList.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        JScrollPane scrollPane = new JScrollPane(userList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("User List"));
        
        listPanel.add(scrollPane);
        
        return listPanel;
    }

    private JPanel createRoomPanel(String title) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        panel.add(createTitleLabel(title), BorderLayout.NORTH);
        
        // Room management form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Room fields
        gbc.gridy = 0;
        formPanel.add(new JLabel("Room Number:"), gbc);
        JTextField roomField = new JTextField(20);
        gbc.gridx = 1;
        formPanel.add(roomField, gbc);

        // Action button
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton actionButton = new JButton(title);
        actionButton.setBackground(PRIMARY_COLOR);
        actionButton.setForeground(Color.WHITE);
        formPanel.add(actionButton, gbc);

        panel.add(formPanel, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel createMapPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        JLabel mapLabel = new JLabel("Interactive Map View", SwingConstants.CENTER);
        mapLabel.setFont(CONTENT_FONT);
        mapLabel.setForeground(Color.GRAY);
        panel.add(mapLabel, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel createHelpPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        panel.add(createTitleLabel("Help & Documentation"), BorderLayout.NORTH);
        
        JTextArea helpText = new JTextArea();
        helpText.setFont(CONTENT_FONT);
        helpText.setEditable(false);
        helpText.setWrapStyleWord(true);
        helpText.setLineWrap(true);
        helpText.setMargin(new Insets(10, 10, 10, 10));
        helpText.setText("WegFinder Admin Dashboard Help\n\n" +
            "1. User Management\n" +
            "   - Add User: Create new user accounts\n" +
            "   - Update User: Modify existing user information\n" +
            "   - Delete User: Remove user accounts\n\n" +
            "2. Room Management\n" +
            "   - Add Room: Register new rooms\n" +
            "   - Update Room: Modify room information\n" +
            "   - Delete Room: Remove rooms\n" +
            "   - Lock Room: Temporarily restrict access\n" +
            "   - Lock Corridor: Restrict access to multiple rooms\n\n" +
            "For additional support, contact system administrator.");
        
        JScrollPane scrollPane = new JScrollPane(helpText);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }

    private JLabel createTitleLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setForeground(PRIMARY_COLOR);
        label.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        return label;
    }

    private void switchToPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(
                UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> new adminCal());
    }
}