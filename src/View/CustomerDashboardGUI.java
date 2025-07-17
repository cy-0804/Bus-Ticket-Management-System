package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;

public class CustomerDashboardGUI {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CustomerDashboardGUI window = new CustomerDashboardGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CustomerDashboardGUI() {
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(130, 182, 234));
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Customer Ticket Booking System");
		lblNewLabel.setForeground(new Color(0, 64, 128));
		lblNewLabel.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 25));
		lblNewLabel.setBounds(66, 40, 308, 28);
		frame.getContentPane().add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Ticket Booking");
		btnNewButton.setFont(new Font("Tw Cen MT Condensed", Font.PLAIN, 20));
		btnNewButton.setBounds(157, 94, 121, 38);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnViewTicket = new JButton("View Ticket");
		btnViewTicket.setFont(new Font("Tw Cen MT Condensed", Font.PLAIN, 20));
		btnViewTicket.setBounds(157, 151, 121, 38);
		frame.getContentPane().add(btnViewTicket);
	}

}
