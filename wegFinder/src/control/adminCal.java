package control;

import javax.swing.*;
import java.awt.*;

public class adminCal extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;

    

    protected void adminPage() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setTitle("Admin Dashboard");

        // Navbar erstellen
        createNavBar();

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

    private void createNavBar() {
        JToolBar navBar = new JToolBar();
        navBar.setFloatable(false); // Verhindert, dass die Leiste beweglich ist
        navBar.setBackground(new Color(50, 50, 50)); // Dunkelgrauer Hintergrund

        // Buttons für die Navigation
        JButton dashboardBtn = createNavButton("Dashboard", "Admin Dashboard");
        JButton userManageBtn = createNavButton("User Management", "User Management");
        JButton roomManageBtn = createNavButton("Room Management", "Room Management");

        
        navBar.add(dashboardBtn);
        navBar.add(userManageBtn);
        navBar.add(roomManageBtn);

        this.add(navBar, BorderLayout.NORTH);
    }

    private JButton createNavButton(String text, String panelName) {
        JButton button = new JButton(text);
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(70, 70, 70));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));

        // Hover-Effekt
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(90, 90, 90));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(70, 70, 70));
            }
        });

        button.addActionListener(e -> switchToPanel(panelName));
        return button;
    }

    private JPanel adminDashboardPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.LIGHT_GRAY);

        JLabel titleLabel = new JLabel("Admin Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        panel.add(titleLabel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel userManagePage() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.LIGHT_GRAY);

        JLabel titleLabel = new JLabel("User Management", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        panel.add(titleLabel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel roomManagePage() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.LIGHT_GRAY);

        JLabel titleLabel = new JLabel("Room Management", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        panel.add(titleLabel, BorderLayout.CENTER);
        return panel;
    }

    private void switchToPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
    }

}
