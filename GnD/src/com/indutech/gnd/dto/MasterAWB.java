package com.indutech.gnd.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="master_awb_t")
public class MasterAWB {
	@Id
	@Column(name="id", nullable=false, unique=true)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long awbId;
	@Column(name="AWB", nullable=false)
	private String awbName;
	@Column(name="service_provider_id", nullable=true)
	private Long serviceProviderId;
	@Column(name="status", nullable=true)
	private Long status;
	
	public final Long getAwbId() {
		return awbId;
	}
	public final void setAwbId(Long awbId) {
		this.awbId = awbId;
	}
	public final String getAwbName() {
		return awbName;
	}
	public final void setAwbName(String awbName) {
		this.awbName = awbName;
	}
	public final Long getServiceProviderId() {
		return serviceProviderId;
	}
	public final void setServiceProviderId(Long serviceProviderId) {
		this.serviceProviderId = serviceProviderId;
	}
	public final Long getStatus() {
		return status;
	}
	public final void setStatus(Long status) {
		this.status = status;
	}	

}
