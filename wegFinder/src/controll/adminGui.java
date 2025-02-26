package controll;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;

public class adminGui extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					adminGui frame = new adminGui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public adminGui() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel dashboard_label = new JLabel("New label");
		dashboard_label.setBounds(188, 6, 61, 16);
		contentPane.add(dashboard_label);
		
		JButton userSettings_btn = new JButton("New button");
		userSettings_btn.setBounds(167, 54, 117, 29);
		contentPane.add(userSettings_btn);
		
		JButton roomSettings_btn = new JButton("New button");
		roomSettings_btn.setBounds(167, 116, 117, 29);
		contentPane.add(roomSettings_btn);
	}
}
