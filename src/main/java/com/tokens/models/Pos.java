package com.tokens.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Pos {

	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer internalPosId;

	private Integer terminalId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "merchantId")
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
