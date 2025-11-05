package com.wegapplikation.config;

import javax.swing.*;
import javax.swing.text.JTextComponent; 
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Eine virtuelle Tastatur-Implementierung unter Verwendung von Swing.
 * Bietet eine grafische Tastaturoberfläche, die zur Eingabe von Text in ein 
 * Ziel-JTextComponent (JTextField oder JPasswordField) verwendet werden kann.
 */
public class CustomKeyboard extends JFrame {
    
    /** Das ursprüngliche Zielfeld, in das der endgültige Text/Passwort übertragen wird. */
    private JTextComponent targetField; 
    
    /** Das aktuell sichtbare und bearbeitete Feld. */
    private JTextComponent activeDisplayField;
    
    /** Flagge zur Verfolgung des CAPS-Zustands (true für Großbuchstaben). */
    private boolean isCapsLock = true;
    
    private JPanel keyboardContainer;
    private final String LETTERS_PANEL = "letters";
    private final String SYMBOLS_PANEL = "symbols";
    private final Color BG_COLOR = new Color(175, 189, 193);

    /** Liste aller Buchstaben-Buttons, um den CAPS-Zustand leicht aktualisieren zu können. */
    private List<JButton> letterButtons = new ArrayList<>();

    /**
     * Konstruiert eine neue Instanz der virtuellen Tastatur.
     * Der Typ des Zielfeldes (normal/passwort) wird automatisch erkannt.
     *
     * @param target Das JTextComponent (JTextField oder JPasswordField), das die endgültige Eingabe erhält.
     */
    public CustomKeyboard(JTextComponent target) {
        this.targetField = target;
        // Der Typ wird nun basierend auf der Klasse des target-Objekts bestimmt
        initializeKeyboard(); 
    }

