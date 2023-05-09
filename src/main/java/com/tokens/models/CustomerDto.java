package com.tokens.models;


public class CustomerDto {

	public Integer customerId;
	public Double totalAmount;
    
	public CustomerDto() {}
	
	public CustomerDto(Integer customerId, Double totalAmount) {
		this.customerId = customerId;
		this.totalAmount = totalAmount;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

}
