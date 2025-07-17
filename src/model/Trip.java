package model;

import java.time.LocalDateTime;

public class Trip {
	//private int tripID;
	//private int busID;
	private Trip trip;
	private Bus bus;
	private int departureStationID;
	private int arrivalStationID;
	private LocalDateTime departureTime;
	private LocalDateTime arrivalTime;
	private String status;
	private double price;
	/*
	public int getTripID() {
		return tripID;
	}
	public void setTripID(int tripID) {
		this.tripID = tripID;
	}
	public int getBusID() {
		return busID;
	}
	public void setBusID(int busID) {
		this.busID = busID;
	}
	*/
	public Trip() {}
	public Trip(Trip trip, Bus bus, int departureStationID, int arrivalStationID, LocalDateTime departureTime,
			LocalDateTime arrivalTime, String status, double price) {
		super();
		this.trip = trip;
		this.bus = bus;
		this.departureStationID = departureStationID;
		this.arrivalStationID = arrivalStationID;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.status = status;
		this.price = price;
	}
	public int getDepartureStationID() {
		return departureStationID;
	}
	public void setDepartureStationID(int departureStationID) {
		this.departureStationID = departureStationID;
	}
	public int getArrivalStationID() {
		return arrivalStationID;
	}
	public void setArrivalStationID(int arrivalStationID) {
		this.arrivalStationID = arrivalStationID;
	}
	public LocalDateTime getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(LocalDateTime departureTime) {
		this.departureTime = departureTime;
	}
	public LocalDateTime getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(LocalDateTime arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
}
