package com.wegapplikation.config;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CustomKeyboard extends JFrame {
    private JTextField targetField;
    private boolean isCapsLock = true;
    private JTextField displayField;

    private JPanel keyboardContainer;
    private final String LETTERS_PANEL = "letters";
    private final String SYMBOLS_PANEL = "symbols";
    private final Color BG_COLOR = new Color(175, 189, 193);

    private List<JButton> letterButtons = new ArrayList<>();

    public CustomKeyboard(JTextField target) {
        this.targetField = target;
        initializeKeyboard();
    }

    private void initializeKeyboard() {
        setTitle("Virtuelle Tastatur");
        setLayout(new BorderLayout(5, 5));
        setSize(925, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(BG_COLOR);

        displayField = new JTextField(targetField.getText());
        displayField.setEditable(false);
        displayField.setFont(new Font("Arial", Font.BOLD, 18));
        displayField.setPreferredSize(new Dimension(700, 40));
        displayField.setBackground(BG_COLOR);
        add(displayField, BorderLayout.NORTH);

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

        // Seite 2 – Sonderzeichen
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

        // Umschalten
        JButton toggle = new JButton(isLettersPanel ? "123" : "ABC");
        toggle.setPreferredSize(new Dimension(85, 50));
        toggle.setFont(new Font("Arial", Font.BOLD, 14));
        toggle.addActionListener(e -> switchKeyboardPanel(isLettersPanel ? SYMBOLS_PANEL : LETTERS_PANEL));
        row.add(toggle);

        // Leertaste
        JButton space = new JButton("Leertaste");
        space.setPreferredSize(new Dimension(500, 50));
        space.setFont(new Font("Arial", Font.BOLD, 14));
        space.addActionListener(e -> displayField.setText(displayField.getText() + " "));
        row.add(space);

  // Backspace
  JButton backspace = new JButton("<-");
  backspace.setPreferredSize(new Dimension(85, 50));
  backspace.setFont(new Font("Arial", Font.BOLD, 14));
  backspace.addActionListener(e -> {
      String text = displayField.getText();
      if (!text.isEmpty()) {
          displayField.setText(text.substring(0, text.length() - 1));
      }
  });
  row.add(backspace);

        // ENTER
        JButton enter = new JButton("ENTER");
        enter.setPreferredSize(new Dimension(120, 50));
        enter.setFont(new Font("Arial", Font.BOLD, 14));
        enter.addActionListener(e -> {
            targetField.setText(displayField.getText());
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
            displayField.setText(displayField.getText() + inputKey);
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

    public void keyboard(JTextField textField) {
        new CustomKeyboard(textField);
    }
}
