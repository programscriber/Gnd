package com.indutech.gnd.bo;

public class StateBO {

	private Long id;
	private Long stateCode;
	private String stateName;
	private Long status;	
	
	public final Long getStatus() {
		return status;
	}
	public final void setStatus(Long status) {
		this.status = status;
	}
	public final Long getId() {
		return id;
	}
	public final void setId(Long id) {
		this.id = id;
	}
	public final Long getStateCode() {
		return stateCode;
	}
	public final void setStateCode(Long stateCode) {
		this.stateCode = stateCode;
	}
	public final String getStateName() {
		return stateName;
	}
	public final void setStateName(String stateName) {
		this.stateName = stateName;
	}	
	
}
