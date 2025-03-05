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
        JMenuBar menuBar = new JMenuBar(); // Erstellen der Menüleiste
        menuBar.setBackground(new Color(50,50,50));

        // Erstellen von Menüs
        JMenuItem dashboardItem = new JMenuItem("Dashboard");
        dashboardItem.addActionListener(e -> switchToPanel("Admin Dashboard")); // Aktion für Dashboard

        JMenu userManageItem = new JMenu("User Management");
        
        JMenuItem addUser = new JMenuItem("Add User");
        addUser.addActionListener(e -> switchToPanel("User Management"));
        userManageItem.add(addUser);

        JMenu roomManageItem = new JMenu("Room Management");
        roomManageItem.addActionListener(e -> switchToPanel("Room Management")); // Aktion für Raumverwaltung

        
      

      
        // Menüleiste zum JFrame hinzufügen
        menuBar.add(dashboardItem);
        menuBar.add(userManageItem);
        menuBar.add(roomManageItem);
        this.setJMenuBar(menuBar);
        
        
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
