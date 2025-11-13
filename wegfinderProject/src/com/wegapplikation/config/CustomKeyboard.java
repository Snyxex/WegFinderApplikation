package com.wegapplikation.config;

import javax.swing.*;
import javax.swing.text.JTextComponent; 
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of a virtual keyboard using Swing.
 * It provides a graphical keyboard interface that can be used to input text
 * into a target {@link JTextComponent} (JTextField or JPasswordField).
 */
public class CustomKeyboard extends JFrame {
    
    /** The original target field where the final text/password will be transferred. */
    private JTextComponent targetField; 
    
    /** The currently visible and editable display field (JTextField or JPasswordField). */
    private JTextComponent activeDisplayField;
    
    /** Flag to track the CAPS lock state (true for uppercase). */
    private boolean isCapsLock = true;
    
    private JPanel keyboardContainer;
    private final String LETTERS_PANEL = "letters";
    private final String SYMBOLS_PANEL = "symbols";
    private final Color BG_COLOR = new Color(175, 189, 193);

    /** List of all letter buttons, used to easily update the CAPS state. */
    private List<JButton> letterButtons = new ArrayList<>();

    /**
     * Constructs a new instance of the virtual keyboard.
     * The type of the target field (normal/password) is automatically detected.
     *
     * @param target The JTextComponent (JTextField or JPasswordField) that receives the final input.
     */
    public CustomKeyboard(JTextComponent target) {
        this.targetField = target;
        initializeKeyboard(); 
    }

