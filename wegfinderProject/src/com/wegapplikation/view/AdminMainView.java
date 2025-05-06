package com.wegapplikation.view;

import javax.swing.*;
import java.awt.*;
import com.wegapplikation.controller.AdminController;
import com.wegapplikation.view.admin.*;
import com.wegapplikation.view.components.MenuBuilder;

public class AdminMainView extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private AdminController controller;
    private static AdminMainView instance;

    // Singleton-Pattern für zentrale Instanz
    public static AdminMainView getInstance() {
        if (instance == null) {
            instance = new AdminMainView(new AdminController());
        }
        return instance;
    }
    
    private AdminMainView(AdminController controller) {
        this.controller = controller;
        initializeUI();
    }
    
    private void initializeUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1024, 768);
        setTitle("WegFinder Admin Dashboard");
        setLocationRelativeTo(null); // Zentriert das Fenster

        // Look and Feel auf System-Standard setzen
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Menu erstellen
        MenuBuilder menuBuilder = new MenuBuilder(this);
        setJMenuBar(menuBuilder.createMenuBar());

        // CardLayout für Panel-Wechsel
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panels hinzufügen
        mainPanel.add(new AdminDashboardView(), "DASHBOARD");
        mainPanel.add(new UserManagementView(controller), "USER_MANAGEMENT");
        mainPanel.add(new RoomManagementView(controller), "ROOM_MANAGEMENT");

        add(mainPanel);

        // Standard-Panel anzeigen
        cardLayout.show(mainPanel, "DASHBOARD");
    }

    public void switchToPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
    }

    // Main-Methode als Startpunkt der Anwendung
    public static void main(String[] args) {
        // UI im Event-Dispatch-Thread starten
        SwingUtilities.invokeLater(() -> {
            AdminMainView mainView = AdminMainView.getInstance();
            mainView.setVisible(true);
            
            // Optional: Splash-Screen oder Willkommensnachricht
            JOptionPane.showMessageDialog(mainView, 
                "Willkommen im WegFinder Admin-System",
                "WegFinder", 
                JOptionPane.INFORMATION_MESSAGE);
        });
    }

    // Hilfsmethode zum Neuladen von Panels
    public void refreshCurrentPanel() {
        // Implementierung zum Neuladen des aktuellen Panels
        // z.B. nach Datenbankänderungen
    }

    // Getter für den Controller
    public AdminController getController() {
        return controller;
    }
}