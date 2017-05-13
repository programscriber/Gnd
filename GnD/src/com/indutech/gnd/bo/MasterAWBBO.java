package com.indutech.gnd.bo;

public class MasterAWBBO {
	
	private Long awbId;
	private String awbName;
	private Long serviceProviderId;
	private Long status;
	private String serviceProviderName;
	private String statusString;
	
	public String getStatusString() {
		return statusString;
	}
	public void setStatusString(String statusString) {
		this.statusString = statusString;
	}
	public String getServiceProviderName() {
		return serviceProviderName;
	}
	public void setServiceProviderName(String serviceProviderName) {
		this.serviceProviderName = serviceProviderName;
	}
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
