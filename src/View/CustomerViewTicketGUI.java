package View;

import java.awt.EventQueue;
import javax.swing.*;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.PopupMenuEvent;

import org.json.*;

public class CustomerViewTicketGUI {

    private JFrame frame;
    private JComboBox<String> ticketDropdown;
    private JButton viewTicketButton;
    private Map<String, TicketInfo> ticketMap = new HashMap<>();
    private JButton btnBack;
    private int userID;

    // Labels to show booking details
    private JLabel lblBookingID, lblOrigin, lblDestination, lblDepart, lblArrival;

    public CustomerViewTicketGUI(int userID) {
        this.userID = userID;
        initialize();
        fetchTicketList(userID);
    }

    private void initialize() {
        frame = new JFrame("View Ticket");
        frame.setSize(650, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.getContentPane().setBackground(new Color(130, 182, 234));

        JLabel lblTitle = new JLabel("Select Ticket to View");
        lblTitle.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 22));
        lblTitle.setBounds(220, 20, 250, 30);
        frame.getContentPane().add(lblTitle);

        ticketDropdown = new JComboBox<>();
        ticketDropdown.setBounds(50, 80, 550, 30);
        ticketDropdown.addItem("Select your ticket...");
        ticketDropdown.setSelectedIndex(0);
        ticketDropdown.addPopupMenuListener(new PopupMenuListener() {
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                if (ticketDropdown.getItemCount() <= 1) fetchTicketList(userID);
            }
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {}
            public void popupMenuCanceled(PopupMenuEvent e) {}
        });
        frame.getContentPane().add(ticketDropdown);

        viewTicketButton = new JButton("Generate & View Ticket");
        viewTicketButton.setFont(new Font("Tw Cen MT Condensed", Font.PLAIN, 20));
        viewTicketButton.setBounds(200, 150, 200, 30);
        frame.getContentPane().add(viewTicketButton);

        viewTicketButton.addActionListener(e -> {
            String selected = (String) ticketDropdown.getSelectedItem();
            if (selected != null && ticketMap.containsKey(selected)) {
                TicketInfo t = ticketMap.get(selected);
                String filePath = System.getProperty("user.home") + "/Downloads/Ticket-" + t.bookingID + ".pdf";
                PDFTicketGenerator.generate(filePath, t.origin, t.destination, t.departDate,
                        t.arrivalDate, t.plateNo, String.format("%.2f", t.totalPrice), Collections.emptyList());
                try {
                    if (Desktop.isDesktopSupported()) {
                        Desktop.getDesktop().open(new File(filePath));
                    } else {
                        JOptionPane.showMessageDialog(frame, "PDF saved at: " + filePath);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Failed to open PDF.");
                }
            }
        });

        btnBack = new JButton("Back");
        btnBack.setFont(new Font("Tw Cen MT Condensed", Font.PLAIN, 20));
        btnBack.setBounds(500, 200, 85, 30);
        frame.getContentPane().add(btnBack);
        btnBack.addActionListener(e -> {
            frame.dispose();
            new CustomerDashboardGUI(userID);
        });

        frame.setVisible(true);
    }

    private void fetchTicketList(int userID) {
        try {
            URL url = new URL("http://localhost/webServiceJSON/TicketResponse.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");

            JSONObject json = new JSONObject();
            json.put("userID", userID);
            OutputStream os = conn.getOutputStream();
            os.write(json.toString().getBytes());
            os.flush();
            os.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder responseStr = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                responseStr.append(line);
            }
            in.close();

            JSONObject responseJson = new JSONObject(responseStr.toString());
            if (responseJson.getString("status").equals("success")) {
                JSONArray dataArray = responseJson.getJSONArray("data");
                ticketMap.clear();
                
                ticketDropdown.removeAllItems(); 
                ticketDropdown.addItem("Select your ticket..."); // optional placeholder

                Set<String> uniqueBookingIDs = new HashSet<>();
                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject obj = dataArray.getJSONObject(i);
                    String bookingID = obj.getString("bookingID");

                    if (!uniqueBookingIDs.contains(bookingID)) {
                        uniqueBookingIDs.add(bookingID);

                        String label = String.format("Booking #%s | %s → %s | %s",
                                bookingID,
                                obj.getString("origin"),
                                obj.getString("destination"),
                                obj.getString("departureDate"));

                        ticketMap.put(label, new TicketInfo(obj));
                        ticketDropdown.addItem(label);
                    }
                }

                ticketDropdown.removeAllItems();
                ticketDropdown.addItem("Select your ticket...");
                for (String key : ticketMap.keySet()) {
                    ticketDropdown.addItem(key);
                }
            } else {
                JOptionPane.showMessageDialog(null, "No tickets found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching tickets.");
        }
    }

    static class TicketInfo {
        String bookingID, tripID, seatID, seatNumber, origin, destination, departDate, arrivalDate, plateNo;
        double totalPrice;

        public TicketInfo(JSONObject obj) {
            try {
                this.bookingID = obj.getString("bookingID");
                this.tripID = String.valueOf(obj.getInt("tripID"));
                this.seatID = String.valueOf(obj.getInt("seatID"));
                this.seatNumber = obj.getString("seatNumber");
                this.origin = obj.getString("origin");
                this.destination = obj.getString("destination");
                this.departDate = obj.getString("departureDate");
                this.arrivalDate = obj.getString("arrivalDate");
                this.plateNo = obj.getString("plateNo");
                this.totalPrice = obj.getDouble("totalPrice");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
