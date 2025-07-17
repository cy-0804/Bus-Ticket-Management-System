package dto;

import org.json.JSONObject;

public class BusStatusUpdateRequest {
	private int tripId;
    private String status;

    public BusStatusUpdateRequest(JSONObject json) {
        this.tripId = json.optInt("trip_id");
        this.status = json.optString("status");
    }

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
