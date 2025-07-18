package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Desktop;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.itextpdf.layout.font.FontInfo;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;

public class CustomerViewTicketGUI {

	private JFrame frame;
	private JComboBox<String> ticketDropdown;
    private JButton viewTicketButton;
    private Map<String, TicketInfo> ticketMap = new HashMap<>();
    private JButton btnBack;
    private int userID;

	/**
	 * Create the application.
	 * @wbp.parser.entryPoint
	 */
    public CustomerViewTicketGUI(int userID) {
    	this.userID = userID;  
        initialize();          
        fetchTicketList(userID);
    }
	/**
	 * Initialize the contents of the frame.
	 * @wbp.parser.entryPoint
	 */
	private void initialize() {
		frame = new JFrame("View Ticket");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.getContentPane().setBackground(new Color(130, 182, 234));

        JLabel lblTitle = new JLabel("Select Ticket to View");
        lblTitle.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 22));
        lblTitle.setBounds(140, 20, 250, 30);
        frame.getContentPane().add(lblTitle);

        ticketDropdown = new JComboBox<>();
        for (String key : ticketMap.keySet()) {
            ticketDropdown.addItem(key);
        }
        ticketDropdown.setBounds(50, 80, 400, 30);
        frame.getContentPane().add(ticketDropdown);

        viewTicketButton = new JButton("Generate & View Ticket");
        viewTicketButton.setFont(new Font("Tw Cen MT Condensed", Font.PLAIN, 20));
        viewTicketButton.setBounds(150, 150, 200, 30);
        frame.getContentPane().add(viewTicketButton);
        
        btnBack = new JButton("Back");
        btnBack.setFont(new Font("Tw Cen MT Condensed", Font.PLAIN, 20));
        btnBack.setBounds(379, 212, 85, 30);
        frame.getContentPane().add(btnBack);
        
        btnBack.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            frame.dispose(); 
	            new CustomerDashboardGUI(userID); 
	        }
	    });

        viewTicketButton.addActionListener(e -> {
            String selected = (String) ticketDropdown.getSelectedItem();
            if (selected != null && ticketMap.containsKey(selected)) {
                TicketInfo t = ticketMap.get(selected);
                // PDF Generation
                String filePath = System.getProperty("user.home") + "/Downloads/Ticket-" + t.bookingID + ".pdf";
                PDFTicketGenerator.generate(filePath, t.origin, t.destination, t.departDate,
                        t.arrivalDate, t.plateNo, String.format("%.2f", t.totalPrice), Collections.emptyList()); // Pass passengerForms if available

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

        frame.setVisible(true);
	}
	
	private void fetchTicketList(int userID) {
        try {
        	System.out.println("DEBUG: userID = " + userID);

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
                ticketMap.clear(); // Clear old data
                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject obj = dataArray.getJSONObject(i);
                    System.out.println(obj.toString()); // ← Debug print
                    String key = String.format("Trip %s | Seat %s | %s → %s | %s to %s",
                            obj.getString("tripID"),
                            obj.getString("seatNumber"),
                            obj.getString("origin"),
                            obj.getString("destination"),
                            obj.getString("departureDate"),
                            obj.getString("arrivalDate"));
                    ticketMap.put(key, new TicketInfo(obj));
                }

                ticketDropdown.removeAllItems(); 
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
				this.tripID = obj.getString("tripID");
	            this.seatID = obj.getString("seatID");
	            this.seatNumber = obj.getString("seatNumber");
	            this.origin = obj.getString("origin");
	            this.destination = obj.getString("destination");
	            this.departDate = obj.getString("departureDate");   
	            this.arrivalDate = obj.getString("arrivalDate");
	            this.plateNo = obj.getString("plateNo");
	            this.totalPrice = obj.getDouble("totalPrice");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
        }
    }
}
