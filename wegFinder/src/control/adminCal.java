package control;

import javax.swing.*;
import java.awt.*;

public class adminCal extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;

    public void adminPage() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(420, 420);

        // CardLayout für den Wechsel zwischen den Panels
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Panels zum Hauptpanel hinzufügen
        mainPanel.add(adminDashboardPanel(), "Admin Dashboard");
        mainPanel.add(userManagePage(), "User Management");
        mainPanel.add(roomManagePage(), "Room Management");

        this.add(mainPanel);
        this.setVisible(true);
    }

    private JPanel adminDashboardPanel() {
        return createNavigationPanel("Admin Dashboard");
    }

    private JPanel userManagePage() {
        return createNavigationPanel("User Management");
    }

    private JPanel roomManagePage() {
        return createNavigationPanel("Room Management");
    }

    private JPanel createNavigationPanel(String title) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));
        panel.setBackground(Color.LIGHT_GRAY);

        JLabel label = new JLabel(title, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 16));

        JButton userManageBtn = new JButton("User Management");
        userManageBtn.addActionListener(e -> cardLayout.show(mainPanel, "User Management"));

        JButton roomManageBtn = new JButton("Room Management");
        roomManageBtn.addActionListener(e -> cardLayout.show(mainPanel, "Room Management"));

        JButton backButton = new JButton("Back to Dashboard");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Admin Dashboard"));

        panel.add(label);
        panel.add(userManageBtn);
        panel.add(roomManageBtn);

        if (!title.equals("Admin Dashboard")) {
            panel.add(backButton);
        }

        return panel;
    }

    private void addUser() {
        // Methode zum Hinzufügen eines Benutzers
    }
    
    private void deleteUser() {
        // Methode zum Löschen eines Benutzers
    }
    
    private void updateUser() {
        // Methode zum Aktualisieren eines Benutzers
    }
    
    private void addRoom() {
        // Methode zum Hinzufügen eines Raums
    }
    
    private void deleteRoom() {
        // Methode zum Löschen eines Raums
    }
    
    private void lockRoom() {
        // Methode zum Sperren eines Raums
    }
    
    private void lockCoridoor() {
        // Methode zum Sperren eines Flurs
    }
    
}
