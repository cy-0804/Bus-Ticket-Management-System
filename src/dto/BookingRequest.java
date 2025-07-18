package dto;

import java.util.List;

public class BookingRequest {
    private int tripId;
    private int staffId;
    private int customerId;
    private List<Integer> seats;

    public int getTripId() {
        return tripId;
    }
    public void setTripId(int tripId) {
        this.tripId = tripId;
    }
    public int getStaffId() {
        return staffId;
    }
    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }
    public int getCustomerId() {
        return customerId;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    
    public List<Integer> getSeats() {
        return seats;
    }
    public void setSeats(List<Integer> seats) {
        this.seats = seats;
    }
}