    private void initializeKeyboard() {
        setTitle("Virtuelle Tastatur");
        setLayout(new BorderLayout(5, 5));
        setSize(945, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(BG_COLOR);

        // 1. Initialen Text aus dem Zielfeld lesen
        String initialText = "";
        // Automatische Erkennung des Feldtyps
        boolean isPasswordField = (targetField instanceof JPasswordField);
        
        if (isPasswordField) {
             // Achtung: getPassword() sollte idealerweise vermieden werden.
            initialText = new String(((JPasswordField) targetField).getPassword());
        } else {
            initialText = targetField.getText();
        }

        // 2. Anzeige-Feld basierend auf dem erkannten Typ erstellen und initialisieren
        if (isPasswordField){
            JPasswordField passwordField = new JPasswordField(initialText);
            passwordField.setEditable(false);
            passwordField.setFont(new Font("Arial", Font.BOLD, 18));
            passwordField.setPreferredSize(new Dimension(700, 40));
            passwordField.setBackground(BG_COLOR);
            add(passwordField, BorderLayout.NORTH);
            activeDisplayField = passwordField; // Das aktive Feld setzen
        } else {
            JTextField displayField = new JTextField(initialText);
            displayField.setEditable(false);
            displayField.setFont(new Font("Arial", Font.BOLD, 18));
            displayField.setPreferredSize(new Dimension(700, 40));
            displayField.setBackground(BG_COLOR);
            add(displayField, BorderLayout.NORTH);
            activeDisplayField = displayField; // Das aktive Feld setzen
        }
        
        keyboardContainer = new JPanel(new CardLayout());
        keyboardContainer.setBackground(BG_COLOR);

        // Seite 1 – Buchstaben
        JPanel lettersPanel = new JPanel(new GridLayout(5, 1, 5, 5));
        lettersPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        lettersPanel.setBackground(BG_COLOR);
        lettersPanel.add(createRow(new String[]{"1","2","3","4","5","6","7","8","9","0"}, false));
        lettersPanel.add(createRow(new String[]{"Q","W","E","R","T","Z","U","I","O","P"}, true));
        lettersPanel.add(createRow(new String[]{"A","S","D","F","G","H","J","K","L","Ü"}, true));
        lettersPanel.add(createRow(new String[]{"Y","X","C","V","B","N","M","Ö","Ä"}, true));
        lettersPanel.add(createControlRow(true));

        
        JPanel symbolsPanel = new JPanel(new GridLayout(5, 1, 5, 5));
        symbolsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        symbolsPanel.setBackground(BG_COLOR);
        symbolsPanel.add(createRow(new String[]{"1","2","3","4","5","6","7","8","9","0"}, false));
        symbolsPanel.add(createRow(new String[]{"!", "@", "#", "$", "%", "^", "&", "*", "(", ")"}, false));
        symbolsPanel.add(createRow(new String[]{"-", "_", "+", "=", "{", "}", "[", "]", "|"}, false));
        symbolsPanel.add(createRow(new String[]{":", ";", "\"", "'", "<", ">", ",", ".", "?"}, false));
        symbolsPanel.add(createControlRow(false));

        keyboardContainer.add(lettersPanel, LETTERS_PANEL);
        keyboardContainer.add(symbolsPanel, SYMBOLS_PANEL);

        add(keyboardContainer, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    /** Liest den aktuellen Text aus dem aktiven Anzeigefeld. */
    private String getActiveText() {
        if (activeDisplayField instanceof JPasswordField) {
            // JPasswordField gibt char[] zurück, in String umwandeln
            return new String(((JPasswordField) activeDisplayField).getPassword());
        } else if (activeDisplayField instanceof JTextField) {
            return ((JTextField) activeDisplayField).getText();
        }
        return "";
    }
    
    /** Setzt den Text im aktiven Anzeigefeld. */
    private void setActiveText(String text) {
        if (activeDisplayField instanceof JPasswordField) {
            ((JPasswordField) activeDisplayField).setText(text);
        } else if (activeDisplayField instanceof JTextField) {
            ((JTextField) activeDisplayField).setText(text);
        }
    }


    private JPanel createRow(String[] keys, boolean isLetterRow) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        row.setBackground(BG_COLOR);
        for (String key : keys) {
            JButton button = createKeyButton(key, isLetterRow);
            row.add(button);
        }
        return row;
    }

    private JPanel createControlRow(boolean isLettersPanel) {

        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        row.setBackground(BG_COLOR);

        // CAPS
        JButton capsButton = new JButton("CAPS");
        capsButton.setPreferredSize(new Dimension(85, 50));
        capsButton.setFont(new Font("Arial", Font.BOLD, 14));
        if (isLettersPanel) {
            capsButton.addActionListener(e -> {
                isCapsLock = !isCapsLock;
                capsButton.setBackground(isCapsLock ? Color.LIGHT_GRAY : null);
                updateCapsState();
            });
        } else {
            capsButton.setEnabled(false);
        }
        row.add(capsButton);

   
        JButton toggle = new JButton(isLettersPanel ? "123" : "ABC");
        toggle.setPreferredSize(new Dimension(85, 50));
        toggle.setFont(new Font("Arial", Font.BOLD, 14));
        toggle.addActionListener(e -> switchKeyboardPanel(isLettersPanel ? SYMBOLS_PANEL : LETTERS_PANEL));
        row.add(toggle);


        JButton space = new JButton("Leertaste");
        space.setPreferredSize(new Dimension(500, 50));
        space.setFont(new Font("Arial", Font.BOLD, 14));
        space.addActionListener(e -> setActiveText(getActiveText() + " ")); 
        row.add(space);


        JButton backspace = new JButton("<-");
        backspace.setPreferredSize(new Dimension(85, 50));
        backspace.setFont(new Font("Arial", Font.BOLD, 14));
        backspace.addActionListener(e -> {
            String text = getActiveText(); 
            if (!text.isEmpty()) {
                setActiveText(text.substring(0, text.length() - 1));
            }
        });
        row.add(backspace);


        JButton enter = new JButton("ENTER");
        enter.setPreferredSize(new Dimension(120, 50));
        enter.setFont(new Font("Arial", Font.BOLD, 14));
        enter.addActionListener(e -> {
            // Text wird zurück ins ursprüngliche Zielfeld übertragen
            targetField.setText(getActiveText()); 
            dispose();
        });
        row.add(enter);

        return row;
    }

    private JButton createKeyButton(String key, boolean isLetter) {
        
        JButton button = new JButton(key);
        button.setPreferredSize(new Dimension(85, 50));
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(Color.WHITE);
        button.setFocusPainted(false);

        button.addActionListener(e -> {
            String inputKey = key;
            if (isCapsLock && isLetter) {
                inputKey = key.toUpperCase();
            } else if (!isCapsLock && isLetter) {
                inputKey = key.toLowerCase();
            }
            setActiveText(getActiveText() + inputKey); 
        });

        if (isLetter) {
            letterButtons.add(button);
        }

        return button;
    }

    private void updateCapsState() {

        for (JButton button : letterButtons) {
            String text = button.getText();
            if (text.length() == 1 && Character.isLetter(text.charAt(0))) {
                button.setText(isCapsLock ? text.toUpperCase() : text.toLowerCase());
            }
        }
    }

    private void switchKeyboardPanel(String panelName) {

        CardLayout cl = (CardLayout) keyboardContainer.getLayout();
        cl.show(keyboardContainer, panelName);
    }
    
    /** * Statische Methode zum Starten der Tastatur.
     * Alte Version: public static void keyboard(JTextComponent textField, String type)
     */
    public static void keyboard(JTextComponent textField) {
        new CustomKeyboard(textField);
    }
}