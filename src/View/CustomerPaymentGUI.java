package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.json.*;

public class CustomerPaymentGUI {
    private int tripID;
    private Set<String> seatIDs;
    private double totalPrice;
    private JFrame frame;
    private JPanel formPanel;
    private List<PassengerForm> passengerForms = new ArrayList<>();

    public CustomerPaymentGUI(int tripID, Set<String> seatIDs, double totalPrice) {
        this.tripID = tripID;
        this.seatIDs = seatIDs;
        this.totalPrice = totalPrice;

        frame = new JFrame("Payment");
        frame.setSize(800, 600);
        frame.getContentPane().setBackground(new Color(130, 182, 234));
        frame.setLayout(new BorderLayout());

        JLabel title = new JLabel("CONFIRM BOOKING & PAYMENT", JLabel.CENTER);
        title.setForeground(new Color(0, 64, 128));
        title.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 24));
        frame.add(title, BorderLayout.NORTH);

        formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(formPanel);
        frame.add(scrollPane, BorderLayout.CENTER);

        for (String seatID : seatIDs) {
            PassengerForm pf = new PassengerForm(seatID);
            passengerForms.add(pf);
            formPanel.add(pf.panel);
        }

        JButton payButton = new JButton("Pay & Confirm Booking");
        payButton.addActionListener(e -> sendBookingRequest());
        frame.add(payButton, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void sendBookingRequest() {
        try {
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
            bookingData.put("paymentMethod", "Online Banking"); // or user-selected
            bookingData.put("totalPrice", totalPrice);

            // Send POST to PHP backend
            URL url = new URL("http://localhost/webServiceJSON/BookingRequest.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");

            OutputStream os = conn.getOutputStream();
            os.write(bookingData.toString().getBytes());
            os.flush();
            os.close();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
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
            panel = new JPanel(new GridLayout(2, 5));
            panel.setBorder(BorderFactory.createTitledBorder("Seat " + seatID));

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