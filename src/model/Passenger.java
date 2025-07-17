package model;

//model for passenger
public class Passenger {
	private int passengerID;
    private String name;
    private String gender;
    private String telNo;
    private int age;
    
    public Passenger() {}
	public Passenger(int passengerID, String name, String gender, String telNo, int age) {
		this.passengerID = passengerID;
		this.name = name;
		this.gender = gender;
		this.telNo = telNo;
		this.age = age;
	}
	public int getPassengerID() {
		return passengerID;
	}
	public void setPassengerID(int passengerID) {
		this.passengerID = passengerID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getTelNo() {
		return telNo;
	}
	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
}
