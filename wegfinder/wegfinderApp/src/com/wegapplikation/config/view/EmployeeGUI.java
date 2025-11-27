package com.wegapplikation.config.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import com.wegapplikation.config.controller.RouteCal;
import com.wegapplikation.config.view.LoginGUI; // WICHTIG: Fehlender Import

/**
 * Represents the main graphical user interface (GUI) for the Employee/User level
 * of the pathfinding application.
 * This class sets up the main application frame, the map panel (RouteCal), 
 * and controls for pathfinding input, path calculation, and login access.
 */
public class EmployeeGUI {

    /** The main application frame for the GUI. */
    public JFrame frame;
    /** The custom panel that handles the map drawing and route calculation logic. */
    public RouteCal panel;

    
    /**
     * Creates a new instance of the EmployeeGUI.
     * Initializes the frame and all components.
     */
    public EmployeeGUI() {
        initialize();
    }

    /**
     * Initializes the contents of the frame, including the layout,
     * the RouteCal panel, buttons, input fields, and event listeners.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setResizable(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximizes the frame
        frame.setBounds(100, 100, 1940, 1060);
        // Prevents closing the application by default (DO_NOTHING_ON_CLOSE is used)
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        frame.getContentPane().setLayout(null);

        // Panel for RouteCal (Map visualization and pathfinding logic)
        panel = new RouteCal();	
        panel.loggedin = true;
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
        
        // Listener to start the route calculation when the button is clicked
        btnNewButton_1.addMouseListener(new MouseAdapter() {
        	 public void mouseClicked(MouseEvent e) {
        	    panel.createRoute(); // Calls the controller method to calculate and draw the route
        	 }
        });
        
        frame.getContentPane().add(btnNewButton_1);
        
        
        // Mouse listener for the map panel (RouteCal) to handle touch/click events
        panel.addMouseListener(new java.awt.event.MouseAdapter() {
			
			public void mousePressed(java.awt.event.MouseEvent e) {
				panel.mouseheld = true;
			}
			
			/**
			 * Handles the mouse release event on the map panel.
			 * If a path (corridor/room) has been marked for modification 
			 * (only applicable in employee/admin mode), a dialog for 
			 * opening or closing the path is displayed.
			 * @param e The mouse event.
			 */
			public void mouseReleased(java.awt.event.MouseEvent e) {
				panel.mouseheld = false;
				
				// Check if a path (cmarkedpath or omarkedpath) has been marked 
				// and the marking process is active (ismarkin).
				if((panel.cmarkedpath != null || panel.omarkedpath != null) && panel.ismarkin == true) {			
					
					JDialog pathdialog = new JDialog();
		            pathdialog.setSize(500, 140);
		            pathdialog.getContentPane().setLayout(new BorderLayout());
                    pathdialog.setAlwaysOnTop(true);
		            // Text (in German): "Please enter a start and end room" (Contextually, this text seems misplaced for this dialog)
		            pathdialog.getContentPane().add(new JLabel("Bitte geben Sie einen Start- und einen Endraum an"));

		            JPanel buttonPanel = new JPanel();
		            buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

		            JButton btnnochange = new JButton("X");
		            JButton btnopenpath = new JButton("Weg öffnen"); 
		            JButton btnclosepath = new JButton("Weg schließen"); 

		            // Action listener for 'X' button (No Change)
		            btnnochange.addActionListener(new ActionListener() {
		            	public void actionPerformed(ActionEvent arg0) {
		            		panel.clearMarkedPaths(); // Clears the marked path state
		            		
		            	}
		            });
		            // Action listener for 'Weg öffnen' (Open Path)
		            btnopenpath.addActionListener(new ActionListener() {
		            	public void actionPerformed(ActionEvent arg0) {
		            		panel.openPath(); // Marks the path as open/passable
		            	}
		            });
		            // Action listener for 'Weg schließen' (Close Path)
		            btnclosepath.addActionListener(new ActionListener() {
		            	public void actionPerformed(ActionEvent arg0) {
		            		panel.closePath(); // Marks the path as closed/impassable
		            	}
		            });
		            
		            // Dispose the dialog after any button is clicked
		            btnnochange.addActionListener(event -> pathdialog.dispose());
		            btnopenpath.addActionListener(event -> pathdialog.dispose());
		            btnclosepath.addActionListener(event -> pathdialog.dispose());
		            
		            
		            
		            buttonPanel.add(btnnochange);
		            buttonPanel.add(btnclosepath);
		            buttonPanel.add(btnopenpath);

		            pathdialog.getContentPane().add(buttonPanel, BorderLayout.CENTER);

		            // Center the popup dialog on the screen and make it modal
		            pathdialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		            pathdialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		            pathdialog.setLocationRelativeTo(panel);

		            pathdialog.setVisible(true);
				}
			}
		});
        
        // Mouse motion listener for the map panel (RouteCal) to handle path marking via dragging
        panel.addMouseMotionListener(new java.awt.event.MouseMotionListener() {

			/**
			 * Called when the mouse button is pressed and the mouse is dragged.
			 * Used for drawing/marking a path segment for modification (if ismarkin is true).
			 */
			@Override
			public void mouseDragged(MouseEvent e) {
				if(panel.ismarkin)
				{
				int x = e.getX();
				int y = e.getY();
				panel.markpath(x, y);
				}
			}

			@Override
			public void mouseMoved(MouseEvent arg0) {
			}
        	
        });
        
        // "Von" (From) Text Field for starting point
        JFormattedTextField formattedTextField = new JFormattedTextField();
        formattedTextField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formattedTextField.setBackground(SystemColor.inactiveCaptionBorder);
        formattedTextField.setHorizontalAlignment(SwingConstants.CENTER);
        formattedTextField.setBounds(845, 809, 113, 41);
        frame.getContentPane().add(formattedTextField);

        // "Nach" (To) Text Field for destination point
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
        panel.vonField = formattedTextField;

        // "Nach" (To) Label
        JLabel lblNewLabel_1 = new JLabel("Nach");
        lblNewLabel_1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setBounds(968, 784, 113, 14);
        frame.getContentPane().add(lblNewLabel_1);
        panel.nachField = formattedTextField_1;

        // Button to open the LoginGUI (Admin/Employee Login)
        JButton btnNewButton_1_1_1 = new JButton("\u263C"); // Unicode character (looks like a sun/star) used for the login button
        btnNewButton_1_1_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		EventQueue.invokeLater(new Runnable() {
        	        public void run() { // 🛠️ Bereinigt von unsichtbaren Zeichen
        	            try {
        	                // Create an instance of LoginGUI and make it visible
        	                // and dispose the current EmployeeGUI frame.
        	                LoginGUI window = new LoginGUI();
        	                frame.dispose();
        	                window.frmMitarbeiterLogin.setVisible(true);
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

        
        // Text Panel with instructions/help (in German)
        JTextPane textPane = new JTextPane();
        textPane.setEditable(false);
        // Korrektur der Umlaute
        textPane.setText("Geben Sie im Feld \"Von\" ein, von wo Sie die Wegführung starten möchten. Geben Sie im Feld \"Nach\" ein, wohin Sie gelangen wollen. Drücken Sie danach \"Wegfindung starten\", um die Wegführung anzeigen zu lassen.");
        textPane.setBackground(SystemColor.info);
        textPane.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textPane.setBounds(1259, 809, 475, 71);
        frame.getContentPane().add(textPane);
        
        // Logout Button (Text in German: "Abmelden" - Log Out)
        JButton logout_btn = new JButton("Abmelden"); // Korrektur des Tippfehlers
        logout_btn.setForeground(SystemColor.textHighlightText);
        logout_btn.setFont(new Font("Segoe UI Symbol", Font.BOLD, 13));
        logout_btn.setBackground(SystemColor.textHighlight);
        logout_btn.setBounds(10, 11, 45, 33);
        frame.getContentPane().add(logout_btn);
        
        // Logout button listener (currently empty/incomplete in provided code)
        logout_btn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		frame.dispose();
        		MapGUI mapGUI = new MapGUI();
        		mapGUI.frame.setVisible(true);
        	};
        });

        // Click listener for the "Von" (From) text field to display the numeric keypad
        formattedTextField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showNumericKeypad(formattedTextField); // Show keypad for "From" field
            }
        });

        // Click listener for the "Nach" (To) text field to display the numeric keypad
        formattedTextField_1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showNumericKeypad(formattedTextField_1); // Show keypad for "To" field
            }
        });
    }

    /**
     * Displays a custom modal dialog containing a numeric keypad for input.
     * The input is applied to the specified target text field upon confirmation.
     * @param targetTextField The JFormattedTextField where the input should be placed.
     */
    private void showNumericKeypad(JFormattedTextField targetTextField) {
        // New modal dialog window for the numeric keypad
        JDialog keypadDialog = new JDialog(frame, "Zahleneingabe", true); // "Numeric Input"
        keypadDialog.setSize(300, 400);
        keypadDialog.setLocationRelativeTo(frame);
        keypadDialog.getContentPane().setLayout(new BorderLayout());

        // Input field in the dialog (read-only, displays what is being typed)
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
                    // Limit input length to 4 characters
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

        // Clear Button (Text in German: "Löschen")
        JButton btnClear = new JButton("Löschen"); 
        btnClear.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        btnClear.setForeground(SystemColor.textHighlightText);
        btnClear.setBackground(SystemColor.textHighlight);
        btnClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Deletes the last character from the input field
                String text = inputField.getText();
                if (text.length() > 0) {
                    inputField.setText(text.substring(0, text.length() - 1));
                }
            }
        });
        buttonPanel.add(btnClear);

        // Confirm Button (Text in German: "OK")
        JButton btnOk = new JButton("OK");
        btnOk.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        btnOk.setForeground(SystemColor.textHighlightText);
        btnOk.setBackground(SystemColor.textHighlight);
        btnOk.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Apply the entered value to the target field and close the dialog
                panel.addPoint(inputField.getText(), targetTextField); // Calls a method in RouteCal to handle point logic
                keypadDialog.dispose();
            }
        });
        buttonPanel.add(btnOk);

        // Make the dialog visible
        keypadDialog.setVisible(true);
    }
}