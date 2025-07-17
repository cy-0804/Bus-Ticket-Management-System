package View;

import java.awt.EventQueue;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StaffDashboardGUI {

	private JFrame frame;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				StaffDashboardGUI window = new StaffDashboardGUI();
				window.frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public StaffDashboardGUI() {
		initialize();
		frame.setVisible(true);
	}

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

		JButton viewBusScheduleBtn = new JButton("View Bus Schedule");
		viewBusScheduleBtn.setFont(new Font("Tw Cen MT Condensed", Font.PLAIN, 20));
		viewBusScheduleBtn.setBounds(279, 264, 162, 39);
		frame.getContentPane().add(viewBusScheduleBtn);
		
		passengerCheckInBtn.addActionListener(e -> {
			EventQueue.invokeLater(() -> {
				try {
					Staff_CheckInGUI checkin = new Staff_CheckInGUI();
					frame.dispose(); 
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			});
		});

		// ActionListener for View Bus Schedule
		viewBusScheduleBtn.addActionListener(e -> {
			EventQueue.invokeLater(() -> {
				try {
					ViewBusGUI viewBus = new ViewBusGUI();
					frame.dispose(); 
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			});
		});
	}
}
