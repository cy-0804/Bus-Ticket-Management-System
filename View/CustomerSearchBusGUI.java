package View;

import java.awt.EventQueue;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CustomerSearchBusGUI {

	private JFrame frame;

	private JComboBox<String> originBox;
	private JComboBox<String> destinationBox;
	private JTextField dateField; 
	private JButton searchButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CustomerSearchBusGUI window = new CustomerSearchBusGUI();
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
	public CustomerSearchBusGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(130, 182, 234));
		frame.setBounds(100, 100, 650, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel title = new JLabel("SEARCH BUS");
		title.setForeground(new Color(0, 64, 128));
		title.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 25));
		title.setBounds(270, 25, 200, 30);
		frame.getContentPane().add(title);

		JLabel originLabel = new JLabel("Origin:");
		originLabel.setBounds(70, 100, 80, 25);
		originLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 16));
		frame.getContentPane().add(originLabel);

		originBox = new JComboBox<>(new String[] {
			"-- Select Location --", "Kuala Lumpur", "Melaka", "Johor Bahru", "Butterworth", "Ipoh"
		});
		originBox.setBounds(230, 100, 200, 25);
		frame.getContentPane().add(originBox);

		JLabel destinationLabel = new JLabel("Destination:");
		destinationLabel.setBounds(70, 150, 80, 25);
		destinationLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 16));
		frame.getContentPane().add(destinationLabel);

		destinationBox = new JComboBox<>(new String[] {
			"-- Select Location --", "Kuala Lumpur", "Melaka", "Johor Bahru", "Butterworth", "Ipoh"
		});
		destinationBox.setBounds(230, 150, 200, 25);
		frame.getContentPane().add(destinationBox);

		JLabel dateLabel = new JLabel("Date (YYYY-MM-DD):");
		dateLabel.setBounds(70, 200, 160, 25);
		dateLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 16));
		frame.getContentPane().add(dateLabel);

		dateField = new JTextField();
		dateField.setBounds(230, 200, 200, 25);
		frame.getContentPane().add(dateField);

		searchButton = new JButton("Search");
		searchButton.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 16));
		searchButton.setBounds(280, 260, 100, 30);
		frame.getContentPane().add(searchButton);

		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String origin = (String) originBox.getSelectedItem();
				String destination = (String) destinationBox.getSelectedItem();
				String date = dateField.getText();

				
			}
		});
	}
}
