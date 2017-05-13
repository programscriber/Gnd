package com.indutech.gnd.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "master_network_t")
public class Network {

	@Id
	@Column(name = "network_id", nullable = false, unique = true)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long networkId;
	@Column(name = "network_name")
	private String networkName;

	public Long getNetworkId() {
		return networkId;
	}

	public void setNetworkId(Long networkId) {
		this.networkId = networkId;
	}

	public String getNetworkName() {
		return networkName;
	}

	public void setNetworkName(String networkName) {
		this.networkName = networkName;
	}

}
