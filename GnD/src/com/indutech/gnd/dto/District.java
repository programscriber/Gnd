package com.indutech.gnd.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="master_district_t")
public class District {
	
	@Id
	@Column(name="id", nullable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@Column(name="district_id")
	private Long districtCode;
	@Column(name="district_name")
	private String districtName;
	@Column(name="state_id")
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
