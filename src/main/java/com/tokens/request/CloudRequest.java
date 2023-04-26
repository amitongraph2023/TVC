package com.tokens.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CloudRequest {
	
	@NotBlank(message = "transactionId is null")
    @Size(min = 2, max = 30)
	private String transactionId;
	
	@NotBlank(message = "customerId is null")
    @Size(min = 2, max = 30)
	private String customerId;
	
	@NotBlank(message = "amount is null")
	private String amount;
	
	@NotBlank(message = "createdDate is null")
    @Size(min = 2, max = 30)
	private String createdDate;
	
	@NotBlank(message = "merchantId is null")
    @Size(min = 2, max = 30)
	private String merchantId;
	
	@NotBlank(message = "merchantName is null")
    @Size(min = 2, max = 30)
	private String merchantName;
	
	@NotBlank(message = "posId is null")
    @Size(min = 2, max = 30)
	private String posId;
	
	@NotBlank(message = "cardNumber is null")
    @Size(min = 2, max = 30)
	private String cardNumber;
	
	@NotBlank(message = "sourceIp is null")
    @Size(min = 2, max = 30)
	private String sourceIp;

	// optional
	private String gpsLocation;

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getPosId() {
		return posId;
	}

	public void setPosId(String posId) {
		this.posId = posId;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getSourceIp() {
		return sourceIp;
	}

	public void setSourceIp(String sourceIp) {
		this.sourceIp = sourceIp;
	}

	public String getGpsLocation() {
		return gpsLocation;
	}

	public void setGpsLocation(String gpsLocation) {
		this.gpsLocation = gpsLocation;
	}

}
