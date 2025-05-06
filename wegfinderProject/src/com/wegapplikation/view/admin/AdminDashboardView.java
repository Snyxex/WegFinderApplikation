package com.wegapplikation.view.admin;

import javax.swing.*;
import java.awt.*;

public class AdminDashboardView extends JPanel {
    
    public AdminDashboardView() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        JLabel titleLabel = new JLabel("Admin Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        
        JTextField mapPlaceholder = new JTextField("Hier kommt noch das MapGui hin!", SwingConstants.CENTER);
        mapPlaceholder.setEditable(false);
        mapPlaceholder.setFont(new Font("Arial", Font.BOLD, 20));
        
        add(titleLabel, BorderLayout.NORTH);
        add(mapPlaceholder, BorderLayout.CENTER);
    }
}