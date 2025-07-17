package model;

import java.time.LocalDateTime;

//booking model
public class Booking {
	private String bookingID;
    private int tripID;
    private User customer;
    private User staff;
    private Payment payment;
    //private int bookedBy;
    //private int managedBy;
    //private int paymentID; 
    private LocalDateTime bookingDate;
    private double totalPrice;
    public Booking() {
    }

    public Booking(String bookingID, int tripID, User customer, User staff, Payment payment, LocalDateTime bookingDate, double totalPrice) {
        this.bookingID = bookingID;
        this.tripID = tripID;
        this.customer = customer;
        this.staff = staff;
        this.payment = payment;
        this.bookingDate = bookingDate;
        this.totalPrice = totalPrice;
    }
	public String getBookingID() {
		return bookingID;
	}
	public void setBookingID(String bookingID) {
		this.bookingID = bookingID;
	}
	public int getTripID() {
		return tripID;
	}
	public void setTripID(int tripID) {
		this.tripID = tripID;
	}
	public User getCustomer() {
		return customer;
	}
	public void setCustomer(User customer) {
		this.customer = customer;
	}
	public User getStaff() {
		return staff;
	}
	public void setStaff(User staff) {
		this.staff = staff;
	}
	public Payment getPayment() {
		return payment;
	}
	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	/*
	public int getBookedBy() {
		return bookedBy;
	}
	public void setBookedBy(int bookedBy) {
		this.bookedBy = bookedBy;
	}
	public int getManagedBy() {
		return managedBy;
	}
	public void setManagedBy(int managedBy) {
		this.managedBy = managedBy;
	}
	public int getPaymentID() {
		return paymentID;
	}
	public void setPaymentID(int paymentID) {
		this.paymentID = paymentID;
	}
	*/
	public LocalDateTime getBookingDate() {
		return bookingDate;
	}
	public void setBookingDate(LocalDateTime bookingDate) {
		this.bookingDate = bookingDate;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
}
