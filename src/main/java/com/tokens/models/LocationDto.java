package com.tokens.models;

public class LocationDto {

	public Integer merchantId;
	public Double totalAmount;
	
	public LocationDto() {
		super();
	}
	
	public LocationDto(Integer merchantId, Double totalAmount) {
		super();
		this.merchantId = merchantId;
		this.totalAmount = totalAmount;
	}

	public Integer getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Integer merchantId) {
		this.merchantId = merchantId;
	}


	public Double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	
}
