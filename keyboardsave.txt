package com.wegapplikation;

import javax.swing.*;
import java.awt.*;

public class CustomKeyboard extends JFrame {
    private JTextField targetField;
    private boolean[] capsLock;
    private JTextField displayField;

    public CustomKeyboard(JTextField target) {
        this.targetField = target;
        this.capsLock = new boolean[]{false};
        initializeKeyboard();
    }

    private void initializeKeyboard() {
        setTitle("Virtuelle Tastatur");
        setLayout(new BorderLayout(5, 5));
        setSize(900, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Display field
        displayField = new JTextField(targetField.getText());
        displayField.setEditable(false);
        displayField.setFont(new Font("Arial", Font.BOLD, 18));
        displayField.setPreferredSize(new Dimension(900, 40));
        add(displayField, BorderLayout.NORTH);

        // Keyboard panel
        JPanel keyboardPanel = new JPanel(new GridLayout(5, 1, 5, 5));
        keyboardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        addNumberRow(keyboardPanel);
        addFirstLetterRow(keyboardPanel);
        addSecondLetterRow(keyboardPanel);
        addThirdLetterRow(keyboardPanel);
        addControlRow(keyboardPanel);

        add(keyboardPanel, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addNumberRow(JPanel panel) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        String[] numbers = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
        for (String key : numbers) {
            row.add(createKeyButton(key));
        }
        panel.add(row);
    }

    private void addFirstLetterRow(JPanel panel) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        String[] keys = {"Q", "W", "E", "R", "T", "Z", "U", "I", "O", "P"};
        for (String key : keys) {
            row.add(createKeyButton(key));
        }
        panel.add(row);
    }

    private void addSecondLetterRow(JPanel panel) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        String[] keys = {"A", "S", "D", "F", "G", "H", "J", "K", "L"};
        for (String key : keys) {
            row.add(createKeyButton(key));
        }
        panel.add(row);
    }

    private void addThirdLetterRow(JPanel panel) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        
        // CAPS button
        JButton capsButton = new JButton("CAPS");
        capsButton.setPreferredSize(new Dimension(100, 40));
        capsButton.setFont(new Font("Arial", Font.BOLD, 14));
        capsButton.addActionListener(e -> {
            capsLock[0] = !capsLock[0];
            capsButton.setBackground(capsLock[0] ? Color.LIGHT_GRAY : null);
        });
        row.add(capsButton);

        String[] keys = {"Y", "X", "C", "V", "B", "N", "M", "<-"};
        for (String key : keys) {
            row.add(createKeyButton(key));
        }

        // ENTER button
        JButton enterButton = new JButton("ENTER");
        enterButton.setPreferredSize(new Dimension(100, 40));
        enterButton.setFont(new Font("Arial", Font.BOLD, 14));
        enterButton.addActionListener(e -> {
            targetField.setText(displayField.getText());
            dispose();
        });
        row.add(enterButton);

        panel.add(row);
    }

    private void addControlRow(JPanel panel) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        
        // Space button
        JButton spaceButton = new JButton("Leertaste");
        spaceButton.setPreferredSize(new Dimension(400, 40));
        spaceButton.setFont(new Font("Arial", Font.BOLD, 14));
        spaceButton.addActionListener(e -> {
            displayField.setText(displayField.getText() + " ");
        });
        row.add(spaceButton);
        
        panel.add(row);
    }

    private JButton createKeyButton(String key) {
        JButton button = new JButton(key);
        button.setPreferredSize(new Dimension(60, 40));
        button.setFont(new Font("Arial", Font.BOLD, 14));

        if (key.equals("<-")) {
            button.addActionListener(e -> {
                String text = displayField.getText();
                if (!text.isEmpty()) {
                    displayField.setText(text.substring(0, text.length() - 1));
                }
            });
        } else {
            button.addActionListener(e -> {
                String inputKey = capsLock[0] ? key.toUpperCase() : key.toLowerCase();
                displayField.setText(displayField.getText() + inputKey);
            });
        }

        return button;
    }

    public void keyboard(JTextField textField) {
        new CustomKeyboard(textField);
    }
}