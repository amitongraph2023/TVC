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
	private Integer userId;
	private Double amount;
	private Date createdDate;
	private String locationId;
	//( we have to maintain the master data for that )
	private String posId;
	private String cardNumber;
	private String sourceIp;
	
	private String status;
	
	private String lastUpdated;
	
	//optional
    private String gpsLocation;
	
	public Transaction(String token, Integer userId, Double amount, Date createdDate, String locationId, String posId,
			String cardNumber, String sourceIp, String gpsLocation) {
		super();
		this.token = token;
		this.userId = userId;
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
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public String getPosId() {
		return posId;
	}

	public void setPosId(String posId) {
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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

}
