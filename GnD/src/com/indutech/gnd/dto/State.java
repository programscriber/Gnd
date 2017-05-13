package com.indutech.gnd.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="master_state_t")
public class State {
	
	@Id
	@Column(name="id", nullable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@Column(name="state_id",unique=true)
	private Long stateCode;
	@Column(name="state_name")
	private String stateName;
	@Column(name="status")
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
