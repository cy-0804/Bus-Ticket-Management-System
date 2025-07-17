package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import org.json.*;

public class CustomerPaymentGUI {
    private int tripID, userID;
    private String origin, destination, departDate, arrivalDate, plateNo;
    private Set<String> seatIDs;
    private double totalPrice;
    private JFrame frame;
    private JPanel formPanel;
    private List<PassengerForm> passengerForms = new ArrayList<>();
    private JComboBox<String> paymentMethodBox;

    public CustomerPaymentGUI(int userID, int tripID, Set<String> seatIDs, double totalPrice, String origin,
    							String destination, String departDate, String arrivalDate, String plateNo) {
        this.userID = userID;
    	this.tripID = tripID;
        this.seatIDs = seatIDs;
        this.totalPrice = totalPrice;
        this.origin = origin;
        this.destination = destination;
        this.departDate = departDate;
        this.arrivalDate = arrivalDate;
        this.plateNo = plateNo;

        frame = new JFrame("Payment");
        frame.setSize(600, 700);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(new Color(130, 182, 234));
        frame.setContentPane(contentPane);

        JLabel title = new JLabel("CONFIRM BOOKING & PAYMENT", JLabel.CENTER);
        title.setForeground(new Color(0, 64, 128));
        title.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 24));
        title.setOpaque(true);
        title.setBackground(new Color(130, 182, 234));
        contentPane.add(title, BorderLayout.NORTH);

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(new Color(130, 182, 234));
        container.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // Booking summary
        JPanel bookingInfoPanel = new JPanel(new GridLayout(7, 1, 5, 5));
        bookingInfoPanel.setBackground(new Color(130, 182, 234));
        bookingInfoPanel.add(new JLabel("Origin: " + origin));
        bookingInfoPanel.add(new JLabel("Destination: " + destination));
        bookingInfoPanel.add(new JLabel("Depart Date: " + departDate));
        bookingInfoPanel.add(new JLabel("Arrival Date: " + arrivalDate));
        bookingInfoPanel.add(new JLabel("Bus Plate No: " + plateNo));
        bookingInfoPanel.add(new JLabel("Selected Seats: " + String.join(", ", seatIDs)));
        bookingInfoPanel.add(new JLabel("Total Price: RM " + String.format("%.2f", totalPrice)));
        container.add(bookingInfoPanel);
        container.add(Box.createVerticalStrut(15));

        // Passenger forms
        for (String seatID : seatIDs) {
            PassengerForm pf = new PassengerForm(seatID);
            passengerForms.add(pf);
            container.add(pf.panel);
            container.add(Box.createVerticalStrut(10));
        }

        // Payment method selection
        JPanel paymentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        paymentPanel.setBackground(new Color(130, 182, 234));
        paymentPanel.add(new JLabel("Payment Method: "));
        paymentMethodBox = new JComboBox<>(new String[]{"-- Select Method --", "card", "bank"});
        paymentPanel.add(paymentMethodBox);
        container.add(paymentPanel);

        // Confirm button
        JButton payButton = new JButton("Pay & Confirm Booking");
        payButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        payButton.setFocusPainted(false);
        payButton.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 16));
        payButton.addActionListener(e -> sendBookingRequest());
        container.add(Box.createVerticalStrut(10));
        container.add(payButton);

        // Scroll pane for form container
        JScrollPane scrollPane = new JScrollPane(container);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(new Color(130, 182, 234));
        contentPane.add(scrollPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private void sendBookingRequest() {
        try {
            if (paymentMethodBox.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(frame, "Please select a payment method.");
                return;
            }

            JSONArray seatArray = new JSONArray();
            JSONArray passengerArray = new JSONArray();

            for (PassengerForm pf : passengerForms) {
                if (!pf.isFilled()) {
                    JOptionPane.showMessageDialog(frame, "Please fill in all fields for seat " + pf.seatID);
                    return;
                }

                seatArray.put(pf.seatID);

                JSONObject p = new JSONObject();
                p.put("name", pf.nameField.getText());
                p.put("gender", pf.genderBox.getSelectedItem().toString());
                p.put("telNo", pf.telField.getText());
                p.put("age", Integer.parseInt(pf.ageField.getText()));
                passengerArray.put(p);
            }

            JSONObject bookingData = new JSONObject();
            bookingData.put("tripID", tripID);
            bookingData.put("seatIDs", seatArray);
            bookingData.put("passengers", passengerArray);
            bookingData.put("paymentMethod", paymentMethodBox.getSelectedItem().toString());
            bookingData.put("totalPrice", totalPrice);
            bookingData.put("bookedBy", userID);

            URL url = new URL("http://localhost/webServiceJSON/BookingRequest.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");

            OutputStream os = conn.getOutputStream();
            os.write(bookingData.toString().getBytes());
            os.flush();
            os.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String response = in.readLine();
            in.close();

            if (response != null && response.toLowerCase().contains("success")) {
                JOptionPane.showMessageDialog(frame, "Booking successful!");
                frame.dispose();
            } else {
                JOptionPane.showMessageDialog(frame, "Booking failed: " + response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error during booking request.");
        }
    }

    // Inner class for each passenger form
    private static class PassengerForm {
        JPanel panel;
        String seatID;
        JTextField nameField, telField, ageField;
        JComboBox<String> genderBox;

        PassengerForm(String seatID) {
            this.seatID = seatID;
            panel = new JPanel(new GridLayout(2, 4, 10, 5));
            panel.setBorder(BorderFactory.createTitledBorder("Passenger for Seat " + seatID));
            panel.setBackground(Color.WHITE);

            nameField = new JTextField();
            telField = new JTextField();
            ageField = new JTextField();
            genderBox = new JComboBox<>(new String[]{"Male", "Female"});

            panel.add(new JLabel("Name:"));
            panel.add(nameField);
            panel.add(new JLabel("Gender:"));
            panel.add(genderBox);
            panel.add(new JLabel("Tel No:"));
            panel.add(telField);
            panel.add(new JLabel("Age:"));
            panel.add(ageField);
        }

        boolean isFilled() {
            return !nameField.getText().trim().isEmpty()
                    && !telField.getText().trim().isEmpty()
                    && !ageField.getText().trim().isEmpty();
        }
    }
}
