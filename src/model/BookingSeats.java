package model;

public class BookingSeats {
	private Booking booking;
	private Seat seat;
	private Passenger passenger;
	//private String bookingID;
    //private int seatID;
    //private int passengerID;
    private String checkin_status;
    
    /*
	public String getBookingID() {
		return bookingID;
	}
	public void setBookingID(String bookingID) {
		this.bookingID = bookingID;
	}
	public int getSeatID() {
		return seatID;
	}
	public void setSeatID(int seatID) {
		this.seatID = seatID;
	}
	public int getPassengerID() {
		return passengerID;
	}
	public void setPassengerID(int passengerID) {
		this.passengerID = passengerID;
	}
	*/
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
