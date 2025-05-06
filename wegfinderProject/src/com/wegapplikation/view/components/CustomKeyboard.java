package com.wegapplikation.view.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CustomKeyboard extends JFrame {
    private JTextField targetField;
    private boolean capsLock;
    private JTextField displayField;
    
    public CustomKeyboard(JTextField target) {
        this.targetField = target;
        this.capsLock = false;
        initializeKeyboard();
    }
    
    private void initializeKeyboard() {
        setTitle("Virtuelle Tastatur");
        setLayout(new BorderLayout(5, 5));
        setSize(900, 400);
        
        // Anzeige-Feld
        displayField = new JTextField(targetField.getText());
        displayField.setEditable(false);
        displayField.setFont(new Font("Arial", Font.BOLD, 18));
        displayField.setPreferredSize(new Dimension(900, 40));
        add(displayField, BorderLayout.NORTH);
        
        // Tastatur-Panel
        JPanel keyboardPanel = new JPanel(new GridLayout(5, 1, 5, 5));
        keyboardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        addNumberRow(keyboardPanel);
        addFirstLetterRow(keyboardPanel);
        addSecondLetterRow(keyboardPanel);
        addThirdLetterRow(keyboardPanel);
        addControlRow(keyboardPanel);
        
        add(keyboardPanel, BorderLayout.CENTER);
        
        // Bestätigen und Abbrechen Buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton confirmButton = new JButton("Bestätigen");
        JButton cancelButton = new JButton("Abbrechen");
        
        confirmButton.addActionListener(e -> {
            targetField.setText(displayField.getText());
            dispose();
        });
        
        cancelButton.addActionListener(e -> dispose());
        
        bottomPanel.add(confirmButton);
        bottomPanel.add(cancelButton);
        add(bottomPanel, BorderLayout.SOUTH);
        
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void addNumberRow(JPanel panel) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        String[] numbers = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "ß", "´", "<-"};
        for (String key : numbers) {
            if (key.equals("<-")) {
                addBackspaceButton(row);
            } else {
                row.add(createKeyButton(key));
            }
        }
        panel.add(row);
    }
    
    private void addFirstLetterRow(JPanel panel) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        String[] keys = {"q", "w", "e", "r", "t", "z", "u", "i", "o", "p", "ü", "+", "#"};
        for (String key : keys) {
            row.add(createKeyButton(key));
        }
        panel.add(row);
    }
    
    private void addSecondLetterRow(JPanel panel) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        String[] keys = {"a", "s", "d", "f", "g", "h", "j", "k", "l", "ö", "ä"};
        for (String key : keys) {
            row.add(createKeyButton(key));
        }
        panel.add(row);
    }
    
    private void addThirdLetterRow(JPanel panel) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        
        // Shift-Taste
        JButton shiftButton = new JButton("Shift");
        shiftButton.setPreferredSize(new Dimension(100, 40));
        shiftButton.addActionListener(e -> {
            capsLock = !capsLock;
            shiftButton.setBackground(capsLock ? Color.LIGHT_GRAY : null);
        });
        row.add(shiftButton);
        
        String[] keys = {"y", "x", "c", "v", "b", "n", "m", ",", ".", "-"};
        for (String key : keys) {
            row.add(createKeyButton(key));
        }
        panel.add(row);
    }
    
    private void addControlRow(JPanel panel) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        
        // Leertaste
        JButton spaceButton = new JButton("Leertaste");
        spaceButton.setPreferredSize(new Dimension(400, 40));
        spaceButton.addActionListener(e -> {
            String currentText = displayField.getText();
            displayField.setText(currentText + " ");
        });
        row.add(spaceButton);
        
        panel.add(row);
    }
    
    private void addBackspaceButton(JPanel panel) {
        JButton backspace = new JButton("←");
        backspace.setPreferredSize(new Dimension(60, 40));
        backspace.addActionListener(e -> {
            String currentText = displayField.getText();
            if (!currentText.isEmpty()) {
                displayField.setText(currentText.substring(0, currentText.length() - 1));
            }
        });
        panel.add(backspace);
    }
    
    private JButton createKeyButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(60, 40));
        button.addActionListener(e -> {
            String currentText = displayField.getText();
            String newText = capsLock ? text.toUpperCase() : text;
            displayField.setText(currentText + newText);
        });
        return button;
    }
}