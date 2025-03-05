package control;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class adminCal extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;

    protected void adminPage() {
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
        mainPanel.add(userManagePage(), "User Management");
        mainPanel.add(roomManagePage(), "Room Management");

        this.add(mainPanel, BorderLayout.CENTER);
        this.setVisible(true);
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar(); // Menüleiste erstellen

        // Dashboard als Dropdown-Menü (verhindert Design-Probleme)
        JMenu dashboardMenu = new JMenu("Dashboard");
        dashboardMenu.add(createMenuItem("Dashboard", "dashboard_icon.png", KeyEvent.VK_D, e -> switchToPanel("Admin Dashboard")));

        // User Management mit Dropdown
        JMenu userMenu = new JMenu("User Management");
        userMenu.add(createMenuItem("Benutzerliste", "user_list.png", KeyEvent.VK_U, e -> switchToPanel("User Management")));
        userMenu.add(createMenuItem("Neuer Benutzer", "add_user.png", KeyEvent.VK_N, e -> System.out.println("Neuer Benutzer hinzugefügt")));

        // Room Management mit Dropdown
        JMenu roomMenu = new JMenu("Room Management");
        roomMenu.add(createMenuItem("Räume anzeigen", "room_list.png", KeyEvent.VK_R, e -> switchToPanel("Room Management")));
        roomMenu.add(createMenuItem("Neuen Raum hinzufügen", "add_room.png", KeyEvent.VK_A, e -> System.out.println("Neuen Raum hinzufügen")));

        // Menü zur Menüleiste hinzufügen
        menuBar.add(dashboardMenu);
        menuBar.add(userMenu);
        menuBar.add(roomMenu);

        this.setJMenuBar(menuBar);
    }

    /**
     * Methode zur Erstellung eines Menüeintrags (JMenuItem) mit Icon, Tastenkürzel und Aktion.
     */
    private JMenuItem createMenuItem(String text, String iconPath, int keyEvent, ActionListener actionListener) {
        JMenuItem menuItem = new JMenuItem(text);
        if (iconPath != null) {
            menuItem.setIcon(loadScaledIcon(iconPath));
        }
        menuItem.setAccelerator(KeyStroke.getKeyStroke(keyEvent, getShortcutKeyMask()));
        menuItem.addActionListener(actionListener);
        return menuItem;
    }

    /**
     * Methode zum Laden und Skalieren eines Icons.
     */
    private ImageIcon loadScaledIcon(String path) {
        ImageIcon icon = new ImageIcon(path);
        Image scaledImage = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    /**
     * Methode zur Ermittlung der korrekten Tastenkombination für Shortcuts (Java 8+ kompatibel).
     */
    private static int getShortcutKeyMask() {
        try {
            return (int) Toolkit.getDefaultToolkit().getClass()
                    .getMethod("getMenuShortcutKeyMaskEx")
                    .invoke(Toolkit.getDefaultToolkit());
        } catch (Exception e) {
            return Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(); // Für Java 8
        }
    }

    private JPanel adminDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.LIGHT_GRAY);
        JLabel titleLabel = new JLabel("Admin Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(titleLabel, BorderLayout.NORTH);
        return panel;
    }

    private JPanel userManagePage() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.LIGHT_GRAY);
        JLabel titleLabel = new JLabel("User Management", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(titleLabel, BorderLayout.NORTH);
        return panel;
    }

    private JPanel roomManagePage() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.LIGHT_GRAY);
        JLabel titleLabel = new JLabel("Room Management", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(titleLabel, BorderLayout.NORTH);
        return panel;
    }

    private void switchToPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
    }
}
