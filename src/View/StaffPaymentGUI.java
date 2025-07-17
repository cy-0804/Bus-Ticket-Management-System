package View;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class StaffPaymentGUI {

    private JFrame frame;
    private JTextField nameField, phoneField, ageField, totalAmountField;
    private JComboBox<String> genderBox, paymentMethodBox;

    public StaffPaymentGUI(int userID, int tripID, double totalPrice) {
        frame = new JFrame("Payment");
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPane.setBackground(new Color(240, 248, 255));
        frame.setContentPane(contentPane);

        JLabel titleLabel = new JLabel("Customer Information and Payment");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(new Color(50, 50, 120));

        JLabel nameLabel = new JLabel("Customer Name:");
        nameField = new JTextField(20);

        JLabel genderLabel = new JLabel("Gender:");
        genderBox = new JComboBox<>(new String[]{"Male", "Female", "Other"});

        JLabel phoneLabel = new JLabel("Phone No:");
        phoneField = new JTextField(15);

        JLabel ageLabel = new JLabel("Age:");
        ageField = new JTextField(5);

        JLabel totalLabel = new JLabel("Total Amount (RM):");
        totalAmountField = new JTextField(String.format("%.2f", totalPrice));
        totalAmountField.setEditable(false);
        totalAmountField.setBackground(new Color(220, 220, 220));

        JLabel paymentLabel = new JLabel("Payment Method:");
        paymentMethodBox = new JComboBox<>(new String[]{"Cash", "Card", "Online"});

        JButton submitButton = new JButton("Confirm Payment");
        submitButton.setBackground(new Color(70, 130, 180));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);

        submitButton.addActionListener(e -> {
            try {
                String name = nameField.getText().trim();
                String gender = (String) genderBox.getSelectedItem();
                String phone = phoneField.getText().trim();
                String ageStr = ageField.getText().trim();
                String method = (String) paymentMethodBox.getSelectedItem();

                if (name.isEmpty() || phone.isEmpty() || ageStr.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill in all fields.");
                    return;
                }

                int age = Integer.parseInt(ageStr);

                // Build JSON
                JSONObject json = new JSONObject();
                json.put("name", name);
                json.put("gender", gender);
                json.put("phone", phone);
                json.put("age", age);
                json.put("paymentMethod", method);
                json.put("totalAmount", totalPrice);
                json.put("tripID", tripID);
                json.put("userID", userID);

                // Send POST request
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

                    JOptionPane.showMessageDialog(frame,
                            "Booking Confirmed!\nBooking ID: " + bookingID +
                            "\nPassenger ID: " + passengerID +
                            "\nPayment ID: " + paymentID);
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Error: " + jsonResponse.getString("message"));
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Failed to confirm booking.");
            }
        });

        // Layout with GroupLayout
        GroupLayout layout = new GroupLayout(contentPane);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(titleLabel)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(nameLabel)
                                        .addComponent(genderLabel)
                                        .addComponent(phoneLabel)
                                        .addComponent(ageLabel)
                                        .addComponent(totalLabel)
                                        .addComponent(paymentLabel))
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(submitButton)
                                        .addComponent(nameField, 331, 331, 331)
                                        .addComponent(genderBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(phoneField, 331, 331, 331)
                                        .addComponent(ageField, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(totalAmountField, 331, 331, 331)
                                        .addComponent(paymentMethodBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(titleLabel)
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
                                        .addComponent(totalLabel)
                                        .addComponent(totalAmountField))
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(paymentLabel)
                                        .addComponent(paymentMethodBox))
                                .addGap(30)
                                .addComponent(submitButton))
        );

        contentPane.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        frame.setVisible(true);
    }
}
