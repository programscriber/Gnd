package com.indutech.gnd.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="MASTER_TYPE_T")
public class MasterType {
	
	@Id
	@Column(name="TYPE_ID", nullable=false, unique=true)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long typeId;
	
	@Column(name = "TYPE_DESC")
	private String typeDescription;
	
	public Long getTypeId() {
		return typeId;
	}
	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}
	public String getTypeDescription() {
		return typeDescription;
	}
	public void setTypeDescription(String typeDescription) {
		this.typeDescription = typeDescription;
	}

}
