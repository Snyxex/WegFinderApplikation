package com.wegapplikation.view.components;

import javax.swing.*;
import java.awt.*;
import com.wegapplikation.view.AdminMainView;

public class MenuBuilder {
    private AdminMainView mainView;
    
    public MenuBuilder(AdminMainView mainView) {
        this.mainView = mainView;
    }
    
    public JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(Color.WHITE);
        
        menuBar.add(createDashboardMenu());
        menuBar.add(createUserMenu());
        menuBar.add(createRoomMenu());
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(createSignOutMenu());
        
        return menuBar;
    }
    
    private JMenu createDashboardMenu() {
        JMenu menu = new JMenu("Dashboard");
        menu.setFont(new Font("Arial", Font.BOLD, 16));
        
        menu.add(createMenuItem("Dashboard", e -> mainView.switchToPanel("Admin Dashboard")));
        menu.add(createMenuItem("Hilfe", e -> mainView.switchToPanel("Help")));
        
        return menu;
    }
    
    private JMenuItem createMenuItem(String text, ActionListener listener) {
        JMenuItem item = new JMenuItem(text);
        item.setFont(new Font("Arial", Font.PLAIN, 16));
        item.setOpaque(true);
        item.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        // Hover-Effekt
        item.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                item.setBackground(new Color(70, 70, 70));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                item.setBackground(Color.WHITE);
            }
        });
        
        item.addActionListener(listener);
        return item;
    }
    
    // ... weitere Menü-Erstellungsmethoden
}