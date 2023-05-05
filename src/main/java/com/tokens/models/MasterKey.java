package com.tokens.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table
public class MasterKey {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer masterkeyId;

	@NotNull
	@Column(name="master_key")
	private String masterKey;
	
	@NotNull
	@Column(name="user_id")
	private Integer userId;
	
	@NotNull
	@Column(name="created_on")
	private String createdOn;
	
	@Column(name="last_updated")
	private String lastUpdated;
	
	@Column(name="system_id")
	private String systemId;

	public Integer getMasterKeyId() {
		return masterkeyId;
	}

	public void setMasterKeyId(Integer masterKeyId) {
		this.masterkeyId = masterKeyId;
	}

	public String getMasterKey() {
		return masterKey;
	}

	public void setMasterKey(String masterKey) {
		this.masterKey = masterKey;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public Integer getMasterkeyId() {
		return masterkeyId;
	}

	public void setMasterkeyId(Integer masterkeyId) {
		this.masterkeyId = masterkeyId;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	
	

}
