package controller;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

//import dto.CheckInRequest;

//public class checkInController extends HttpServlet{
//	private static final long serialVersionUID = 1L;
//    private final CheckInService checkInService = new CheckInService();
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        response.setContentType("application/json");
//
//        BufferedReader reader = request.getReader();
//        StringBuilder jsonBuffer = new StringBuilder();
//        String line;
//        while ((line = reader.readLine()) != null) {
//            jsonBuffer.append(line);
//        }
//
//        JSONObject jsonRequest = new JSONObject(jsonBuffer.toString());
//
//        CheckInRequest checkInRequest = new CheckInRequest();
//        checkInRequest.setBookingId(jsonRequest.getInt("bookingId"));
//        checkInRequest.setPassengerId(jsonRequest.getInt("passengerId"));
//
//        // Call service and return response
//        String resultJson = checkInService.checkInPassenger(checkInRequest);
//        response.getWriter().write(resultJson);
//    }
//}

public class checkInController {
    public static JSONObject sendCheckIn(String bookingId) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json");

        try {
            JSONObject requestData = new JSONObject();
            requestData.put("booking_id", bookingId);

            RequestBody body = RequestBody.create(JSON, requestData.toString());
            Request request = new Request.Builder()
                    .url("http://localhost/checkin.php")
                    .post(body)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                String responseBody = response.body().string();

                System.out.println("RAW RESPONSE:");
                System.out.println(responseBody);  // 🔍 print the response here!

                if (!responseBody.trim().startsWith("{")) {
                    throw new IOException("Invalid JSON received:\n" + responseBody);
                }

                return new JSONObject(responseBody);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            throw new IOException("Failed to create or parse JSON.", e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Network error or invalid response.", e);
        }
    }
}

