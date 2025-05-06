package com.wegapplikation.view.admin;

import javax.swing.*;
import java.awt.*;
import com.wegapplikation.controller.AdminController;
import com.wegapplikation.view.components.CustomKeyboard;

public class UserManagementView extends JPanel {
    private AdminController controller;
    private JTextField userField;
    private JPasswordField passField;
    private JComboBox<String> roleBox;
    private JList<String> userList;
    private JLabel statusLabel;
    
    public UserManagementView(AdminController controller) {
        this.controller = controller;
        initializeUI();
    }
    
    private void initializeUI() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Komponenten erstellen
        createComponents();
        
        // Layout aufbauen
        buildLayout(formPanel, gbc);
        
        // Liste hinzufügen
        add(createUserListPanel(), BorderLayout.EAST);
        add(formPanel, BorderLayout.CENTER);
    }
    
    private void createComponents() {
        userField = new JTextField(15);
        passField = new JPasswordField(15);
        String[] roles = {"Admin", "Mitarbeiter"};
        roleBox = new JComboBox<>(roles);
        statusLabel = new JLabel();
        
        // Keyboard-Listener hinzufügen
        userField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new CustomKeyboard(userField);
            }
        });
        
        passField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new CustomKeyboard(passField);
            }
        });
    }
    
    // ... weitere Methoden für Layout und Event-Handling
}