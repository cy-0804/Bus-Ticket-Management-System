package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.util.*;
import org.json.*;

public class CustomerBookingGUI {
	private String origin, destination, departDate, arrivalDate, plateNo;
    private Set<String> selectedSeatIDs = new HashSet<>();
    private double seatPrice;
    private int tripID, userID;
    private JFrame frame;
    private JPanel seatPanel, infoPanel;

    public CustomerBookingGUI(int userID, int tripID, String origin, String destination,
                               String plateNo, String departure, String arrival, double price) {
        this.userID = userID;
    	this.tripID = tripID;
    	this.origin = origin;
    	this.destination = destination;
    	this.departDate = departure;
    	this.arrivalDate = arrival;
        this.seatPrice = price;
        this.plateNo = plateNo;

        frame = new JFrame("Seat Selection and Booking");
        frame.setSize(700, 500);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(130, 182, 234));

        JLabel title = new JLabel("SEAT SELECTION AND BOOKING");
        title.setForeground(new Color(0, 64, 128));
        title.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 25));
        title.setBounds(220, 25, 400, 30);
        frame.add(title);

        seatPanel = new JPanel(new GridLayout(5, 4, 10, 10)); 
        seatPanel.setBounds(100, 80, 300, 300);
        seatPanel.setBackground(new Color(130, 182, 234));
        frame.add(seatPanel);

        infoPanel = new JPanel();
        infoPanel.setBounds(450, 80, 300, 300);
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(new Color(130, 182, 234));
        frame.add(infoPanel);

        fetchSeatData();

        frame.setVisible(true);
    }

    private void fetchSeatData() {
        try {
            URL url = new URL("http://localhost/webServiceJSON/BookingSeatRequest.php?tripID=" + tripID);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) response.append(line);
            in.close();

            JSONArray seats = new JSONArray(response.toString());

            for (int i = 0; i < seats.length(); i++) {
                JSONObject seat = seats.getJSONObject(i);
                String seatNumber = seat.getString("seatNumber");
                String status = seat.getString("status");

                JButton seatBtn = new JButton(seatNumber);
                seatBtn.setBackground(status.equals("booked") ? Color.GRAY : Color.WHITE);
                seatBtn.setEnabled(!status.equals("booked"));

                seatBtn.addActionListener(e -> {
                    if (selectedSeatIDs.contains(seatNumber)) {
                        selectedSeatIDs.remove(seatNumber);
                        seatBtn.setBackground(Color.WHITE);
                    } else {
                        selectedSeatIDs.add(seatNumber);
                        seatBtn.setBackground(Color.ORANGE);
                    }
                    updateInfoPanel();
                });

                seatPanel.add(seatBtn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateInfoPanel() {
        infoPanel.removeAll();

        if (!selectedSeatIDs.isEmpty()) {
            JLabel selectedLabel = new JLabel("Selected Seats: " + String.join(", ", selectedSeatIDs));
            JLabel priceLabel = new JLabel("Total Price: RM " + (selectedSeatIDs.size() * seatPrice));
            JButton confirmBtn = new JButton("Confirm & Proceed to Payment");
            confirmBtn.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 16));

            confirmBtn.addActionListener(e -> {
                new CustomerPaymentGUI(userID, tripID, selectedSeatIDs, seatPrice * selectedSeatIDs.size(), origin, destination, departDate, arrivalDate, plateNo);
                frame.dispose();
            });

            infoPanel.add(selectedLabel);
            infoPanel.add(priceLabel);
            infoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            infoPanel.add(confirmBtn);
        }

        infoPanel.revalidate();
        infoPanel.repaint();
    }
}