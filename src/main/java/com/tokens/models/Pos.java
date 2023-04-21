package com.tokens.models;


//Entity
public class Pos {

	private Integer posId;
	
	private String posDetail;
 
	public Pos() {}
	public Pos(Integer posId, String posDetail) {
		super();
		this.posId = posId;
		this.posDetail = posDetail;
	}

	public Integer getPosId() {
		return posId;
	}

	public void setPosId(Integer posId) {
		this.posId = posId;
	}

	public String getPosDetail() {
		return posDetail;
	}

	public void setPosDetail(String posDetail) {
		this.posDetail = posDetail;
	};
}
