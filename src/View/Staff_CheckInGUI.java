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
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

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
	private int userID;

	public Staff_CheckInGUI(int userID) {
		this.userID = userID;
		initialize();
		frame.setVisible(true); 
	}


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
		btnPrint.setVisible(false);
		frame.getContentPane().add(btnPrint);

		frame.setVisible(true);

		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				new StaffDashboardGUI(userID);
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
					JSONObject response = checkInController.getCheckInInfo(bookingId);

					if (response.getString("status").equals("success")) {
						if (response.has("data")) {
							checkInData = response.getJSONObject("data");
							JSONObject data = checkInData;

							String info =
									"         ========= Check-in Info =========\n" +
									String.format("\tTrip ID: %s\n", data.getInt("tripID")) +
									String.format("\tPassenger Name: %s\n", data.getString("passengerName")) +
									String.format("\tFrom: %s\n", data.getString("from")) +
									String.format("\tTo: %s\n", data.getString("to")) +
									String.format("\tDeparture: %s\n", data.getString("departureTime")) +
									String.format("\tArrival: %s\n", data.getString("arrivalTime")) +
									String.format("\tBus Plate No: %s\n", data.getString("busPlate")) +
									String.format("\tSeat No: %s\n", data.getString("seatNo")) +
									"        ==============================";

							boardingPassArea.setText(info);
							textArea.setText("");
						} else {
							textArea.setText("Success: " + response.getString("message"));
							boardingPassArea.setText("");
						}

					} else {
						boardingPassArea.setText("");
						textArea.setText("Error: " + response.getString("message"));
					}

				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(frame, "Error retrieving booking data.");
				}
			}
		});

		btnCheckin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String bookingId = txtBookingId.getText().trim();
				if (bookingId.isEmpty()) {
					JOptionPane.showMessageDialog(frame, "Please enter a booking ID.");
					return;
				}

				try {
					JSONObject response = checkInController.updateCheckInStatus(bookingId);
					if (response.getString("status").equals("success")) {
						textArea.setText("Check-in successful.");
						btnPrint.setVisible(true);
					} else {
						textArea.setText("Check-in failed: " + response.getString("message"));
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(frame, "Error during check-in.");
				}
			}
		});

		btnPrint.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        if (checkInData == null) {
		            JOptionPane.showMessageDialog(frame, "No boarding data to print.");
		            return;
		        }

		        JDialog loadingDialog = new JDialog(frame, "Generating PDF...", true);
		        JLabel loadingLabel = new JLabel("Generating.......");
		        loadingDialog.add(loadingLabel);
		        loadingDialog.setSize(350, 100);
		        loadingDialog.setLocationRelativeTo(frame);

		        SwingWorker<Void, Void> worker = new SwingWorker<>() {
		            private String pdfUrl = null;
		            private File downloadedFile = null;
		            private String errorMessage = null;

		            @Override
		            protected Void doInBackground() {
		                try {
		                    OkHttpClient client = new OkHttpClient();

		                    String apiKey = "api_test_sfzZfbZbgXzKJajhk6";
		                    String apiSecret = "tZZsnM633eSbYn67jD7gDZj7bkcKae977LsAJzZnbt";
		                    String templateId = "tpl_LecfLcE5gn5hTrXHm9";

		                    MediaType mediaType = MediaType.parse("application/json");

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
		                    JSONObject submission = new JSONObject(response.body().string()).optJSONObject("submission");

		                    if (submission == null) {
		                        errorMessage = "Error: 'submission' not found.";
		                        return null;
		                    }

		                    String submissionId = submission.getString("id");

		                    for (int i = 0; i < 10; i++) {
		                        Request pollRequest = new Request.Builder()
		                                .url("https://api.docspring.com/api/v1/submissions/" + submissionId)
		                                .get()
		                                .addHeader("Authorization", Credentials.basic(apiKey, apiSecret))
		                                .build();

		                        Response pollResponse = client.newCall(pollRequest).execute();
		                        JSONObject pollSubmission = new JSONObject(pollResponse.body().string());
		                        String state = pollSubmission.getString("state");

		                        if (state.equals("success") || state.equals("processed")) {
		                            pdfUrl = pollSubmission.optString("download_url");
		                            break;
		                        } else if (state.equals("failed")) {
		                            errorMessage = "PDF generation failed.";
		                            return null;
		                        }

		                        Thread.sleep(1000);
		                    }

		                    if (pdfUrl != null && !pdfUrl.isEmpty()) {
		                        Request pdfRequest = new Request.Builder().url(pdfUrl).build();
		                        Response pdfResponse = client.newCall(pdfRequest).execute();

		                        if (pdfResponse.isSuccessful()) {
		                            InputStream inputStream = pdfResponse.body().byteStream();
		                            downloadedFile = new File("boarding_pass.pdf");
		                            FileOutputStream outputStream = new FileOutputStream(downloadedFile);

		                            byte[] buffer = new byte[4096];
		                            int bytesRead;
		                            while ((bytesRead = inputStream.read(buffer)) != -1) {
		                                outputStream.write(buffer, 0, bytesRead);
		                            }

		                            outputStream.close();
		                            inputStream.close();
		                        } else {
		                            errorMessage = "Failed to download PDF.";
		                        }
		                    } else {
		                        errorMessage = "PDF not ready after polling.";
		                    }
		                } catch (Exception ex) {
		                    ex.printStackTrace();
		                    errorMessage = "Error during submission or processing.";
		                }
		                return null;
		            }

		            @Override
		            protected void done() {
		                loadingDialog.dispose(); 

		                if (errorMessage != null) {
		                    JOptionPane.showMessageDialog(frame, errorMessage);
		                } else if (downloadedFile != null && downloadedFile.exists()) {
		                    JOptionPane.showMessageDialog(frame, "PDF downloaded successfully!");

		                    try {
		                        if (Desktop.isDesktopSupported()) {
		                            Desktop.getDesktop().open(downloadedFile);
		                        } else {
		                            JOptionPane.showMessageDialog(frame, "Preview not supported on this system.");
		                        }
		                    } catch (Exception ex) {
		                        ex.printStackTrace();
		                        JOptionPane.showMessageDialog(frame, "Error opening PDF.");
		                    }
		                }
		            }
		        };

		        worker.execute();
		        loadingDialog.setVisible(true);
		    }
		});




	}

}