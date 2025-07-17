package dto;

import org.json.JSONObject;

public class BusStatusUpdateRequest {
	private int tripId;
    private String status;

    public BusStatusUpdateRequest() {}

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
