package model;

//seat model
public class Seat {
	private int seatID;
	private int seatNumber;
	private Trip trip;
	private String status;
	
	public Seat() {}
	public Seat(int seatID, int seatNumber, Trip trip, String status) {
		this.seatID = seatID;
		this.seatNumber = seatNumber;
		this.trip = trip;
		this.status = status;
	}
	public int getSeatID() {
		return seatID;
	}
	public void setSeatID(int seatID) {
		this.seatID = seatID;
	}
	public int getSeatNumber() {
		return seatNumber;
	}
	public void setSeatNumber(int seatNumber) {
		this.seatNumber = seatNumber;
	}
	public Trip getTrip() {
		return trip;
	}
	public void setTrip(Trip trip) {
		this.trip = trip;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
