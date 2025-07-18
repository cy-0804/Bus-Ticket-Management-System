
package controller;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.OutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class checkInController {

	//depends
    private static final String CHECKIN_INFO_URL = "http://localhost/busApi/get_booking_info.php";
    private static final String UPDATE_STATUS_URL = "http://localhost/busApi/checkin(1).php";

    public static JSONObject getCheckInInfo(String bookingID) {
        try {
            URL url = new URL(CHECKIN_INFO_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            JSONObject jsonInput = new JSONObject();
            jsonInput.put("booking_id", bookingID);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(jsonInput.toString().getBytes("UTF-8"));
            }

            InputStream responseStream = conn.getInputStream();
            Scanner scanner = new Scanner(responseStream, "UTF-8").useDelimiter("\\A");

            String responseBody = scanner.hasNext() ? scanner.next() : "";
            System.out.println("Raw response: " + responseBody);

            return new JSONObject(responseBody);

        } catch (Exception e) {
            JSONObject error = new JSONObject();
            try {
				error.put("status", "fail");
				error.put("message", e.getMessage());
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            return error;
        }
    }

    public static JSONObject updateCheckInStatus(String bookingID) {
        try {
            URL url = new URL(UPDATE_STATUS_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            JSONObject jsonInput = new JSONObject();
            jsonInput.put("booking_id", bookingID);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(jsonInput.toString().getBytes("UTF-8"));
            }

            InputStream responseStream = conn.getInputStream();
            Scanner scanner = new Scanner(responseStream, "UTF-8").useDelimiter("\\A");
            String responseBody = scanner.hasNext() ? scanner.next() : "";

            return new JSONObject(responseBody);

        } catch (Exception e) {
            JSONObject error = new JSONObject();
            try {
				error.put("status", "fail");
				error.put("message", e.getMessage());
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            return error;
        }
    }
}
