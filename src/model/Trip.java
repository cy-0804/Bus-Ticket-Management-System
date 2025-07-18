package model;

import java.time.LocalDateTime;

public class Trip {
    private int id; 
    private int busId;
    private int departureStationID;
    private int arrivalStationID;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String status;
    private double price;

    public Trip() {}

    public Trip(int id, int busId, int departureStationID, int arrivalStationID, LocalDateTime departureTime,
                LocalDateTime arrivalTime, String status, double price) {
        this.id = id;
        this.busId = busId;
        this.departureStationID = departureStationID;
        this.arrivalStationID = arrivalStationID;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.status = status;
        this.price = price;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getBusId() {
        return busId;
    }
    public void setBusId(int busId) {
        this.busId = busId;
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
