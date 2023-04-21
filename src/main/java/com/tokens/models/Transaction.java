package com.tokens.models;

import java.util.Date;

//Entity
public class Transaction {
	
	//primary Key
	private String token;
	private Integer userId;
	private String amount;
	private Date createdDate;
	private Integer locationId;
	//( we have to maintain the master data for that )
	private Integer posId;
	private String cardNumber;
	
	//cardNumber and masterKey are might be same entity
	private String masterKey;
	
	
	
	public Transaction(String token, Integer userId, String amount, Date createdDate, Integer locationId, Integer posId,
			String cardNumber, String masterKey, String gpsLocation) {
		super();
		this.token = token;
		this.userId = userId;
		this.amount = amount;
		this.createdDate = createdDate;
		this.locationId = locationId;
		this.posId = posId;
		this.cardNumber = cardNumber;
		this.masterKey = masterKey;
		this.gpsLocation = gpsLocation;
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

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getMasterKey() {
		return masterKey;
	}

	public void setMasterKey(String masterKey) {
		this.masterKey = masterKey;
	}

	public String getGpsLocation() {
		return gpsLocation;
	}

	public void setGpsLocation(String gpsLocation) {
		this.gpsLocation = gpsLocation;
	}

	//optional
    private String gpsLocation;
	
	
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

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	
}
