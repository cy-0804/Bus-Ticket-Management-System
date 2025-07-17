package View;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class StaffPaymentGUI {

    private JFrame frame;
    private JTextField nameField, phoneField, ageField, totalAmountField;
    private JComboBox<String> genderBox, paymentMethodBox;
    private JList<Seat> seatList; // Use a custom Seat object for list items
    private DefaultListModel<Seat> seatListModel; // Model for the JList

    private int userID;
    private int tripID;
    private double totalPrice;

    // Inner class to represent a Seat for the JList
    // This allows us to store both seatID and seatNumber but display only seatNumber
    private static class Seat {
        int seatID;
        String seatNumber;

        public Seat(int seatID, String seatNumber) {
            this.seatID = seatID;
            this.seatNumber = seatNumber;
        }

        public int getSeatID() {
            return seatID;
        }

        public String getSeatNumber() {
            return seatNumber;
        }

        @Override
        public String toString() {
            // This is what will be displayed in the JList
            return "Seat " + seatNumber;
        }
    }

    public StaffPaymentGUI(int userID, int tripID, double totalPrice) {
        this.userID = userID;
        this.tripID = tripID;
        this.totalPrice = totalPrice;

        frame = new JFrame("Payment");
        frame.setSize(600, 650); // Increased size to accommodate seat list
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPane.setBackground(new Color(240, 248, 255));
        frame.setContentPane(contentPane);

        JLabel titleLabel = new JLabel("Customer Information and Payment");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(new Color(50, 50, 120));

        JLabel nameLabel = new JLabel("Customer Name:");
        nameField = new JTextField(20);

        JLabel genderLabel = new JLabel("Gender:");
        genderBox = new JComboBox<>(new String[]{"male", "female"}); // Match DB enum

        JLabel phoneLabel = new JLabel("Phone No:");
        phoneField = new JTextField(15);

        JLabel ageLabel = new JLabel("Age:");
        ageField = new JTextField(5);

        JLabel seatsLabel = new JLabel("Available Seats:");
        seatListModel = new DefaultListModel<>();
        seatList = new JList<>(seatListModel);
        seatList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Or MULTIPLE_INTERVAL_SELECTION for multiple seats
        seatList.setVisibleRowCount(5);
        JScrollPane seatScrollPane = new JScrollPane(seatList); // Add scroll pane for the list

        JLabel totalLabel = new JLabel("Total Amount (RM):");
        totalAmountField = new JTextField(String.format("%.2f", totalPrice));
        totalAmountField.setEditable(false);
        totalAmountField.setBackground(new Color(220, 220, 220));

        JLabel paymentLabel = new JLabel("Payment Method:");
        paymentMethodBox = new JComboBox<>(new String[]{"cash", "card", "online"}); // Match DB enum

        JButton submitButton = new JButton("Confirm Payment");
        submitButton.setBackground(new Color(70, 130, 180));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);

        // Fetch available seats when the GUI loads
        fetchAvailableSeats();

        submitButton.addActionListener(e -> {
            try {
                String name = nameField.getText().trim();
                String gender = (String) genderBox.getSelectedItem();
                String phone = phoneField.getText().trim();
                String ageStr = ageField.getText().trim();
                String method = (String) paymentMethodBox.getSelectedItem();

                // Get selected seat(s)
                List<Seat> selectedSeats = seatList.getSelectedValuesList();
                if (selectedSeats.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please select at least one seat.");
                    return;
                }

                if (name.isEmpty() || phone.isEmpty() || ageStr.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill in all customer fields.");
                    return;
                }

                int age = Integer.parseInt(ageStr);

                // Build JSON for booking, including selected seats
                JSONObject json = new JSONObject();
                json.put("name", name);
                json.put("gender", gender);
                json.put("phone", phone);
                json.put("age", age);
                json.put("paymentMethod", method);
                json.put("totalAmount", totalPrice);
                json.put("tripID", tripID);
                json.put("userID", userID); // Staff's userID

                // Convert list of Seat objects to JSON Array of seat details
                JSONArray seatsArray = new JSONArray();
                for (Seat seat : selectedSeats) {
                    JSONObject seatObj = new JSONObject();
                    seatObj.put("seatID", seat.getSeatID());
                    seatObj.put("seatNumber", seat.getSeatNumber());
                    seatsArray.put(seatObj);
                }
                json.put("selectedSeats", seatsArray);

                // Send POST request to confirm_payment.php
                URL url = new URL("http://localhost/webServiceJSON/confirm_payment.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = json.toString().getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                StringBuilder response = new StringBuilder();
                try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                }

                JSONObject jsonResponse = new JSONObject(response.toString());
                if (jsonResponse.getString("status").equals("success")) {
                    String bookingID = jsonResponse.getString("bookingID");
                    int passengerID = jsonResponse.getInt("passengerID");
                    int paymentID = jsonResponse.getInt("paymentID");

                    // Optionally, you could pass the selected seats back to the calling frame
                    // if further actions are needed with the booked seats.
                    JOptionPane.showMessageDialog(frame,
                            "Booking Confirmed!\nBooking ID: " + bookingID +
                            "\nPassenger ID: " + passengerID +
                            "\nPayment ID: " + paymentID +
                            "\nSeats Booked: " + getSelectedSeatNumbers(selectedSeats)); // Helper method
                    frame.dispose(); // Close this payment window
                } else {
                    JOptionPane.showMessageDialog(frame, "Error: " + jsonResponse.getString("message"));
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Failed to confirm booking: " + ex.getMessage());
            }
        });

        // --- Layout with GroupLayout ---
        GroupLayout layout = new GroupLayout(contentPane);
        contentPane.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(titleLabel)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(nameLabel)
                                        .addComponent(genderLabel)
                                        .addComponent(phoneLabel)
                                        .addComponent(ageLabel)
                                        .addComponent(seatsLabel) // New Label for seats
                                        .addComponent(totalLabel)
                                        .addComponent(paymentLabel))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED) // Add some space
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(nameField, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE) // Make fields stretch
                                        .addComponent(genderBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(phoneField, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                                        .addComponent(ageField, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(seatScrollPane, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE) // Seat list with scroll
                                        .addComponent(totalAmountField, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                                        .addComponent(paymentMethodBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(submitButton, GroupLayout.Alignment.CENTER, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(titleLabel)
                        .addGap(20) // Add some vertical space
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(nameLabel)
                                .addComponent(nameField))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(genderLabel)
                                .addComponent(genderBox))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(phoneLabel)
                                .addComponent(phoneField))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(ageLabel)
                                .addComponent(ageField))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(seatsLabel)
                                .addComponent(seatScrollPane, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)) // Fixed height for scroll pane
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(totalLabel)
                                .addComponent(totalAmountField))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(paymentLabel)
                                .addComponent(paymentMethodBox))
                        .addGap(30)
                        .addComponent(submitButton)
        );


        frame.setVisible(true);
    }

    // Helper method to fetch available seats from PHP
    private void fetchAvailableSeats() {
        new SwingWorker<List<Seat>, Void>() {
            @Override
            protected List<Seat> doInBackground() throws Exception {
                List<Seat> seats = new ArrayList<>();
                try {
                    URL url = new URL("http://localhost/webServiceJSON/get_available_seat.php");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setDoOutput(true);

                    JSONObject requestBody = new JSONObject();
                    requestBody.put("tripID", tripID);

                    try (OutputStream os = conn.getOutputStream()) {
                        byte[] input = requestBody.toString().getBytes("utf-8");
                        os.write(input, 0, input.length);
                    }

                    StringBuilder response = new StringBuilder();
                    try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                        String responseLine;
                        while ((responseLine = br.readLine()) != null) {
                            response.append(responseLine.trim());
                        }
                    }

                    JSONObject jsonResponse = new JSONObject(response.toString());
                    if (jsonResponse.getString("status").equals("success")) {
                        JSONArray seatsArray = jsonResponse.getJSONArray("seats");
                        for (int i = 0; i < seatsArray.length(); i++) {
                            JSONObject seatObj = seatsArray.getJSONObject(i);
                            int seatID = seatObj.getInt("seatID");
                            String seatNumber = seatObj.getString("seatNumber");
                            seats.add(new Seat(seatID, seatNumber));
                        }
                    } else {
                        throw new Exception("Error fetching seats: " + jsonResponse.getString("message"));
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    // Log or show error, but don't re-throw to avoid crashing EDT
                    JOptionPane.showMessageDialog(frame, "Failed to load available seats: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
                return seats;
            }

            @Override
            protected void done() {
                try {
                    List<Seat> seats = get();
                    seatListModel.clear(); // Clear existing items
                    for (Seat seat : seats) {
                        seatListModel.addElement(seat); // Add fetched seats
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    // Error already handled in doInBackground's catch block, but could re-log here if needed.
                }
            }
        }.execute(); // Execute the SwingWorker
    }

    // Helper method to get selected seat numbers for display in JOptionPane
    private String getSelectedSeatNumbers(List<Seat> seats) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < seats.size(); i++) {
            sb.append(seats.get(i).getSeatNumber());
            if (i < seats.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    // You might call this GUI from another part of your application, for example:
    /*
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Example usage: userID 3 (staff), tripID 1, total amount 45.00
            new StaffPaymentGUI(3, 1, 45.00);
        });
    }
    */
}