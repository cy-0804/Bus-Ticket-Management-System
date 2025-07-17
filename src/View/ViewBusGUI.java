package View;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.*;
import java.io.*;
import java.net.*;
import org.json.*;

public class ViewBusGUI {

	private JFrame frame;
	private JTextField busIDTxt;
	private JTextField DateTxt;
	private JTable table;
	private DefaultTableModel model;
	private JButton searchBtn;
	private JButton btnBack;

	public ViewBusGUI() {
	    initialize();
	    frame.setVisible(true);
	}

	private void initialize() {
	    frame = new JFrame();
	    frame.getContentPane().setBackground(new Color(130, 182, 234));
	    frame.setBounds(100, 100, 906, 502);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.getContentPane().setLayout(null);

	    JLabel lblViewBusSchedule = new JLabel("View Bus Schedule");
	    lblViewBusSchedule.setForeground(new Color(0, 64, 128));
	    lblViewBusSchedule.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 25));
	    lblViewBusSchedule.setBounds(280, 10, 215, 40);
	    frame.getContentPane().add(lblViewBusSchedule);

	    frame.getContentPane().add(createLabel("Bus ID:", 34, 61));
	    busIDTxt = new JTextField();
	    busIDTxt.setBounds(100, 68, 120, 19);
	    frame.getContentPane().add(busIDTxt);

	    frame.getContentPane().add(createLabel("Date:", 240, 61));
	    DateTxt = new JTextField();
	    DateTxt.setBounds(290, 68, 130, 19);
	    frame.getContentPane().add(DateTxt);

	    searchBtn = new JButton("Search");
	    searchBtn.setFont(new Font("Tahoma", Font.PLAIN, 15));
	    searchBtn.setBounds(450, 61, 130, 25);
	    frame.getContentPane().add(searchBtn);

	    frame.getContentPane().add(createLabel("Schedule Trip:", 34, 100));

	    JScrollPane scrollPane = new JScrollPane();
	    scrollPane.setBounds(10, 130, 860, 284);
	    frame.getContentPane().add(scrollPane);

	    model = new DefaultTableModel(new String[]{"Trip ID", "Departure", "Arrival", "From", "To", "Status", "Action"}, 0);
	    table = new JTable(model);
	    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	    table.getColumn("Status").setCellEditor(new DefaultCellEditor(new JComboBox<>(new String[]{
	        "scheduled", "boarding", "delayed", "departed", "cancelled"
	    })));
	    table.getColumn("Action").setCellRenderer(new ButtonRenderer());
	    table.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox()));
	    scrollPane.setViewportView(table);
	    
	    btnBack = new JButton("Back");
	    btnBack.setFont(new Font("Tw Cen MT Condensed", Font.PLAIN, 20));
	    btnBack.setBounds(784, 424, 65, 31);
	    frame.getContentPane().add(btnBack);
	    
	    btnBack.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            frame.dispose(); 
	            new StaffDashboardGUI(); 
	        }
	    });

	    searchBtn.addActionListener(e -> {
	        String busID = busIDTxt.getText().trim();
	        String date = DateTxt.getText().trim();
	        if (!busID.isEmpty() && !date.isEmpty()) {
	            loadTripData(busID, date);
	        } else {
	            JOptionPane.showMessageDialog(frame, "Please enter both Bus ID and Date.");
	        }
	    });
	}

	private JLabel createLabel(String text, int x, int y) {
	    JLabel label = new JLabel(text);
	    label.setFont(new Font("Tahoma", Font.BOLD, 15));
	    label.setForeground(Color.BLACK);
	    label.setBounds(x, y, 150, 25);
	    return label;
	}

	private void loadTripData(String busID, String date) {
	    try {
	        URL url = new URL("http://localhost/busApi/staff_view_bus.php?busID=" + busID + "&date=" + date);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");

	        int responseCode = conn.getResponseCode();
	        if (responseCode != HttpURLConnection.HTTP_OK) {
	            JOptionPane.showMessageDialog(frame, "Server error: " + responseCode);
	            return;
	        }

	        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        StringBuilder response = new StringBuilder();
	        String inputLine;

	        while ((inputLine = in.readLine()) != null) {
	            response.append(inputLine);
	        }
	        in.close();

	        if (response.toString().isEmpty()) {
	            JOptionPane.showMessageDialog(frame, "No response from server.");
	            return;
	        }

	        JSONArray trips = new JSONArray(response.toString());
	        model.setRowCount(0);

	        if (trips.length() == 0) {
	            JOptionPane.showMessageDialog(frame, "No trip found for Bus ID " + busID + " on " + date);
	            return;
	        }

	        for (int i = 0; i < trips.length(); i++) {
	            JSONObject trip = trips.getJSONObject(i);
	            model.addRow(new Object[]{
	                trip.getInt("tripID"),
	                trip.getString("departureTime"),
	                trip.getString("arrivalTime"),
	                trip.getString("fromStation"),
	                trip.getString("toStation"),
	                trip.getString("status"),
	                "Update Status"
	            });
	        }

	        resizeColumnWidths(table);

	    } catch (IOException e) {
	        JOptionPane.showMessageDialog(frame, "Connection failed: " + e.getMessage());
	    } catch (JSONException e) {
	        JOptionPane.showMessageDialog(frame, "Invalid data format from server.");
	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(frame, "Unexpected error: " + e.getMessage());
	    }
	}

	private void resizeColumnWidths(JTable table) {
	    final TableColumnModel columnModel = table.getColumnModel();
	    for (int column = 0; column < table.getColumnCount(); column++) {
	        int width = 75;
	        TableColumn tableColumn = columnModel.getColumn(column);
	        TableCellRenderer headerRenderer = table.getTableHeader().getDefaultRenderer();
	        Component headerComp = headerRenderer.getTableCellRendererComponent(table, tableColumn.getHeaderValue(), false, false, 0, 0);
	        width = Math.max(headerComp.getPreferredSize().width, width);

	        for (int row = 0; row < table.getRowCount(); row++) {
	            TableCellRenderer cellRenderer = table.getCellRenderer(row, column);
	            Component c = table.prepareRenderer(cellRenderer, row, column);
	            width = Math.max(c.getPreferredSize().width + 10, width);
	        }

	        columnModel.getColumn(column).setPreferredWidth(width);
	    }
	}

	class ButtonRenderer extends JButton implements TableCellRenderer {
	    public ButtonRenderer() {
	        setOpaque(true);
	    }

	    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	        setText("Update Status");
	        return this;
	    }
	}

	class ButtonEditor extends DefaultCellEditor {
	    private JButton button;

	    public ButtonEditor(JCheckBox checkBox) {
	        super(checkBox);
	        button = new JButton("Update Status");
	        button.setOpaque(true);
	        button.addActionListener(e -> {
	            fireEditingStopped();
	            int selectedRow = table.getSelectedRow();
	            int tripID = (int) table.getValueAt(selectedRow, 0);
	            String newStatus = table.getValueAt(selectedRow, 5).toString();

	            try {
	                JSONObject jsonPayload = new JSONObject();
	                jsonPayload.put("tripID", tripID);
	                jsonPayload.put("status", newStatus);

	                URL url = new URL("http://localhost/busApi/bus_status_update.php");
	                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	                conn.setRequestMethod("POST");
	                conn.setRequestProperty("Content-Type", "application/json");
	                conn.setDoOutput(true);

	                OutputStream os = conn.getOutputStream();
	                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
	                writer.write(jsonPayload.toString());
	                writer.flush();
	                writer.close();
	                os.close();

	                int responseCode = conn.getResponseCode();
	                InputStream is = (responseCode == 200) ? conn.getInputStream() : conn.getErrorStream();
	                BufferedReader in = new BufferedReader(new InputStreamReader(is));
	                StringBuilder response = new StringBuilder();
	                String inputLine;
	                while ((inputLine = in.readLine()) != null) {
	                    response.append(inputLine);
	                }
	                in.close();

	                String responseText = response.toString().trim();
	                System.out.println("Raw server response: " + responseText); // Debug line

	                if (!responseText.startsWith("{")) {
	                    JOptionPane.showMessageDialog(frame, "Invalid response from server:\n" + responseText);
	                    return;
	                }

	                JSONObject responseJson = new JSONObject(responseText);
	                if ("success".equalsIgnoreCase(responseJson.optString("status"))) {
	                    JOptionPane.showMessageDialog(frame, responseJson.optString("message", "Trip updated."));
	                    loadTripData(busIDTxt.getText(), DateTxt.getText());
	                } else {
	                    JOptionPane.showMessageDialog(frame, responseJson.optString("message", "Failed to update trip."));
	                }
	            } catch (Exception ex) {
	                JOptionPane.showMessageDialog(frame, "Error updating trip: " + ex.getMessage());
	                ex.printStackTrace();
	            }
	        });
	    }

	    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
	        return button;
	    }

	    public Object getCellEditorValue() {
	        return "Update Status";
	    }

	    public boolean stopCellEditing() {
	        return super.stopCellEditing();
	    }
	}
}
