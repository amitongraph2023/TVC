package com.tokens.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class TransactionStatusLogs {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name = "transaction_id")
	private Integer transactionId;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "last_updated")
	private String lastUpdated;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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

	public TransactionStatusLogs(Integer transactionId, String status, String lastUpdated) {
		super();
		this.transactionId = transactionId;
		this.status = status;
		this.lastUpdated = lastUpdated;
	}
	
}
