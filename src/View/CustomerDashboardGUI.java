package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import javax.swing.JButton;

public class CustomerDashboardGUI {
	
	private int userID;

	private JFrame frame;

	/**
	 * Create the application.
	 */
	public CustomerDashboardGUI(int userID) {
		this.userID = userID;
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
		lblNewLabel.setBounds(66, 40, 350, 28);
		frame.getContentPane().add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Ticket Booking");
		btnNewButton.setFont(new Font("Tw Cen MT Condensed", Font.PLAIN, 20));
		btnNewButton.setBounds(157, 94, 121, 38);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnViewTicket = new JButton("View Ticket");
		btnViewTicket.setFont(new Font("Tw Cen MT Condensed", Font.PLAIN, 20));
		btnViewTicket.setBounds(157, 151, 121, 38);
		frame.getContentPane().add(btnViewTicket);
		
		JButton btnBack = new JButton("Back");
		btnBack.setFont(new Font("Tw Cen MT Condensed", Font.PLAIN, 20));
		btnBack.setBounds(347, 214, 65, 28);
		frame.getContentPane().add(btnBack);
		
		btnBack.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            frame.dispose(); 
	            new LoginGUI(); 
	        }
	    });
		
		btnNewButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        frame.dispose();
		        new CustomerSearchBusGUI(userID); 
		    }
		});
		
		btnViewTicket.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        frame.dispose();
		        new CustomerViewTicketGUI(userID); 
		    }
		});

	}

}
