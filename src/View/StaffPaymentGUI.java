package View;

import javax.swing.*;
import java.awt.*;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import javax.swing.GroupLayout.Alignment;

public class StaffPaymentGUI {
    private int tripID, userID;
    private String origin, destination, departDate, arrivalDate, plateNo;
    private Set<String> seatIDs;
    private double totalPrice;
    private JFrame frame;
    private List<PassengerForm> passengerForms = new ArrayList<>();
    private JComboBox<String> paymentMethodBox;
    private JComboBox<String> genderBox;
    private JTextField nameField, phoneField, icField, totalAmountField;

    public StaffPaymentGUI(int userID, int tripID, Set<String> seatIDs, double totalPrice,
                           String origin, String destination, String departDate, String arrivalDate, String plateNo) {
        this.userID = userID;
        this.tripID = tripID;
        this.seatIDs = seatIDs;
        this.totalPrice = totalPrice;
        this.origin = origin;
        this.destination = destination;
        this.departDate = departDate;
        this.arrivalDate = arrivalDate;
        this.plateNo = plateNo;

        frame = new JFrame("Payment");
        frame.setSize(500, 450);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPane.setBackground(new Color(240, 248, 255)); // Light blue background
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

        JLabel icLabel = new JLabel("IC No:");
        icField = new JTextField(15);

        JLabel totalLabel = new JLabel("Total Amount (RM):");
        totalAmountField = new JTextField(String.format("%.2f", totalPrice));
        totalAmountField.setEditable(false);
        totalAmountField.setBackground(new Color(220, 220, 220));

        JLabel paymentLabel = new JLabel("Payment Method:");
        paymentMethodBox = new JComboBox<>(new String[]{"Cash", "Credit Card", "Online Banking", "E-Wallet"});

        JButton submitButton = new JButton("Confirm Payment");
        submitButton.setBackground(new Color(70, 130, 180));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);

        // Layout using GroupLayout
        GroupLayout layout = new GroupLayout(contentPane);
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.CENTER)
        		.addComponent(titleLabel)
        		.addGroup(layout.createSequentialGroup()
        			.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        				.addComponent(nameLabel)
        				.addComponent(genderLabel)
        				.addComponent(phoneLabel)
        				.addComponent(icLabel)
        				.addComponent(totalLabel)
        				.addComponent(paymentLabel))
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(nameField, 331, 331, 331)
        				.addComponent(genderBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(phoneField, 331, 331, 331)
        				.addComponent(icField, 331, 331, 331)
        				.addComponent(totalAmountField, 331, 331, 331)
        				.addComponent(paymentMethodBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
        		.addComponent(submitButton, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addComponent(titleLabel)
        			.addGap(15)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(nameLabel)
        				.addComponent(nameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(genderLabel)
        				.addComponent(genderBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(phoneLabel)
        				.addComponent(phoneField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(icLabel)
        				.addComponent(icField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(totalLabel)
        				.addComponent(totalAmountField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(paymentLabel)
        				.addComponent(paymentMethodBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(20)
        			.addComponent(submitButton)
        			.addGap(138))
        );
        contentPane.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        frame.setVisible(true);
    }
}
