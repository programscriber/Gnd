package com.indutech.gnd.bo;

public class DistrictBO {
	
	private Long id;
	private Long districtCode;
	private String districtName;
	private Long stateId;
	
	public final Long getStateId() {
		return stateId;
	}
	public final void setStateId(Long stateId) {
		this.stateId = stateId;
	}
	public final Long getId() {
		return id;
	}
	public final void setId(Long id) {
		this.id = id;
	}
	public final Long getDistrictCode() {
		return districtCode;
	}
	public final void setDistrictCode(Long districtCode) {
		this.districtCode = districtCode;
	}
	public final String getDistrictName() {
		return districtName;
	}
	public final void setDistrictName(String districtName) {
		this.districtName = districtName;
	}	

}
