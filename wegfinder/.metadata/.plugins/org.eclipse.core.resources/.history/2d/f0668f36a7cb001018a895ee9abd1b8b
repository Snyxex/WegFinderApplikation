package com.wegapplikation.config.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import com.wegapplikation.config.controller.RouteCal;

/**
 * The {@code MapGUI} class represents the main graphical user interface (GUI)
 * for the pathfinding application. It sets up the main window,
 * displays the map/route calculation panel, and includes controls for
 * route selection and pathfinding.
 * * It serves as the primary view component of the application.
 */
public class MapGUI {

    /**
     * The main application frame.
     */
    public JFrame frame;
    
    /**
     * The panel dedicated to route calculation and display, controlled by {@code RouteCal}.
     */
    public RouteCal panel;

    /**
     * Main method to launch the application.
     * It ensures the GUI creation and display happens on the Event Dispatch Thread (EDT).
     * * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MapGUI window = new MapGUI();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Creates the application.
     */
    public MapGUI() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     * This method sets up the main window properties, adds the map panel,
     * input fields for "From" and "To" locations, a start button, 
     * and various event listeners.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setResizable(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setBounds(100, 100, 1940, 1060);
        // Do not close on exit, allowing custom handling (e.g., prompting user)
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        frame.getContentPane().setLayout(null);

        // Panel for RouteCal
        /**
         * The main panel where the map and route calculations are displayed.
         */
        panel = new RouteCal();	
		panel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBackground(SystemColor.activeCaption);
		panel.setBounds(557, 157, 819, 603);
		frame.getContentPane().add(panel);
        
        // Button "Wegfindung Starten" (Start Pathfinding)
        JButton btnNewButton_1 = new JButton("Wegfindung Starten");
        btnNewButton_1.setForeground(SystemColor.textHighlightText);
        btnNewButton_1.setBackground(SystemColor.textHighlight);
        btnNewButton_1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnNewButton_1.setBounds(881, 861, 162, 33);
        frame.getContentPane().add(btnNewButton_1);
        btnNewButton_1.addMouseListener(new MouseAdapter() {
        	 public void mouseClicked(MouseEvent e) {
        	// Calls the method in RouteCal to initiate the route calculation.
        	panel.createRoute();
        	 }
        });
        
        frame.getContentPane().add(btnNewButton_1);
        
        
        // Mouse listener for the RouteCal panel for custom path marking/selection
        panel.addMouseListener(new java.awt.event.MouseAdapter() {
			
			public void mousePressed(java.awt.event.MouseEvent e) {
				// Indicates that a mouse button is currently held down on the panel.
				panel.mouseheld = true;
			}
			public void mouseReleased(java.awt.event.MouseEvent e) {
				panel.mouseheld = false;
				
				// Checks if a path has been marked/selected and if the application is in marking mode.
				if((panel.cmarkedpath != null || panel.omarkedpath != null) && panel.ismarkin == true) {			
					
					// Dialog for path options (Open/Close path)
					JDialog pathdialog = new JDialog();
		            pathdialog.setSize(500, 140);
		            pathdialog.setLayout(new BorderLayout());
                    pathdialog.setAlwaysOnTop(true);
		            pathdialog.add(new JLabel("Bitte geben sie einen Start- und einen Endraum an")); // Please enter a start and end room

		            JPanel buttonPanel = new JPanel();
		            buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

		            JButton btnnochange = new JButton("X");
		            JButton btnopenpath = new JButton("Weg öffnen"); // Open Path
		            JButton btnclosepath = new JButton("Weg schließen"); // Close Path

		            // Action listener to clear the marked path
		            btnnochange.addActionListener(new ActionListener() {
		            	public void actionPerformed(ActionEvent arg0) {
		            		panel.clearMarkedPaths();
		            		
		            	}
		            });
		            // Action listener to open the path
		            btnopenpath.addActionListener(new ActionListener() {
		            	public void actionPerformed(ActionEvent arg0) {
		            		panel.openPath();
		            	}
		            });
		            // Action listener to close the path
		            btnclosepath.addActionListener(new ActionListener() {
		            	public void actionPerformed(ActionEvent arg0) {
		            		panel.closePath();
		            	}
		            });
		            
		            // Action listeners to dispose the dialog upon button click
		            btnnochange.addActionListener(event -> pathdialog.dispose());
		            btnopenpath.addActionListener(event -> pathdialog.dispose());
		            btnclosepath.addActionListener(event -> pathdialog.dispose());
		           
		            
		            
		            buttonPanel.add(btnnochange);
		            buttonPanel.add(btnclosepath);
		            buttonPanel.add(btnopenpath);

		            pathdialog.add(buttonPanel, BorderLayout.CENTER);

		            /** Center the popup in the middle of the screen **/
		            pathdialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		            pathdialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		            pathdialog.setLocationRelativeTo(panel);

		            pathdialog.setVisible(true);
			}
			}
		});
        
        // Mouse motion listener for path marking (dragging)
        panel.addMouseMotionListener(new java.awt.event.MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				// Only mark path if the application is in marking mode
				if(panel.ismarkin)
				{
				int x = e.getX();
				int y = e.getY();
				panel.markpath(x, y);
				}
			}

			@Override
			public void mouseMoved(MouseEvent arg0) {
			    // Not used
			}
        	
        });
        
        // "Von" (From) Text field
        JFormattedTextField formattedTextField = new JFormattedTextField();
        formattedTextField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formattedTextField.setBackground(SystemColor.inactiveCaptionBorder);
        formattedTextField.setHorizontalAlignment(SwingConstants.CENTER);
        formattedTextField.setBounds(845, 809, 113, 41);
        frame.getContentPane().add(formattedTextField);

        // "Nach" (To) Text field
        JFormattedTextField formattedTextField_1 = new JFormattedTextField();
        formattedTextField_1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formattedTextField_1.setBackground(SystemColor.inactiveCaptionBorder);
        formattedTextField_1.setHorizontalAlignment(SwingConstants.CENTER);
        formattedTextField_1.setBounds(968, 809, 113, 41);
        frame.getContentPane().add(formattedTextField_1);
        
        // "Von" (From) Label
        JLabel lblNewLabel = new JLabel("Von");
        lblNewLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(845, 784, 113, 14);
        frame.getContentPane().add(lblNewLabel);
        // Store reference to the "From" field in the RouteCal panel
        panel.vonField = formattedTextField;

        // "Nach" (To) Label
        JLabel lblNewLabel_1 = new JLabel("Nach");
        lblNewLabel_1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setBounds(968, 784, 113, 14);
        frame.getContentPane().add(lblNewLabel_1);
        // Store reference to the "To" field in the RouteCal panel
        panel.nachField = formattedTextField_1;

        // The button to open the numeric input window (likely a settings/login button, denoted by a gear icon)
        JButton btnNewButton_1_1_1 = new JButton("\u263C"); // Gear icon
        btnNewButton_1_1_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		EventQueue.invokeLater(new Runnable() {
        	        public void run() {
        	            try {
        	                // Create an instance of LoginGUI and show the window
        	                LoginGUI window = new LoginGUI();
        	                frame.dispose(); // Close the current MapGUI frame
        	                window.frmMitarbeiterLogin.setVisible(true); // Show the Login GUI
        	            } catch (Exception e) {
        	                e.printStackTrace();
        	            }
        	        }
        	    });
        	}
        });
        btnNewButton_1_1_1.setForeground(SystemColor.textHighlightText);
        btnNewButton_1_1_1.setFont(new Font("Segoe UI Symbol", Font.BOLD, 13));
        btnNewButton_1_1_1.setBackground(SystemColor.textHighlight);
        btnNewButton_1_1_1.setBounds(10, 814, 45, 33);
        frame.getContentPane().add(btnNewButton_1_1_1);

        
        // Text Panel with instructions
        JTextPane textPane = new JTextPane();
        textPane.setEditable(false);
        textPane.setText("Geben Sie im Feld Von ein, von wo Sie die Wegfhrung starten mchten. Geben Sie im Feld Nach ein, wohin Sie gelangen wollen. Drcken Sie danach Wegfindung starten, um die Wegfhrung anzeigen zu lassen.");
        // Translation: "Enter in the 'From' field where you want to start the pathfinding. Enter in the 'To' field where you want to go. Then press 'Start Pathfinding' to display the pathfinding."
        textPane.setBackground(SystemColor.info);
        textPane.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textPane.setBounds(1259, 809, 475, 71);
        frame.getContentPane().add(textPane);

        // Click listener for the "From" text field to show the numeric keypad
        formattedTextField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showNumericKeypad(formattedTextField); // Show keypad for "From"
            }
        });

        // Click listener for the "To" text field to show the numeric keypad
        formattedTextField_1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showNumericKeypad(formattedTextField_1); // Show keypad for "To"
            }
        });
    }

    /**
     * Displays a custom numeric keypad dialog to input room numbers into a target text field.
     * The input is limited to 4 digits.
     * * @param targetTextField The JFormattedTextField where the numeric input will be placed.
     */
    private void showNumericKeypad(JFormattedTextField targetTextField) {
        // New dialog window for the numeric keypad
        JDialog keypadDialog = new JDialog(frame, "Zahleneingabe", true); // Numeric Input
        keypadDialog.setSize(300, 400);


        // Input field in the dialog
        JTextField inputField = new JTextField();
        inputField.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        inputField.setHorizontalAlignment(SwingConstants.CENTER);
        inputField.setEditable(false);
        keypadDialog.getContentPane().add(inputField, BorderLayout.NORTH);

        // Panel for the number buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 3, 5, 5));
        keypadDialog.getContentPane().add(buttonPanel, BorderLayout.CENTER);

        // Number buttons (1-9)
        for (int i = 1; i <= 9; i++) {
            JButton btn = new JButton(String.valueOf(i));
            btn.setFont(new Font("Segoe UI", Font.PLAIN, 18));
            btn.setForeground(SystemColor.textHighlightText);
            btn.setBackground(SystemColor.textHighlight);
            btn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {                   
                        // Limit input to 4 characters
                        if (inputField.getText().length() < 4) {
                            inputField.setText(inputField.getText() + btn.getText());
                        }
                    }
                });
            buttonPanel.add(btn);
        }

        // "0" Button
        JButton btn0 = new JButton("0");
        btn0.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        btn0.setForeground(SystemColor.textHighlightText);
        btn0.setBackground(SystemColor.textHighlight);
        btn0.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	if (inputField.getText().length() < 4) {
                    inputField.setText(inputField.getText() + "0");
                }
            }
        });
        buttonPanel.add(btn0);

        // Clear Button ("Löschen")
        JButton btnClear = new JButton("Löschen");
        btnClear.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        btnClear.setForeground(SystemColor.textHighlightText);
        btnClear.setBackground(SystemColor.textHighlight);
        btnClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Remove the last character
                String text = inputField.getText();
                if (text.length() > 0) {
                    inputField.setText(text.substring(0, text.length() - 1));
                }
            }
        });
        buttonPanel.add(btnClear);

        // OK Button ("Bestätigen")
        JButton btnOk = new JButton("OK");
        btnOk.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        btnOk.setForeground(SystemColor.textHighlightText);
        btnOk.setBackground(SystemColor.textHighlight);
        btnOk.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Transfer input and close dialog
               // targetTextField.setText(inputField.getText()); // Commented out in original code
               
               // Calls RouteCal method to process the input and set the field
                panel.addPoint(inputField.getText(), targetTextField);
                keypadDialog.dispose();
            }
        });
        buttonPanel.add(btnOk);

        // Make the dialog visible
        keypadDialog.setVisible(true);
    }
}