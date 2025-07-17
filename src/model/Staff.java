package model;

//model for staff
public class Staff {
	private User user;
	//private int userID;
	private String position;
	private String department;
	private String status;
	private Station station;
	//private int stationID;
	/*
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	*/
	public Staff() {}
	public Staff(User user, String position, String department, String status, Station station) {
		this.user = user;
		this.position = position;
		this.department = department;
		this.status = status;
		this.station = station;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Station getStation() {
		return station;
	}
	public void setStation(Station station) {
		this.station = station;
	}
	/*
	public int getStationID() {
		return stationID;
	}
	public void setStationID(int stationID) {
		this.stationID = stationID;
	}
	*/
	
	
}
