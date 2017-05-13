package com.indutech.gnd.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="MASTER_COURIER_SERVICE_T")
public class MasterCourierService {	
		
	@Id
	@Column(name="ID", nullable=false, unique=true)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name="SERVICE_PROVIDER_NAME")
	private String serviceProviderName;
	@Column(name="CONTACT_PERSON_NAME")
	private String contactPersionName;
	@Column(name="PHONE")
	private String phone;
	@Column(name="EMAIL")
	private String email;
	public final Long getId() {
		return id;
	}
	public final void setId(Long id) {
		this.id = id;
	}
	public final String getServiceProviderName() {
		return serviceProviderName;
	}
	public final void setServiceProviderName(String serviceProviderName) {
		this.serviceProviderName = serviceProviderName;
	}
	public final String getContactPersionName() {
		return contactPersionName;
	}
	public final void setContactPersionName(String contactPersionName) {
		this.contactPersionName = contactPersionName;
	}
	public final String getPhone() {
		return phone;
	}
	public final void setPhone(String phone) {
		this.phone = phone;
	}
	public final String getEmail() {
		return email;
	}
	public final void setEmail(String email) {
		this.email = email;
	}
}	
		

	



