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
        mainPanel.add(addUserPanel(), "Add User");
        mainPanel.add(deleteUserPanel(), "Delete User");
        mainPanel.add(updateUserPanel(),"Update User");
        mainPanel.add(addRoomPanel(),"Add Room");
        mainPanel.add(updateRoomPanel(), "Update Room");
        mainPanel.add(deleteRoomPanel(), "Delete Room");
        mainPanel.add(lockRoomPanel(),"Lock Room");
        mainPanel.add(lockCoridoorPanel(),"Lock Coridoor");

        this.add(mainPanel, BorderLayout.CENTER);
        this.setVisible(true);
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar(); // Menüleiste erstellen

        // Dashboard als Dropdown-Menü (verhindert Design-Probleme)
        JMenu dashboardMenu = new JMenu("Dashboard");
        dashboardMenu.add(createMenuItem("Dashboard",  e -> switchToPanel("Admin Dashboard")));

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
        

        // Menü zur Menüleiste hinzufügen
        menuBar.add(dashboardMenu);
        menuBar.add(userMenu);
        menuBar.add(roomMenu);

        this.setJMenuBar(menuBar);
    }

   
    private JMenuItem createMenuItem(String text, ActionListener actionListener) {
        JMenuItem menuItem = new JMenuItem(text);
        menuItem.addActionListener(actionListener);
        return menuItem;
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


    private JPanel addUserPanel(){
       return mainPanel;
    }


    private JPanel deleteUserPanel(){
        return mainPanel;
    }

    private JPanel updateUserPanel(){
        return mainPanel;
    }

    private JPanel addRoomPanel(){
        return mainPanel;
    }
     
    private JPanel updateRoomPanel(){
        return mainPanel;
    }

    private JPanel deleteRoomPanel(){
        return mainPanel;
    }

    private JPanel lockRoomPanel(){
        return mainPanel;
    }

    private JPanel lockCoridoorPanel(){
        return mainPanel;
    }


}
