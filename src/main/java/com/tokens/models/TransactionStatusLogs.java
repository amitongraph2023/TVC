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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "transaction_id")
	private String transactionId;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "last_updated")
	private String lastUpdated;
	
	@Column(name="system_id")
	private String systemId;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
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
	
	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	
	public TransactionStatusLogs() {
		super();
	}
	
	public TransactionStatusLogs(String transactionId, String status, String lastUpdated,
			String systemId) {
		super();
		this.transactionId = transactionId;
		this.status = status;
		this.lastUpdated = lastUpdated;
		this.systemId = systemId;
	}
	
}
