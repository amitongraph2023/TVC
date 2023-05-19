package com.tokens.models;


public class CustomerDto {

	public String customerId;
	public Double totalAmount;
    
	public CustomerDto() {}
	
	public CustomerDto(String customerId, Double totalAmount) {
		this.customerId = customerId;
		this.totalAmount = totalAmount;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

}
