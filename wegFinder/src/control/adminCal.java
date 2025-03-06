package control;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


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


   
//Panels for all Gui on Dashboard
    private JPanel adminDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.LIGHT_GRAY);
        JLabel titleLabel = new JLabel("Admin Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        JTextArea textLabel = new JTextArea("Willkommen im Admin-Dashboard.\nHier kannst du viele Einstellungen an Nutzer oder Räumen und Gängen vornehmen. ");
        textLabel.setFont(new Font("Arial", Font.BOLD, 20));
        textLabel.setEditable(false);
        textLabel.setHorizontalAligment(JTextField(JTextField.CENTER));
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(textLabel, BorderLayout.CENTER);
        return panel;
    }

    private void switchToPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
    }


    private JPanel addUserPanel(){
        JPanel panel = new JPanel(new BorderLayout());
       return panel;
    }


    private JPanel deleteUserPanel(){
        JPanel panel = new JPanel(new BorderLayout());
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

}
