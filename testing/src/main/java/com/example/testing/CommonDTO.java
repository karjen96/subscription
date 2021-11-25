package com.example.testing;

import java.util.List;

public class CommonDTO {

	// request and response
	private double amount;
	private SubscriptionType type;
	private String dayOfWeek;
	private String startDate;
	private String endDate;
	private List<String>invoiceDate;
	
	public CommonDTO() {
		
	}
	
	public CommonDTO(double amount, SubscriptionType type, String startDate, String endDate) {
		this.amount = amount;
		this.type = type;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public CommonDTO(double amount, SubscriptionType type, String dayOfWeek, String startDate, String endDate) {
		this.amount = amount;
		this.type = type;
		this.dayOfWeek = dayOfWeek;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public SubscriptionType getType() {
		return type;
	}
	public void setType(SubscriptionType type) {
		this.type = type;
	}
	public String getDayOfWeek() {
		return dayOfWeek;
	}
	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public List<String> getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(List<String> invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	
	
}
