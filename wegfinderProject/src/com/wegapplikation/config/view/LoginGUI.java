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

public class LoginGUI {

    public static JFrame frmMitarbeiterLogin;
    public static JTextField textField;
    public static JPasswordField passwordField;
    public static JLabel Errormsg = new JLabel("Falscher Benutzername oder falsches Kennwort");
    
  

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LoginGUI window = new LoginGUI();
                    window.frmMitarbeiterLogin.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public LoginGUI() {
        
        initialize();
    }



    /**
     * Initialize the contents of the frame.
     */
    public void initialize() {
        frmMitarbeiterLogin = new JFrame();
        frmMitarbeiterLogin.setUndecorated(true);
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
        frmMitarbeiterLogin.setLocation(760, 300);

        textField = new JTextField();
        textField.setFont(new Font("Bahnschrift", Font.BOLD, 12));
        textField.setBackground(Color.LIGHT_GRAY);
        textField.setBounds(73, 26, 255, 20);
        frmMitarbeiterLogin.getContentPane().add(textField);
        textField.setColumns(10);
        textField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
               new  CustomKeyboard(textField);
            }
        });

        JLabel lblNewLabel = new JLabel("Name");
        lblNewLabel.setFont(new Font("Bahnschrift", Font.BOLD, 12));
        lblNewLabel.setBounds(10, 29, 53, 14);
        frmMitarbeiterLogin.getContentPane().add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Passwort");
        lblNewLabel_1.setFont(new Font("Bahnschrift", Font.BOLD, 12));
        lblNewLabel_1.setBounds(10, 62, 65, 14);
        frmMitarbeiterLogin.getContentPane().add(lblNewLabel_1);

        JButton btnLogin = new JButton("Login");
        btnLogin.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		try {
					LoginCal.cal();
				} catch (Exception e) {
					e.printStackTrace();
				}
        	}
        });
        btnLogin.setBackground(new Color(215, 212, 205));
        btnLogin.setFont(new Font("Bahnschrift", Font.BOLD, 12));
        btnLogin.setBounds(212, 90, 116, 23);
        frmMitarbeiterLogin.getContentPane().add(btnLogin);

        JButton btnBack = new JButton("Zurück");
        btnBack.setBackground(new Color(215, 212, 205));
        btnBack.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		frmMitarbeiterLogin.dispose();
        	
        	}
        });
        btnBack.setFont(new Font("Bahnschrift", Font.BOLD, 12));
        btnBack.setBounds(73, 90, 116, 23);
        frmMitarbeiterLogin.getContentPane().add(btnBack);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Bahnschrift", Font.BOLD, 12));
   
        passwordField.setBounds(73, 59, 255, 20);
        frmMitarbeiterLogin.getContentPane().add(passwordField);
        passwordField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
               new  CustomKeyboard(passwordField);
            }
        });
             
        Errormsg.setFont(new Font("Bahnschrift", Font.BOLD, 12));
        Errormsg.setHorizontalAlignment(SwingConstants.CENTER);
        Errormsg.setForeground(new Color(255, 0, 0));
        Errormsg.setBounds(10, 0, 335, 25);
        Errormsg.setVisible(false);
        frmMitarbeiterLogin.getContentPane().add(Errormsg);
    }
}
