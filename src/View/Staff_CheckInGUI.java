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

	public Staff_CheckInGUI() {
		initialize();
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

		//to update if check in
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
	}
}
