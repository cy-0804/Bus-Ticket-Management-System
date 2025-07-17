package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;

public class StaffDashboardGUI {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StaffDashboardGUI window = new StaffDashboardGUI();
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
	public StaffDashboardGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(130, 182, 234));
		frame.setBounds(100, 100, 742, 387);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel title = new JLabel("STAFF TICKET MANAGEMENT SYSTEM");
		title.setForeground(new Color(0, 64, 128));
		title.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 25));
		title.setBounds(182, 31, 384, 58);
		frame.getContentPane().add(title);
		
		JButton ticketBookingBtn = new JButton("Ticket Booking");
		ticketBookingBtn.setFont(new Font("Tw Cen MT Condensed", Font.PLAIN, 20));
		ticketBookingBtn.setBounds(279, 118, 162, 39);
		frame.getContentPane().add(ticketBookingBtn);
		
		JButton passengerCheckInBtn = new JButton("Passenger Check In");
		passengerCheckInBtn.setFont(new Font("Tw Cen MT Condensed", Font.PLAIN, 20));
		passengerCheckInBtn.setBounds(279, 191, 162, 39);
		frame.getContentPane().add(passengerCheckInBtn);
		
		JButton viewBusSchduleBtn = new JButton("View Bus Schedule");
		viewBusSchduleBtn.setFont(new Font("Tw Cen MT Condensed", Font.PLAIN, 20));
		viewBusSchduleBtn.setBounds(279, 264, 162, 39);
		frame.getContentPane().add(viewBusSchduleBtn);
	}
}
