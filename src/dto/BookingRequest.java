package dto;

import java.util.List;

public class BookingRequest {
    private int tripId;
    private int staffId;
    private int customerId;
    //private PassengerDTO passenger; //xy
    private List<Integer> seats;
    //private PaymentDTO payment;	//xy

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
    /*
    public PassengerDTO getPassenger() {
        return passenger;
    }
    public void setPassenger(PassengerDTO passenger) {
        this.passenger = passenger;
    }
    */
    public List<Integer> getSeats() {
        return seats;
    }
    public void setSeats(List<Integer> seats) {
        this.seats = seats;
    }
    /*
    public PaymentDTO getPayment() {
        return payment;
    }
    public void setPayment(PaymentDTO payment) {
        this.payment = payment;
    }
    */
}
