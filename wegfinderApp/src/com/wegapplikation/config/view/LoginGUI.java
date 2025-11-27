package com.wegapplikation.config.view;

import java.awt.EventQueue;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPasswordField;

import javax.swing.SwingConstants;
import java.awt.Color;

import com.wegapplikation.config.CustomKeyboard;
import com.wegapplikation.config.controller.LoginCal;

import java.awt.Font;
import java.awt.Window.Type;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionEvent;

/**
 * The {@code LoginGUI} class provides the graphical user interface for user
 * authentication (employee login). It collects username and password and delegates
 * the login attempt to the {@code LoginCal} controller.
 */
public class LoginGUI {

    /** The main frame for the login window. */
    public static JFrame frmMitarbeiterLogin;
    /** Text field for entering the username. Declared static for external access (e.g., AdminGUI). */
    public static JTextField textField;
    /** Password field for entering the password. Declared static for external access. */
    public static JPasswordField passwordField;
    /** Label used to display error messages (e.g., wrong credentials). */
    public static JLabel Errormsg = new JLabel("Falscher Benutzername oder falsches Kennwort");
    
 
    /**
     * Create the application.
     */
    public LoginGUI() {
        initialize();
    }



    /**
     * Initialize the contents of the frame, setting up components and layout.
     */
    public void initialize() {
        frmMitarbeiterLogin = new JFrame();
        frmMitarbeiterLogin.setUndecorated(true); // Removes the frame decorations
        frmMitarbeiterLogin.setType(Type.UTILITY);
        frmMitarbeiterLogin.setForeground(new Color(175, 189, 193));
        frmMitarbeiterLogin.setFont(new Font("Bahnschrift", Font.BOLD, 12));
        frmMitarbeiterLogin.setTitle("Mitarbeiter Login");
        frmMitarbeiterLogin.setBackground(new Color(175, 189, 193));
        frmMitarbeiterLogin.getContentPane().setBackground(new Color(175, 189, 193));
        frmMitarbeiterLogin.getContentPane().setForeground(new Color(0, 0, 0));
        frmMitarbeiterLogin.setResizable(false);
        frmMitarbeiterLogin.setBounds(100, 100, 350, 128);
        frmMitarbeiterLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmMitarbeiterLogin.getContentPane().setLayout(null);
        frmMitarbeiterLogin.setLocation(760, 300); // Sets the window position

        // --- Username Field ---
        textField = new JTextField();
        textField.setFont(new Font("Bahnschrift", Font.BOLD, 12));
        textField.setBackground(Color.LIGHT_GRAY);
        textField.setBounds(73, 26, 255, 20);
        frmMitarbeiterLogin.getContentPane().add(textField);
        textField.setColumns(10);
        
        // Mouse listener to trigger CustomKeyboard on click
        textField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
               CustomKeyboard.keyboard(textField); // Assumed static call to keyboard method
            }
        });

        // --- Username Label ---
        JLabel lblNewLabel = new JLabel("Name");
        lblNewLabel.setFont(new Font("Bahnschrift", Font.BOLD, 12));
        lblNewLabel.setBounds(10, 29, 53, 14);
        frmMitarbeiterLogin.getContentPane().add(lblNewLabel);

        // --- Password Label ---
        JLabel lblNewLabel_1 = new JLabel("Passwort");
        lblNewLabel_1.setFont(new Font("Bahnschrift", Font.BOLD, 12));
        lblNewLabel_1.setBounds(10, 62, 65, 14);
        frmMitarbeiterLogin.getContentPane().add(lblNewLabel_1);

        // --- Login Button ---
        JButton btnLogin = new JButton("Login");
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    LoginCal.cal(); // Delegates login logic to the controller
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        btnLogin.setBackground(new Color(215, 212, 205));
        btnLogin.setFont(new Font("Bahnschrift", Font.BOLD, 12));
        btnLogin.setBounds(212, 90, 116, 23);
        frmMitarbeiterLogin.getContentPane().add(btnLogin);

        // --- Back Button ---
        JButton btnBack = new JButton("Zurück");
        btnBack.setBackground(new Color(215, 212, 205));
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                frmMitarbeiterLogin.dispose(); // Closes the login window
            }
        });
        btnBack.setFont(new Font("Bahnschrift", Font.BOLD, 12));
        btnBack.setBounds(73, 90, 116, 23);
        frmMitarbeiterLogin.getContentPane().add(btnBack);

        // --- Password Field ---
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Bahnschrift", Font.BOLD, 12));
        passwordField.setBounds(73, 59, 255, 20);
        frmMitarbeiterLogin.getContentPane().add(passwordField);
        
        // Mouse listener to trigger CustomKeyboard on click
        passwordField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
               CustomKeyboard.keyboard(passwordField); // Assumed static call to keyboard method
            }
        });
             
        // --- Error Message Label ---
        Errormsg.setFont(new Font("Bahnschrift", Font.BOLD, 12));
        Errormsg.setHorizontalAlignment(SwingConstants.CENTER);
        Errormsg.setForeground(new Color(255, 0, 0));
        Errormsg.setBounds(10, 0, 335, 25);
        Errormsg.setVisible(false); // Initially hidden
        frmMitarbeiterLogin.getContentPane().add(Errormsg);
    }
}