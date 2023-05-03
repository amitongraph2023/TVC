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
public class MasterKeyLogs {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull
	@Column(name="masterkey_id")
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


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

}
