package com.tokens.models;

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
	
	@Column(name="terminal_id")
	private Integer transactionId;
	
	@Column(name="token")
	private String token;
	
	@Column(name="customer_id")
	private Integer customerId;
	
	@Column(name="amount")
	private Double amount;

	@Column(name="created_date")
	private String createdDate;
	
	@Column(name="merchant_id")
	private Integer merchantId;
	
	@Column(name="merchant_name")
	private String merchantName;
	//( we have to maintain the master data for that )
	
	@Column(name="pos_id")
	private Integer posId;
	
	@Column(name="card_number")
	private String cardNumber;
	
	@Column(name="source_ip")
	private String sourceIp;
	
	//optional
	@Column(name="gps_location")
    private String gpsLocation;

	public Transaction(String token, Integer customerId, Double amount, String createdDate, Integer merchantId, String merchantName,Integer posId,

			String cardNumber, String sourceIp, String gpsLocation) {
		super();
		this.token = token;
		this.customerId = customerId;
		this.amount = amount;
		this.createdDate = createdDate;
		this.merchantId = merchantId;
		this.merchantName = merchantName;
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
		return merchantId;
	}

	public void setLocationId(Integer locationId) {
		this.merchantId = locationId;
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

	public Integer getInternalTransactionId() {
		return internalTransactionId;
	}

	public void setInternalTransactionId(Integer internalTransactionId) {
		this.internalTransactionId = internalTransactionId;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Integer getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Integer merchantId) {
		this.merchantId = merchantId;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
}
