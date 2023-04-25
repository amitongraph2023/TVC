package com.tokens.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table
public class MasterKeyLogs {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer Id;
	
	@NotNull
	private Integer masterKeyId;
	
	@NotNull
	private String masterKey;
	
	@NotNull
	private Integer userId;
	
	@NotNull
	private String createdOn;


	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public Integer getMasterKeyId() {
		return masterKeyId;
	}

	public void setMasterKeyId(Integer masterKeyId) {
		this.masterKeyId = masterKeyId;
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

}