    /**
     * Initializes the main JFrame, sets up the layout, creates the display field,
     * and builds the keyboard panels (letters and symbols).
     */
    private void initializeKeyboard() {
        setTitle("Virtuelle Tastatur");
        setLayout(new BorderLayout(5, 5));
        setSize(945, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(BG_COLOR);

        // 1. Read initial text from the target field
        String initialText = "";
        // Automatic detection of the field type
        boolean isPasswordField = (targetField instanceof JPasswordField);
        
        if (isPasswordField) {
             // NOTE: getPassword() is used to read the existing password, 
             // but ideally should be handled carefully.
            initialText = new String(((JPasswordField) targetField).getPassword());
        } else {
            initialText = targetField.getText();
        }

        // 2. Create and initialize the display field based on the detected type
        if (isPasswordField){
            JPasswordField passwordField = new JPasswordField(initialText);
            passwordField.setEditable(false);
            passwordField.setFont(new Font("Arial", Font.BOLD, 18));
            passwordField.setPreferredSize(new Dimension(700, 40));
            passwordField.setBackground(BG_COLOR);
            add(passwordField, BorderLayout.NORTH);
            activeDisplayField = passwordField; // Set the active field
        } else {
            JTextField displayField = new JTextField(initialText);
            displayField.setEditable(false);
            displayField.setFont(new Font("Arial", Font.BOLD, 18));
            displayField.setPreferredSize(new Dimension(700, 40));
            displayField.setBackground(BG_COLOR);
            add(displayField, BorderLayout.NORTH);
            activeDisplayField = displayField; // Set the active field
        }
        
        keyboardContainer = new JPanel(new CardLayout());
        keyboardContainer.setBackground(BG_COLOR);

        // Panel 1 – Letters
        JPanel lettersPanel = new JPanel(new GridLayout(5, 1, 5, 5));
        lettersPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        lettersPanel.setBackground(BG_COLOR);
        lettersPanel.add(createRow(new String[]{"1","2","3","4","5","6","7","8","9","0"}, false));
        lettersPanel.add(createRow(new String[]{"Q","W","E","R","T","Z","U","I","O","P"}, true));
        lettersPanel.add(createRow(new String[]{"A","S","D","F","G","H","J","K","L","Ü"}, true));
        lettersPanel.add(createRow(new String[]{"Y","X","C","V","B","N","M","Ö","Ä"}, true));
        lettersPanel.add(createControlRow(true));

        
        // Panel 2 – Symbols
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
    
    /** * Reads the current text content from the active display field.
     * @return The current text as a String.
     */
    private String getActiveText() {
        if (activeDisplayField instanceof JPasswordField) {
            // JPasswordField returns char[], converting to String
            return new String(((JPasswordField) activeDisplayField).getPassword());
        } else if (activeDisplayField instanceof JTextField) {
            return ((JTextField) activeDisplayField).getText();
        }
        return "";
    }
    
    /** * Sets the text content in the active display field.
     * @param text The new text to set.
     */
    private void setActiveText(String text) {
        if (activeDisplayField instanceof JPasswordField) {
            ((JPasswordField) activeDisplayField).setText(text);
        } else if (activeDisplayField instanceof JTextField) {
            ((JTextField) activeDisplayField).setText(text);
        }
    }


    /**
     * Creates a JPanel row containing the specified keys.
     *
     * @param keys An array of strings representing the key labels.
     * @param isLetterRow {@code true} if the keys are letters and should be affected by CAPS lock.
     * @return A JPanel representing the row of buttons.
     */
    private JPanel createRow(String[] keys, boolean isLetterRow) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        row.setBackground(BG_COLOR);
        for (String key : keys) {
            JButton button = createKeyButton(key, isLetterRow);
            row.add(button);
        }
        return row;
    }

    /**
     * Creates the control row containing special keys like CAPS, toggle, space, backspace, and ENTER.
     *
     * @param isLettersPanel {@code true} if this control row is for the letters panel, enabling CAPS button functionality.
     * @return A JPanel representing the control row.
     */
    private JPanel createControlRow(boolean isLettersPanel) {

        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        row.setBackground(BG_COLOR);

        // CAPS Button
        JButton capsButton = new JButton("CAPS");
        capsButton.setPreferredSize(new Dimension(85, 50));
        capsButton.setFont(new Font("Arial", Font.BOLD, 14));
        if (isLettersPanel) {
            capsButton.addActionListener(e -> {
                isCapsLock = !isCapsLock;
                // Highlight the CAPS button when active
                capsButton.setBackground(isCapsLock ? Color.LIGHT_GRAY : null); 
                updateCapsState();
            });
            capsButton.setBackground(isCapsLock ? Color.LIGHT_GRAY : null); // Set initial state
        } else {
            capsButton.setEnabled(false);
        }
        row.add(capsButton);

   
        // Panel Toggle Button
        JButton toggle = new JButton(isLettersPanel ? "123" : "ABC");
        toggle.setPreferredSize(new Dimension(85, 50));
        toggle.setFont(new Font("Arial", Font.BOLD, 14));
        toggle.addActionListener(e -> switchKeyboardPanel(isLettersPanel ? SYMBOLS_PANEL : LETTERS_PANEL));
        row.add(toggle);


        // Space Button
        JButton space = new JButton("Leertaste"); // Spacebar
        space.setPreferredSize(new Dimension(500, 50));
        space.setFont(new Font("Arial", Font.BOLD, 14));
        space.addActionListener(e -> setActiveText(getActiveText() + " ")); 
        row.add(space);


        // Backspace Button
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


        // ENTER Button
        JButton enter = new JButton("ENTER");
        enter.setPreferredSize(new Dimension(120, 50));
        enter.setFont(new Font("Arial", Font.BOLD, 14));
        enter.addActionListener(e -> {
            // Transfer text back to the original target field
            targetField.setText(getActiveText()); 
            dispose();
        });
        row.add(enter);

        return row;
    }

    /**
     * Creates a standard key button and assigns its action listener.
     *
     * @param key The string label for the button.
     * @param isLetter {@code true} if the key is a letter, affecting its case based on CAPS lock state.
     * @return The configured JButton.
     */
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

    /**
     * Iterates over all letter buttons and updates their displayed text
     * to uppercase or lowercase based on the current {@code isCapsLock} state.
     */
    private void updateCapsState() {

        for (JButton button : letterButtons) {
            String text = button.getText();
            if (text.length() == 1 && Character.isLetter(text.charAt(0))) {
                button.setText(isCapsLock ? text.toUpperCase() : text.toLowerCase());
            }
        }
    }

    /**
     * Switches the currently visible panel in the {@code keyboardContainer}
     * using the CardLayout.
     *
     * @param panelName The name of the panel to switch to (e.g., {@code LETTERS_PANEL}).
     */
    private void switchKeyboardPanel(String panelName) {

        CardLayout cl = (CardLayout) keyboardContainer.getLayout();
        cl.show(keyboardContainer, panelName);
    }
    
    /** * Static method to launch the virtual keyboard.
     * It creates a new instance of {@code CustomKeyboard} for the specified target field.
     *
     * @param textField The {@link JTextComponent} (JTextField or JPasswordField) that will receive the input.
     */
    public static void keyboard(JTextComponent textField) {
        new CustomKeyboard(textField);
    }
}