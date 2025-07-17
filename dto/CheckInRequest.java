package dto;

import org.json.JSONObject;

public class CheckInRequest {
	private int bookingId;

    public CheckInRequest(JSONObject json) {
        this.bookingId = json.optInt("booking_id");
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }
}
