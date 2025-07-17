package View;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Credentials;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Staff_CheckInGUI {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Staff_CheckInGUI window = new Staff_CheckInGUI();
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
	public Staff_CheckInGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
	    frame = new JFrame("Check-In Customer");
	    frame.getContentPane().setBackground(new Color(130, 182, 234));
	    frame.setBounds(100, 100, 742, 387);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.getContentPane().setLayout(null);

	    JLabel lblBookingId = new JLabel("Enter Booking ID:");
	    lblBookingId.setBounds(168, 46, 120, 25);
	    frame.getContentPane().add(lblBookingId);

	    JTextField txtBookingId = new JTextField();
	    txtBookingId.setBounds(278, 46, 177, 25);
	    frame.getContentPane().add(txtBookingId);

	    JTextArea boardingPassArea = new JTextArea();
	    boardingPassArea.setBounds(188, 81, 300, 156);
	    boardingPassArea.setEditable(false);
	    frame.getContentPane().add(boardingPassArea);

	    JButton btnCheckin = new JButton("Check-In");
	    btnCheckin.setBounds(278, 247, 120, 30);
	    frame.getContentPane().add(btnCheckin);

	    JButton btnGo = new JButton("Go");
	    btnGo.setBounds(465, 48, 53, 21);
	    frame.getContentPane().add(btnGo);

	    JTextArea textArea = new JTextArea();
	    textArea.setBackground(new Color(130, 182, 234));
	    textArea.setBounds(288, 287, 255, 22);
	    textArea.setEditable(false);
	    frame.getContentPane().add(textArea);

	    JButton btnBack = new JButton("Back");
	    btnBack.setBackground(new Color(0, 255, 255));
	    btnBack.setBounds(25, 10, 70, 21);
	    frame.getContentPane().add(btnBack);

	    JButton btnPrint = new JButton(">>Print Boarding Pass");
	    btnPrint.setBackground(new Color(130, 182, 234));
	    btnPrint.setBounds(261, 319, 166, 21);
	    btnPrint.setVisible(false); // only visible after successful check-in
	    frame.getContentPane().add(btnPrint);

	    frame.setVisible(true);

	    
	    btnBack.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            frame.dispose(); 
	            new StaffDashboardGUI(); 
	        }
	    });

	    btnGo.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            String bookingId = txtBookingId.getText().trim();
	            if (bookingId.isEmpty()) {
	                JOptionPane.showMessageDialog(frame, "Please enter a booking ID.");
	                return;
	            }

	            try {
//	                String name = BookingModel.getBookingCustomerName(bookingId);
//	                String departStation = BookingModel.getDepartStation(bookingId);
//	                String arrivalStation = BookingModel.getArrivalStation(bookingId);
//	                String departTime = BookingModel.getDepartTime(bookingId);
//	                String arrivalTime = BookingModel.getArrivalTime(bookingId);
//	                String busPlate = BookingModel.getBusPlateNo(bookingId);
//	                String seatNo = BookingModel.getSeatNo(bookingId);
//	                String tripID = BookingModel.getTripID(bookingId);
	                
	            	String name = "banana";
	                String departStation = "Malaysia";
	                String arrivalStation = "Singapore";
	                String departTime = "0000";
	                String arrivalTime = "2359";
	                String busPlate = "ABC1234";
	                String seatNo = "A01";
	                String tripID = "B1234";


	                String info =
	                        "         ========= Check-in Info =========\n" +
	                        String.format("\tTrip ID: %s\n", tripID) +
	                        String.format("\tPassenger Name: %s\n", name) +
	                        String.format("\tFrom: %s\n", departStation) +
	                        String.format("\tTo: %s\n", arrivalStation) +
	                        String.format("\tDeparture: %s\n", departTime) +
	                        String.format("\tArrival: %s\n", arrivalTime) +
	                        String.format("\tBus Plate No: %s\n", busPlate) +
	                        String.format("\tSeat No: %s\n", seatNo) +
	                        "        ==============================";

	                boardingPassArea.setText(info);

	            } catch (Exception ex) {
	                ex.printStackTrace();
	                JOptionPane.showMessageDialog(frame, "Error retrieving booking data.");
	            }
	        }
	    });

	    // Check-in: update DB and show status
	    btnCheckin.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            String bookingId = txtBookingId.getText().trim();
	            if (bookingId.isEmpty()) {
	                JOptionPane.showMessageDialog(frame, "Please enter a booking ID.");
	                return;
	            }

	            try {
	            	//boolean success = CheckInRequest.sendCheckIn("bookingId");
	            	Boolean success=true;
	                if (success) {
	                    textArea.setText("Check-in successfully.");
	                    btnPrint.setVisible(true); 

	                } else {
	                	 textArea.setText("Check-in fail.");
	                }
	            } catch (Exception ex) {
	                ex.printStackTrace();
	                JOptionPane.showMessageDialog(frame, "Error during check-in.");
	            }
	        }
	    });

	    // Print Boarding Pass
	    btnPrint.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            try {
	                OkHttpClient client = new OkHttpClient();

	                String apiKey = "api_test_sfzZfbZbgXzKJajhk6";
	                String apiSecret = "tZZsnM633eSbYn67jD7gDZj7bkcKae977LsAJzZnbt";
	                String templateId = "tpl_LecfLcE5gn5hTrXHm9";

	                MediaType mediaType = MediaType.parse("application/json");
	                String json = "{"
	                	    + "\"data\": {"
	                	        + "\"Passenger\": \"Banana\","
	                	        + "\"Departure\": \"0000\","
	                	        + "\"From\": \"Malaysia\","
	                	        + "\"To\": \"Singapore\","
	                	        + "\"Seat\": \"A01\","
	                	        + "\"Bus\": \"ABC1234\","
	                	        + "\"Ticket\": \"B1234\""
	                	    + "},"
	                	    + "\"test\": true"
	                	+ "}";


	                RequestBody body = RequestBody.create(mediaType, json);
	                Request request = new Request.Builder()
	                    .url("https://api.docspring.com/api/v1/templates/" + templateId + "/submissions")
	                    .post(body)
	                    .addHeader("Authorization", Credentials.basic(apiKey, apiSecret))
	                    .addHeader("Content-Type", "application/json")
	                    .build();

	                Response response = client.newCall(request).execute();
	                String responseBody = response.body().string();

	                if (response.isSuccessful()) {
	                    JSONObject jsonObject = new JSONObject(responseBody);
	                    JSONObject submission = jsonObject.getJSONObject("submission");
	                    String submissionId = submission.getString("id");

	                    String state = "";
	                    String pdfUrl = null;

	                    for (int i = 0; i < 10; i++) {
	                        Request pollRequest = new Request.Builder()
	                            .url("https://api.docspring.com/api/v1/submissions/" + submissionId)
	                            .get()
	                            .addHeader("Authorization", Credentials.basic(apiKey, apiSecret))
	                            .build();

	                        Response pollResponse = client.newCall(pollRequest).execute();
	                        String pollBody = pollResponse.body().string();
	                        JSONObject pollJson = new JSONObject(pollBody);
	                        JSONObject pollSubmission = pollJson.getJSONObject("submission");

	                        state = pollSubmission.getString("state");

	                        if (state.equals("success")) {
	                            pdfUrl = pollSubmission.getString("download_url");
	                            break;
	                        } else if (state.equals("failed")) {
	                            JOptionPane.showMessageDialog(null, "Submission failed during processing.");
	                            return;
	                        }

	                        Thread.sleep(1000); // Wait before retry
	                    }

	                    if (pdfUrl != null && !pdfUrl.isEmpty()) {
	                        Request pdfRequest = new Request.Builder().url(pdfUrl).build();
	                        Response pdfResponse = client.newCall(pdfRequest).execute();

	                        if (pdfResponse.isSuccessful()) {
	                            InputStream inputStream = pdfResponse.body().byteStream();
	                            File file = new File("boarding_pass.pdf");
	                            FileOutputStream outputStream = new FileOutputStream(file);

	                            byte[] buffer = new byte[4096];
	                            int bytesRead;
	                            while ((bytesRead = inputStream.read(buffer)) != -1) {
	                                outputStream.write(buffer, 0, bytesRead);
	                            }

	                            outputStream.close();
	                            inputStream.close();

	                            JOptionPane.showMessageDialog(null, "PDF downloaded successfully!");

	                            // Preview the PDF
	                            if (Desktop.isDesktopSupported()) {
	                                Desktop.getDesktop().open(file);
	                            } else {
	                                JOptionPane.showMessageDialog(null, "Preview not supported on this system.");
	                            }

	                        } else {
	                            JOptionPane.showMessageDialog(null, "Failed to download PDF.");
	                        }

	                    } else {
	                        JOptionPane.showMessageDialog(null, "PDF not ready after polling.");
	                    }

	                } else {
	                    JOptionPane.showMessageDialog(null, "Submission Failed:\n" + responseBody);
	                }

	            } catch (Exception ex) {
	                ex.printStackTrace();
	                JOptionPane.showMessageDialog(null, "Error during submission.");
	            }
	        }
	    });

	}

}
