package model;

import java.time.LocalDateTime;

//model for payment
public class Payment {
	private int paymentID;
	private LocalDateTime paymentDate;
	private double amount;
	
	public Payment() {}
	public Payment(int paymentID, LocalDateTime paymentDate, double amount, String paymentMethod) {
		this.paymentID = paymentID;
		this.paymentDate = paymentDate;
		this.amount = amount;
		this.paymentMethod = paymentMethod;
	}
	private String paymentMethod;
	
	
	public int getPaymentID() {
		return paymentID;
	}
	public void setPaymentID(int paymentID) {
		this.paymentID = paymentID;
	}
	public LocalDateTime getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(LocalDateTime paymentDate) {
		this.paymentDate = paymentDate;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	
	
}
