package control;

import javax.swing.*;
import java.awt.*;

public class adminCal extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;

    protected void adminPage() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 500);
        this.setTitle("Admin Dashboard");

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
        JPanel panel = new JPanel(new GridLayout(3, 1));
        panel.setBackground(Color.LIGHT_GRAY);

        JLabel titleLabel = new JLabel("Admin Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JButton userManageBtn = new JButton("User Management");
        userManageBtn.addActionListener(e -> switchToPanel("User Management"));

        JButton roomManageBtn = new JButton("Room Management");
        roomManageBtn.addActionListener(e -> switchToPanel("Room Management"));

        panel.add(titleLabel);
        panel.add(userManageBtn);
        panel.add(roomManageBtn);

        return panel;
    }

    private JPanel userManagePage() {
        JPanel panel = new JPanel(new GridLayout(5, 1));
        panel.setBackground(Color.LIGHT_GRAY);

        JLabel titleLabel = new JLabel("User Management", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JButton addUserBtn = new JButton("Add User");
        addUserBtn.addActionListener(e -> addUser());

        JButton deleteUserBtn = new JButton("Delete User");
        deleteUserBtn.addActionListener(e -> deleteUser());

        JButton updateUserBtn = new JButton("Update User");
        updateUserBtn.addActionListener(e -> updateUser());

        JButton backButton = new JButton("Back to Dashboard");
        backButton.addActionListener(e -> switchToPanel("Admin Dashboard"));

        panel.add(titleLabel);
        panel.add(addUserBtn);
        panel.add(deleteUserBtn);
        panel.add(updateUserBtn);
        panel.add(backButton);

        return panel;
    }

    private JPanel roomManagePage() {
        JPanel panel = new JPanel(new GridLayout(5, 1));
        panel.setBackground(Color.LIGHT_GRAY);

        JLabel titleLabel = new JLabel("Room Management", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JButton addRoomBtn = new JButton("Add Room");
        addRoomBtn.addActionListener(e -> addRoom());

        JButton deleteRoomBtn = new JButton("Delete Room");
        deleteRoomBtn.addActionListener(e -> deleteRoom());

        JButton lockRoomBtn = new JButton("Lock Room");
        lockRoomBtn.addActionListener(e -> lockRoom());

        JButton lockCorridorBtn = new JButton("Lock Corridor");
        lockCorridorBtn.addActionListener(e -> lockCorridor());

        JButton backButton = new JButton("Back to Dashboard");
        backButton.addActionListener(e -> switchToPanel("Admin Dashboard"));

        panel.add(titleLabel);
        panel.add(addRoomBtn);
        panel.add(deleteRoomBtn);
        panel.add(lockRoomBtn);
        panel.add(lockCorridorBtn);
        panel.add(backButton);

        return panel;
    }

    private void switchToPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
    }

    private void addUser() {
        
        // Hier Code für das Hinzufügen eines Benutzers einfügen
    }

    private void deleteUser() {
       
        // Hier Code für das Löschen eines Benutzers einfügen
    }

    private void updateUser() {
       
        // Hier Code für das Aktualisieren eines Benutzers einfügen
    }

    private void addRoom() {
        
        // Hier Code für das Hinzufügen eines Raums einfügen
    }

    private void deleteRoom() {
        
        // Hier Code für das Löschen eines Raums einfügen
    }

    private void lockRoom() {
        
        // Hier Code für das Sperren eines Raums einfügen
    }

    private void lockCorridor() {
        
        // Hier Code für das Sperren eines Flurs einfügen
    }

    
}
