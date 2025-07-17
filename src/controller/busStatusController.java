/*package controller;

import java.io.BufferedReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import dto.BusStatusUpdateRequest;

public class busStatusController {
	private static final long serialVersionUID = 1L;
    private final BusStatusService busStatusService = new BusStatusService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        JSONObject json = new JSONObject(sb.toString());

        BusStatusUpdateRequest statusRequest = new BusStatusUpdateRequest();
        statusRequest.setTripId(json.getInt("tripId"));
        statusRequest.setStatus(json.getString("status"));

        String result = busStatusService.updateBusStatus(statusRequest);
        response.getWriter().write(result);
    }
}
*/
