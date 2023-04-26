package com.tokens.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class TransactionStatusLogs {

	private Integer internalTransactionId;
	
	private Integer transactionId;
	private String status;
	private String lastUpdated;
	

	public Integer getInternalTransactionId() {
		return internalTransactionId;
	}
	public void setInternalTransactionId(Integer internalTransactionId) {
		this.internalTransactionId = internalTransactionId;
	}
	public Integer getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
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

	
	public TransactionStatusLogs(Integer internalTransactionId, Integer transactionId, String status, String lastUpdated) {
		super();
		this.internalTransactionId = internalTransactionId;
		this.transactionId = transactionId;
		this.status = status;
		this.lastUpdated = lastUpdated;
	}
	
	
	
}
