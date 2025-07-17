package View;

import java.awt.EventQueue;
import javax.swing.*;

import org.json.JSONException;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;

public class StaffBookingTicketGUI {

	private JFrame frame;
	private JComboBox<String> destinationBox;
	private JTextField departDateField; 
	private JButton searchButton;

	private JButton btnBack;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StaffBookingTicketGUI window = new StaffBookingTicketGUI();
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
	public StaffBookingTicketGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Search Bus");
		frame.getContentPane().setBackground(new Color(130, 182, 234));
		frame.setBounds(100, 100, 650, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel title = new JLabel("SEARCH BUS");
		title.setForeground(new Color(0, 64, 128));
		title.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 25));
		title.setBounds(255, 25, 200, 30);
		frame.getContentPane().add(title);

		JLabel originLabel = new JLabel("Origin:");
		originLabel.setBounds(70, 100, 80, 25);
		originLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 16));
		frame.getContentPane().add(originLabel);

		JLabel destinationLabel = new JLabel("Destination:");
		destinationLabel.setBounds(70, 150, 80, 25);
		destinationLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 16));
		frame.getContentPane().add(destinationLabel);

		destinationBox = new JComboBox<>(new String[] {
			"-- Select Location --", "Terminal Bersepadu Selatan", "Melaka Sentral", "Larkin Sentral", "Penang Sentral", "Ipoh Amanjaya"
		});
		destinationBox.setBounds(300, 150, 200, 25);
		frame.getContentPane().add(destinationBox);

		JLabel departDateLabel = new JLabel("Depart Date (YYYY-MM-DD):");
		departDateLabel.setBounds(70, 200, 200, 25);
		departDateLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 16));
		frame.getContentPane().add(departDateLabel);

		departDateField = new JTextField();
		departDateField.setBounds(300, 200, 200, 25);
		frame.getContentPane().add(departDateField);

		searchButton = new JButton("Search");
		searchButton.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 16));
		searchButton.setBounds(400, 270, 100, 30);
		frame.getContentPane().add(searchButton);

		btnBack = new JButton("Back");
		btnBack.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 16));
		btnBack.setBounds(510, 270, 94, 30);
		frame.getContentPane().add(btnBack);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(300, 101, 200, 22);
		frame.getContentPane().add(textArea);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBounds(101, 322, 417, 177);
		frame.getContentPane().add(textArea_1);
		
		JButton btnNewButton = new JButton("Confirm");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(281, 521, 85, 21);
		frame.getContentPane().add(btnNewButton);
		
		btnBack.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            frame.dispose(); 
	            new StaffDashboardGUI(); 
	        }
	    });

		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String origin = (String) originBox.getSelectedItem();
				String destination = (String) destinationBox.getSelectedItem();
				String departDate = departDateField.getText().trim();

				if (origin.equals("-- Select Location --")) {
					JOptionPane.showMessageDialog(null, "Please select a valid origin.");
					return;
				}
				if (destination.equals("-- Select Location --")) {
					JOptionPane.showMessageDialog(null, "Please select a valid destination.");
					return;
				}
				if (origin.equals(destination)) {
					JOptionPane.showMessageDialog(null, "Origin and destination cannot be the same.");
					return;
				}

				try {
					LocalDate.parse(departDate); 
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Invalid date format! Please use YYYY-MM-DD.");
					return;
				}

				try {
					URL url = new URL("http://localhost/BusResponse.php"); 
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setRequestMethod("GET");
					conn.setRequestProperty("Accept", "application/json");

					if (conn.getResponseCode() != 200) {
						throw new RuntimeException("HTTP error code: " + conn.getResponseCode());
					}

					BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
					StringBuilder sb = new StringBuilder();
					String output;
					while ((output = br.readLine()) != null) {
						sb.append(output);
					}
					conn.disconnect();

					org.json.JSONArray trips = new org.json.JSONArray(sb.toString());
					StringBuilder result = new StringBuilder();
					for (int i = 0; i < trips.length(); i++) {
						org.json.JSONObject trip = trips.getJSONObject(i);

						if (trip.getString("fromStation").equals(origin) && trip.getString("toStation").equals(destination)) {
							result.append("Trip ID: ").append(trip.getInt("tripID"))
								  .append(", Bus: ").append(trip.getString("plateNo"))
								  .append(", Departure: ").append(trip.getString("departureTime"))
								  .append(", Arrival: ").append(trip.getString("arrivalTime"))
								  .append(", Price: RM").append(trip.getDouble("price"))
								  .append("\n");
						}
					}

					tripsPanel.removeAll();

					boolean found = false;
					for (int i = 0; i < trips.length(); i++) {
						org.json.JSONObject trip = trips.getJSONObject(i);

						if (trip.getString("fromStation").equals(origin) &&
							trip.getString("toStation").equals(destination) &&
							trip.getString("departureTime").split(" ")[0].equals(departDate)) {

							found = true;

							JPanel tripPanel = new JPanel();
							tripPanel.setLayout(new GridLayout(0, 1));
							tripPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
							tripPanel.setBackground(new Color(230, 240, 255));

							String tripInfo = "<html>Trip ID: " + trip.getInt("tripID") +
									"<br>Bus: " + trip.getString("plateNo") +
									"<br>Departure: " + trip.getString("departureTime") +
									"<br>Arrival: " + trip.getString("arrivalTime") +
									"<br>Price: RM" + trip.getDouble("price") + "</html>";

							JLabel tripLabel = new JLabel(tripInfo);
							JButton bookBtn = new JButton("Book");
							bookBtn.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 20));

							bookBtn.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									int tripID = 0;
									try {
										tripID = trip.getInt("tripID");
									} catch (JSONException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									String plateNo = null;
									try {
										plateNo = trip.getString("plateNo");
									} catch (JSONException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									String departure = null;
									try {
										departure = trip.getString("departureTime");
									} catch (JSONException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									String arrival = null;
									try {
										arrival = trip.getString("arrivalTime");
									} catch (JSONException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									double price = 0;
									try {
										price = trip.getDouble("price");
									} catch (JSONException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}

									new CustomerBookingGUI(tripID, origin, destination, departDate, plateNo, departure, arrival, price);
								}
							});

							tripPanel.add(tripLabel);
							tripPanel.add(bookBtn);

							tripsPanel.add(tripPanel);
						}
					}

					if (!found) {
						JOptionPane.showMessageDialog(null, "No trips found for selected route and date.");
					}
					tripsPanel.revalidate();
					tripsPanel.repaint();

				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error fetching bus data: " + ex.getMessage());
				}

			}
		});
	}
}