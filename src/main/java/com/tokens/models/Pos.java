package com.tokens.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Pos {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer internalPosId;

	@Column(name="terminal_id")
	private Integer terminalId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "merchantId",referencedColumnName = "merchant_id", nullable = false)
	@JsonBackReference
	private Location location;

	public Integer getInternalPosId() {
		return internalPosId;
	}

	public void setInternalPosId(Integer internalPosId) {
		this.internalPosId = internalPosId;
	}

	public Integer getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(Integer terminalId) {
		this.terminalId = terminalId;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

}
