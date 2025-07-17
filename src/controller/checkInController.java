package controller;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import dto.CheckInRequest;

public class checkInController extends HttpServlet{
	private static final long serialVersionUID = 1L;
    private final CheckInService checkInService = new CheckInService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        BufferedReader reader = request.getReader();
        StringBuilder jsonBuffer = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonBuffer.append(line);
        }

        JSONObject jsonRequest = new JSONObject(jsonBuffer.toString());

        CheckInRequest checkInRequest = new CheckInRequest();
        checkInRequest.setBookingId(jsonRequest.getInt("bookingId"));
        checkInRequest.setPassengerId(jsonRequest.getInt("passengerId"));

        // Call service and return response
        String resultJson = checkInService.checkInPassenger(checkInRequest);
        response.getWriter().write(resultJson);
    }
}
