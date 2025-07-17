package controller;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import dto.BookingRequest;

public class bookingController {
	private static final long serialVersionUID = 1L;
    private final BookingService bookingService = new BookingService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        JSONObject json = new JSONObject(sb.toString());

        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setPassengerId(json.getInt("passengerId"));
        bookingRequest.setTripId(json.getInt("tripId"));
        bookingRequest.setSeats(json.getJSONArray("seats").toList()); 

        String result = bookingService.createBooking(bookingRequest);
        response.getWriter().write(result);
    }
}
