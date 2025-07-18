package model;

public class BookingSeats {
	private Booking booking;
	private Seat seat;
	private Passenger passenger;
    private String checkin_status;
    
    public BookingSeats() {}
    public BookingSeats(Booking booking, Seat seat, Passenger passenger, String checkin_status) {
    	this.booking = booking;
    	this.seat = seat;
    	this.passenger = passenger;
    	this.checkin_status = checkin_status;
    }
	public Booking getBooking() {
		return booking;
	}
	public void setBooking(Booking booking) {
		this.booking = booking;
	}
	public Seat getSeat() {
		return seat;
	}
	public void setSeat(Seat seat) {
		this.seat = seat;
	}
	public Passenger getPassenger() {
		return passenger;
	}
	public void setPassenger(Passenger passenger) {
		this.passenger = passenger;
	}
	public String getCheckin_status() {
		return checkin_status;
	}
	public void setCheckin_status(String checkin_status) {
		this.checkin_status = checkin_status;
	}
    
    
}
