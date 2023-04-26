package com.tokens.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Transaction {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer internalTransactionId;
	
	private Integer transactionId;
	
	private String token;
	private Integer customerId;
	private Double amount;
	private String createdDate;
	private Integer locationId;
	//( we have to maintain the master data for that )
	private Integer posId;
	private String cardNumber;
	private String sourceIp;
	
	//optional
    private String gpsLocation;
	
	public Transaction(String token, Integer customerId, Double amount, String createdDate, Integer locationId, Integer posId,
			String cardNumber, String sourceIp, String gpsLocation) {
		super();
		this.token = token;
		this.customerId = customerId;
		this.amount = amount;
		this.createdDate = createdDate;
		this.locationId = locationId;
		this.posId = posId;
		this.cardNumber = cardNumber;
		this.sourceIp = sourceIp;
		this.gpsLocation = gpsLocation;
	}
	
	public Integer getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}


	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getGpsLocation() {
		return gpsLocation;
	}

	public void setGpsLocation(String gpsLocation) {
		this.gpsLocation = gpsLocation;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getUserId() {
		return customerId;
	}

	public void setUserId(Integer userId) {
		this.customerId = userId;
	}

	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	public Integer getPosId() {
		return posId;
	}

	public void setPosId(Integer posId) {
		this.posId = posId;
	}

	public String getSourceIp() {
		return sourceIp;
	}

	public void setSourceIp(String sourceIp) {
		this.sourceIp = sourceIp;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
}
