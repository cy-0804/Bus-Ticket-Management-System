package View;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.*;
import model.Booking;
import model.Seat;
import model.Passenger;
import model.BookingSeats;

public class CustomerPaymentGUI {
    private int tripID, userID;
    private String origin, destination, departDate, arrivalDate, plateNo;
    private List<Integer> seatIDs;
    private double totalPrice;
    private JFrame frame;
    private JPanel formPanel;
    private List<PassengerForm> passengerForms = new ArrayList<>();
    private JComboBox<String> paymentMethodBox;
    private Map<Integer, String> seatIdToNumberMap = new HashMap<>();

    public CustomerPaymentGUI(int userID, int tripID, List<Integer> seatIDs, double totalPrice, String origin,
    							String destination, String departDate, String arrivalDate, String plateNo, Map<Integer, String> seatIdToNumberMap) {
        this.userID = userID;
    	this.tripID = tripID;
        this.seatIDs = seatIDs;
        this.totalPrice = totalPrice;
        this.origin = origin;
        this.destination = destination;
        this.departDate = departDate;
        this.arrivalDate = arrivalDate;
        this.plateNo = plateNo;
        this.seatIdToNumberMap = seatIdToNumberMap;

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

        JPanel bookingInfoPanel = new JPanel(new GridLayout(7, 1, 5, 5));
        bookingInfoPanel.setBackground(new Color(130, 182, 234));
        bookingInfoPanel.add(new JLabel("Origin: " + origin));
        bookingInfoPanel.add(new JLabel("Destination: " + destination));
        bookingInfoPanel.add(new JLabel("Depart Date: " + departDate));
        bookingInfoPanel.add(new JLabel("Arrival Date: " + arrivalDate));
        bookingInfoPanel.add(new JLabel("Bus Plate No: " + plateNo));
        String seatStr = seatIDs.stream()
        	    .map(id -> seatIdToNumberMap.getOrDefault(id, "Seat " + id))
        	    .collect(Collectors.joining(", "));
        	bookingInfoPanel.add(new JLabel("Selected Seats: " + seatStr));
        bookingInfoPanel.add(new JLabel("Total Price: RM " + String.format("%.2f", totalPrice)));
        container.add(bookingInfoPanel);
        container.add(Box.createVerticalStrut(15));

        for (Integer seatID : seatIDs) {
        	PassengerForm pf = new PassengerForm(String.valueOf(seatID), seatIdToNumberMap);
            passengerForms.add(pf);
            container.add(pf.panel);
            container.add(Box.createVerticalStrut(10));
        }

        JPanel paymentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        paymentPanel.setBackground(new Color(130, 182, 234));
        paymentPanel.add(new JLabel("Payment Method: "));
        paymentMethodBox = new JComboBox<>(new String[]{"-- Select Method --", "card", "online"});
        paymentPanel.add(paymentMethodBox);
        container.add(paymentPanel);

        JButton payButton = new JButton("Pay & Confirm Booking");
        payButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        payButton.setFocusPainted(false);
        payButton.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 16));
        payButton.addActionListener(e -> sendBookingRequest());
        container.add(Box.createVerticalStrut(10));
        container.add(payButton);

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

            JSONArray passengerArray = new JSONArray();

            for (int i = 0; i < passengerForms.size(); i++) {
                PassengerForm pf = passengerForms.get(i);
                if (!pf.isFilled()) {
                    JOptionPane.showMessageDialog(frame, "Please fill in all fields for seat " + pf.seatNum);
                    return;
                }

                JSONObject p = new JSONObject();
                p.put("name", pf.nameField.getText());
                p.put("gender", pf.genderBox.getSelectedItem().toString());
                p.put("telNo", pf.telField.getText());
                p.put("age", Integer.parseInt(pf.ageField.getText()));

                JSONArray seatArray = new JSONArray();
                seatArray.put(Integer.parseInt(pf.seatID));
                p.put("seatIDs", seatArray);

                passengerArray.put(p);
            }

            JSONObject bookingData = new JSONObject();
            bookingData.put("tripID", tripID);
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
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();

            JSONObject jsonResponse = new JSONObject(response.toString());

            if (jsonResponse.getString("status").equalsIgnoreCase("success")) {
                String bookingID = jsonResponse.getString("bookingID");

                JOptionPane.showMessageDialog(frame, "Booking successful! Booking ID: " + bookingID);

                List<BookingSeats> seatsList = fetchBookingSeats(bookingID);

                String userHome = System.getProperty("user.home");
                String filePath = userHome + "/Downloads/Ticket.pdf";
                PDFTicketGenerator.generate(filePath, origin, destination, departDate, arrivalDate,
                                            plateNo, String.format("%.2f", totalPrice), seatsList);

                try {
                    if (Desktop.isDesktopSupported()) {
                        Desktop.getDesktop().open(new File(filePath));
                    } else {
                        JOptionPane.showMessageDialog(frame, "PDF saved at: " + filePath);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Failed to open the PDF file.");
                }

                frame.dispose();
            } else {
                JOptionPane.showMessageDialog(frame, "Booking failed: " + jsonResponse.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error during booking request.");
        }
    }

    public static class PassengerForm {
        JPanel panel;
        String seatID;
        String seatNum;
        JTextField nameField, telField, ageField;
        JComboBox<String> genderBox;

        PassengerForm(String seatID, Map<Integer, String> seatIdToNumberMap) {
            this.seatID = seatID;
            this.seatNum = seatIdToNumberMap.getOrDefault(Integer.parseInt(seatID), "Seat " + seatID);
            panel = new JPanel(new GridLayout(2, 4, 10, 5));
            panel.setBorder(BorderFactory.createTitledBorder("Passenger for Seat " + seatNum));
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
    
    private List<BookingSeats> fetchBookingSeats(String bookingID) {
        List<BookingSeats> seatsList = new ArrayList<>();

        try {
            URL url = new URL("http://localhost/webServiceJSON/BookingResponse.php?bookingID=" + bookingID);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();

            JSONArray arr = new JSONArray(response.toString());
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);

                Passenger passenger = new Passenger();
                passenger.setName(obj.getString("name"));
                passenger.setGender(obj.getString("gender"));
                passenger.setTelNo(obj.getString("telNo"));
                passenger.setAge(obj.getInt("age"));

                Seat seat = new Seat();
                seat.setSeatNumber(obj.getString("seatNumber"));

                BookingSeats bookingSeat = new BookingSeats();
                bookingSeat.setSeat(seat);
                bookingSeat.setPassenger(passenger);
                seatsList.add(bookingSeat);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching booking seat data");
        }

        return seatsList;
    }

}
