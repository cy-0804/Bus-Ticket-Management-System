package model;

//bus model
public class Bus {
	private int busID;
    private String busNum;
    private String plateNo;
    
    public Bus() {}
	public Bus(int busID, String busNum, String plateNo) {
		super();
		this.busID = busID;
		this.busNum = busNum;
		this.plateNo = plateNo;
	}
	public int getBusID() {
		return busID;
	}
	public void setBusID(int busID) {
		this.busID = busID;
	}
	public String getBusNum() {
		return busNum;
	}
	public void setBusNum(String busNum) {
		this.busNum = busNum;
	}
	public String getPlateNo() {
		return plateNo;
	}
	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}
    
    
}
