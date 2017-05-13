package com.indutech.gnd.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="MASTER_DCMS_T")
public class MasterDcms {

	@Id
	@Column(name="DCMS_ID", nullable=false, unique=true)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long dcmsId;
	
	@Column(name="DCMS_NAME")
	private String dcmsName;

	public Long getDcmsId() {
		return dcmsId;
	}

	public void setDcmsId(Long dcmsId) {
		this.dcmsId = dcmsId;
	}

	public String getDcmsName() {
		return dcmsName;
	}

	public void setDcmsName(String dcmsName) {
		this.dcmsName = dcmsName;
	}
	
	
}
