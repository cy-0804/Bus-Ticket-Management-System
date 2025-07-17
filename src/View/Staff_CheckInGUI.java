package View;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;

import java.io.InputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.json.JSONObject;

import controller.checkInController;
import okhttp3.Credentials;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Staff_CheckInGUI {

	private JFrame frame;
	private JSONObject checkInData = null;


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
	                JSONObject response = checkInController.sendCheckIn(bookingId);

	                if (response.getString("status").equals("success")) {
	                	checkInData = response.getJSONObject("data");
	                	JSONObject data = checkInData;

	                    String tripID = data.getString("tripID");
	                    String name = data.getString("passengerName");
	                    String departStation = data.getString("from");
	                    String arrivalStation = data.getString("to");
	                    String departTime = data.getString("departureTime");
	                    String arrivalTime = data.getString("arrivalTime");
	                    String busPlate = data.getString("busPlate");
	                    String seatNo = data.getString("seatNo");

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
	                    textArea.setText(""); // clear any error message

	                } else {
	                    // Server responded but booking not found or check-in failed
	                    boardingPassArea.setText("");
	                    textArea.setText("Error: " + response.getString("message"));
	                }

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
	                JSONObject response = checkInController.sendCheckIn(bookingId);
	                if (response.getString("status").equals("success")) {
	                    textArea.setText("Check-in successfully.");
	                    btnPrint.setVisible(true); // enable printing

	                    JSONObject data = response.getJSONObject("data");
	                    String info = 
	                            "         ========= Check-in Info =========\n" +
	                            String.format("\tTrip ID: %s\n", data.getString("tripID")) +
	                            String.format("\tPassenger Name: %s\n", data.getString("passengerName")) +
	                            String.format("\tFrom: %s\n", data.getString("from")) +
	                            String.format("\tTo: %s\n", data.getString("to")) +
	                            String.format("\tDeparture: %s\n", data.getString("departureTime")) +
	                            String.format("\tArrival: %s\n", data.getString("arrivalTime")) +
	                            String.format("\tBus Plate No: %s\n", data.getString("busPlate")) +
	                            String.format("\tSeat No: %s\n", data.getString("seatNo")) +
	                            "        ==============================";
	                    boardingPassArea.setText(info);

	                } else {
	                    textArea.setText("Check-in failed: " + response.getString("message"));
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
	            new Thread(() -> {
	                try {
	                    OkHttpClient client = new OkHttpClient();

	                    String apiKey = "api_test_sfzZfbZbgXzKJajhk6";
	                    String apiSecret = "tZZsnM633eSbYn67jD7gDZj7bkcKae977LsAJzZnbt";
	                    String templateId = "tpl_LecfLcE5gn5hTrXHm9";

	                    MediaType mediaType = MediaType.parse("application/json");
	                    if (checkInData == null) {
	                        JOptionPane.showMessageDialog(null, "No boarding data to print.");
	                        return;
	                    }

	                    String passenger = checkInData.optString("passengerName", "N/A");
	                    String departure = checkInData.optString("departureTime", "N/A");
	                    String from = checkInData.optString("from", "N/A");
	                    String to = checkInData.optString("to", "N/A");
	                    String seat = checkInData.optString("seatNo", "N/A");
	                    String bus = checkInData.optString("busPlate", "N/A");
	                    String ticket = checkInData.optString("tripID", "N/A");

	                    String json = "{"
	                        + "\"data\": {"
	                        + "\"Passenger\": \"" + passenger + "\","
	                        + "\"Departure\": \"" + departure + "\","
	                        + "\"From\": \"" + from + "\","
	                        + "\"To\": \"" + to + "\","
	                        + "\"Seat\": \"" + seat + "\","
	                        + "\"Bus\": \"" + bus + "\","
	                        + "\"Ticket\": \"" + ticket + "\""
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

	                    JSONObject jsonObject = new JSONObject(responseBody);
	                    if (!jsonObject.has("submission")) {
	                        JOptionPane.showMessageDialog(null, "Error: 'submission' not found in response.");
	                        return;
	                    }

	                    JSONObject submission = jsonObject.getJSONObject("submission");
	                    String submissionId = submission.getString("id");

	                    String state = "";
	                    String pdfUrl = null;

	                    // Polling loop
	                    for (int i = 0; i < 10; i++) {
	                        Request pollRequest = new Request.Builder()
	                                .url("https://api.docspring.com/api/v1/submissions/" + submissionId)
	                                .get()
	                                .addHeader("Authorization", Credentials.basic(apiKey, apiSecret))
	                                .build();

	                        Response pollResponse = client.newCall(pollRequest).execute();
	                        String pollBody = pollResponse.body().string();

	                        try {
	                            JSONObject pollSubmission = new JSONObject(pollBody);

	                            state = pollSubmission.getString("state");

	                            if (state.equals("success") || state.equals("processed")) {
	                                pdfUrl = pollSubmission.optString("download_url");
	                                break;
	                            } else if (state.equals("failed")) {
	                                JOptionPane.showMessageDialog(null, "PDF generation failed.");
	                                return;
	                            }

	                        } catch (Exception parseEx) {
	                            parseEx.printStackTrace();
	                            JOptionPane.showMessageDialog(null, "Failed to parse poll response:\n" + pollBody);
	                            return;
	                        }

	                        Thread.sleep(1000);
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

	                } catch (Exception ex) {
	                    ex.printStackTrace();
	                    JOptionPane.showMessageDialog(null, "Error during submission or processing.");
	                }
	            }).start();
	        }
	    });



	}

}
