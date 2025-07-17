package View;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel; 
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener; 
import java.io.*;
import java.net.*;
import org.json.*;
import java.time.LocalDate; 
import java.time.format.DateTimeParseException; 

public class StaffBookingTicketGUI {

	private JFrame frame;
	private JComboBox<String> destinationBox;
	private JTextField departDateField;
	private JTextField originField;
	private JTable tripsTable; // Replaced JTextArea
	private DefaultTableModel tableModel; // Model for the JTable
	private JButton confirmButton; // Made accessible to enable/disable
	private int userID;
	private int selectedTripID = -1; // Initialize with an invalid ID
	private double selectedPrice = 0.0;

	public StaffBookingTicketGUI(int userID) {
		this.userID = userID;
		initialize();
		frame.setVisible(true);
	}

	private void initialize() {
		frame = new JFrame("Search Bus");
		frame.getContentPane().setBackground(new Color(130, 182, 234));
		frame.setBounds(100, 100, 800, 650); // Increased frame size for table
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel title = new JLabel("SEARCH BUS");
		title.setForeground(new Color(0, 64, 128));
		title.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 25));
		title.setBounds(320, 25, 200, 30);
		frame.getContentPane().add(title);

		JLabel originLabel = new JLabel("Origin:");
		originLabel.setBounds(70, 100, 80, 25);
		originLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 16));
		frame.getContentPane().add(originLabel);

		originField = new JTextField();
		originField.setBounds(300, 100, 250, 25); // Adjusted width
		frame.getContentPane().add(originField);

		JLabel destinationLabel = new JLabel("Destination:");
		destinationLabel.setBounds(70, 150, 80, 25);
		destinationLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 16));
		frame.getContentPane().add(destinationLabel);

		destinationBox = new JComboBox<>(new String[]{
			"-- Select Location --", "Terminal Bersepadu Selatan", "Melaka Sentral", "Larkin Sentral", "Penang Sentral", "Ipoh Amanjaya"
		});
		destinationBox.setBounds(300, 150, 250, 25); // Adjusted width
		frame.getContentPane().add(destinationBox);

		JLabel departDateLabel = new JLabel("Depart Date (YYYY-MM-DD):");
		departDateLabel.setBounds(70, 200, 200, 25);
		departDateLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 16));
		frame.getContentPane().add(departDateLabel);

		departDateField = new JTextField();
		departDateField.setBounds(300, 200, 250, 25); // Adjusted width
		frame.getContentPane().add(departDateField);

		JButton searchButton = new JButton("Search");
		searchButton.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 16));
		searchButton.setBounds(450, 270, 100, 30);
		frame.getContentPane().add(searchButton);

		JButton btnBack = new JButton("Back");
		btnBack.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 16));
		btnBack.setBounds(560, 270, 94, 30);
		frame.getContentPane().add(btnBack);

		// --- JTable setup ---
		String[] columnNames = {"Trip ID", "From", "To", "Departure Time", "Arrival Time", "Plate No", "Price (RM)", "Seats"};
		tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };
		tripsTable = new JTable(tableModel);
		tripsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Allow only single row selection
		JScrollPane scrollPane = new JScrollPane(tripsTable);
		scrollPane.setBounds(50, 320, 700, 200); // Adjusted bounds for table
		frame.getContentPane().add(scrollPane);

		// Add a listener to the table for row selection
		tripsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) { // Ensure event fires only once
                    int selectedRow = tripsTable.getSelectedRow();
                    if (selectedRow != -1) {
                        // A row is selected, extract tripID and price
                        selectedTripID = (int) tripsTable.getValueAt(selectedRow, 0); // Trip ID is in column 0
                        selectedPrice = (double) tripsTable.getValueAt(selectedRow, 6); // Price is in column 6
                        confirmButton.setEnabled(true); // Enable confirm button
                    } else {
                        // No row is selected
                        selectedTripID = -1;
                        selectedPrice = 0.0;
                        confirmButton.setEnabled(false); // Disable confirm button
                    }
                }
            }
        });


		confirmButton = new JButton("Confirm");
		confirmButton.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 16));
		confirmButton.setBounds(350, 540, 100, 30); // Adjusted bounds
		confirmButton.setEnabled(false); // Initially disabled until a trip is selected
		frame.getContentPane().add(confirmButton);

		btnBack.addActionListener(e -> {
			frame.dispose();
			new StaffDashboardGUI(userID); // Assuming StaffDashboardGUI exists and takes userID
		});

		// Set a default origin for convenience in testing, can be removed later
		originField.setText("Terminal Bersepadu Selatan");

		searchButton.addActionListener(e -> {
			try {
				String origin = originField.getText().trim();
				String destination = destinationBox.getSelectedItem().toString();
				String date = departDateField.getText().trim();

				// Validation
				if (origin.isEmpty()) {
					JOptionPane.showMessageDialog(frame, "Please enter an origin.", "Input Error", JOptionPane.WARNING_MESSAGE);
					return;
				}
				if (destination.equals("-- Select Location --")) {
					JOptionPane.showMessageDialog(frame, "Please select a destination.", "Input Error", JOptionPane.WARNING_MESSAGE);
					return;
				}
				if (origin.equals(destination)) {
					JOptionPane.showMessageDialog(frame, "Origin and destination cannot be the same.", "Input Error", JOptionPane.WARNING_MESSAGE);
					return;
				}
				if (date.isEmpty()) {
					JOptionPane.showMessageDialog(frame, "Please enter a departure date.", "Input Error", JOptionPane.WARNING_MESSAGE);
					return;
				}
				try {
                    LocalDate.parse(date); // Validate date format
                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid date format! Please use YYYY-MM-DD.", "Input Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }


				fetchTripData(origin, destination, date);
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(frame, "Error fetching bus data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		});

		confirmButton.addActionListener(e -> {
			if (selectedTripID != -1) {
				// Proceed to StaffPaymentGUI with the selected trip details
				frame.dispose();
				// Ensure StaffPaymentGUI constructor matches these parameters
				new StaffPaymentGUI(userID, selectedTripID, selectedPrice);
			} else {
				JOptionPane.showMessageDialog(frame, "No trip selected. Please select a trip from the table.", "Selection Error", JOptionPane.WARNING_MESSAGE);
			}
		});
	}

	private void fetchTripData(String origin, String destination, String date) throws Exception {
		// Clear previous search results and disable confirm button
		tableModel.setRowCount(0); // Clear all rows from the table
		confirmButton.setEnabled(false);
		selectedTripID = -1; // Reset selection

		String apiUrl = "http://localhost/webServiceJSON/staff_book_ticket.php?origin="
				+ URLEncoder.encode(origin, "UTF-8") + "&destination=" + URLEncoder.encode(destination, "UTF-8")
				+ "&date=" + URLEncoder.encode(date, "UTF-8");

		URL url = new URL(apiUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");

		int responseCode = conn.getResponseCode();
		if (responseCode != HttpURLConnection.HTTP_OK) {
			throw new IOException("HTTP error code: " + responseCode + " from " + apiUrl);
		}

		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String inputLine;
		StringBuilder response = new StringBuilder();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		conn.disconnect();

		JSONObject jsonResponse = new JSONObject(response.toString());

		if (!jsonResponse.getString("status").equals("success")) {
			JOptionPane.showMessageDialog(frame, "No trips found: " + jsonResponse.getString("message"), "No Results", JOptionPane.INFORMATION_MESSAGE);
			return; // No trips found, exit method
		}

		JSONArray trips = jsonResponse.getJSONArray("trips");

		if (trips.length() == 0) {
            JOptionPane.showMessageDialog(frame, "No trips found for the selected criteria.", "No Results", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

		for (int i = 0; i < trips.length(); i++) {
			JSONObject trip = trips.getJSONObject(i);
			Object[] rowData = {
				trip.getInt("tripID"),
				trip.getString("fromStation"),
				trip.getString("toStation"),
				trip.getString("departureTime"),
				trip.getString("arrivalTime"),
				trip.getString("plateNo"),
				trip.getDouble("price"),
				trip.getInt("availableSeats")
			};
			tableModel.addRow(rowData); // Add row to the table model
		}
	}
}