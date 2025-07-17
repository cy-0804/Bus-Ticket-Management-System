package model;

//station model
public class Station {
	private int stationID;
	public String stationName;
	public String state;
	public String location;
	public String telNo;
	
	public Station() {}
	public Station(int stationID, String stationName, String state, String location, String telNo) {
		this.stationID = stationID;
		this.stationName = stationName;
		this.state = state;
		this.location = location;
		this.telNo = telNo;
	}
	public int getStationID() {
		return stationID;
	}
	public void setStationID(int stationID) {
		this.stationID = stationID;
	}
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getTelNo() {
		return telNo;
	}
	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}
	
	
}
