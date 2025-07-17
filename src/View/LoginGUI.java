package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;


import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.json.JSONObject;

import java.awt.Color;
import javax.swing.JButton;

public class LoginGUI {

	private JFrame frame;
	private JTextField txtUsername;
	private JPasswordField passwordField;
	private JPasswordField txtPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI window = new LoginGUI();
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
	public LoginGUI() {
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(130, 182, 234));
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblLogin = new JLabel("LOG IN");
		lblLogin.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 25));
		lblLogin.setBounds(174, 31, 67, 29);
		frame.getContentPane().add(lblLogin);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(158, 92, 96, 19);
		frame.getContentPane().add(txtUsername);
		txtUsername.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setFont(new Font("Tw Cen MT Condensed", Font.PLAIN, 25));
		lblUsername.setBounds(67, 88, 96, 23);
		frame.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Tw Cen MT Condensed", Font.PLAIN, 25));
		lblPassword.setBounds(67, 121, 96, 23);
		frame.getContentPane().add(lblPassword);
		
		JButton btnLogin = new JButton("Log In");
		btnLogin.setFont(new Font("Tw Cen MT Condensed", Font.PLAIN, 15));
		btnLogin.setBounds(269, 125, 85, 21);
		frame.getContentPane().add(btnLogin);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(158, 126, 96, 19);
		frame.getContentPane().add(txtPassword);
			
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = txtUsername.getText();
				String password = txtPassword.getText();
				loginUser(username, password);
			}
		});
	}
	
	private void loginUser(String username, String password) {
		try {
			URL url = new URL("http://localhost/webServiceJSON/login.php");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json; utf-8");
			conn.setRequestProperty("Accept", "application/json");
			conn.setDoOutput(true);

			JSONObject jsonInput = new JSONObject();
			jsonInput.put("username", username);
			jsonInput.put("password", password);

			try (OutputStream os = conn.getOutputStream()) {
				byte[] input = jsonInput.toString().getBytes(StandardCharsets.UTF_8);
				os.write(input, 0, input.length);
			}

			InputStream responseStream = conn.getInputStream();
			Scanner scanner = new Scanner(responseStream, "UTF-8").useDelimiter("\\A");
			String responseBody = scanner.hasNext() ? scanner.next() : "";

			JSONObject jsonResponse = new JSONObject(responseBody);

			String status = jsonResponse.getString("status");

			if (status.equals("success")) {
				JSONObject data = jsonResponse.getJSONObject("data");
				String role = data.getString("role");
				int userID = data.getInt("user_id");
				System.out.println("Login successful! Role: " + role);

				if (role.equalsIgnoreCase("staff")) {
					SwingUtilities.invokeLater(() -> {
						new StaffDashboardGUI(userID); 
					});
				} else if (role.equalsIgnoreCase("customer")) {
					SwingUtilities.invokeLater(() -> {
						new CustomerDashboardGUI(userID); 
					});
				}

				frame.dispose(); // close login window
			} else {
				String message = jsonResponse.getString("message");
				System.out.println("Login failed: " + message);
			}

			conn.disconnect();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
