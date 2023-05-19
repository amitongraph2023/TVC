package com.tokens.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Pos {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer internalPosId;

	@Column(name="terminal_id")
	private Integer terminalId;


	@Column(name="merchant_id")
	private Integer merchantId;
	/*
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * 
	 * @JoinColumn(name = "merchantId",referencedColumnName = "merchant_id",
	 * nullable = false)
	 * 
	 * @JsonBackReference private Location location;
	 */

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

	public Integer getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Integer merchantId) {
		this.merchantId = merchantId;
	}

	/**
	 * @param terminalId
	 * @param merchantId
	 */
	public Pos(Integer terminalId, Integer merchantId) {
		super();
		this.terminalId = terminalId;
		this.merchantId = merchantId;
	}


}
