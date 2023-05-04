package com.tokens.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table
public class User {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userId;
	
	@Column(name="userName")
	private String userName;
	
	@Column(name="password")
	private String password;
	
	@NotBlank(message = "email is null")
	@Column(name="email")
	private String email;
	
	@Column(name="role")
	private String role;
	
	@NotBlank(message = "systemId is null")
	@Column(name="systemId")
	
	private String systemId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

}
