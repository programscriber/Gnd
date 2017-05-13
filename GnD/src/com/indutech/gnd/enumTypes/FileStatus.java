package com.indutech.gnd.enumTypes;

public enum FileStatus {
	
	RECEIVED("0"),
	REJECT("1"),
	HOLD("2"),
	APPROVED("3"),
	AUF_CONVERTED("4"),
	MAPPED("5"),
	VIP_RECEIVED("6"),
	PGP_RECEIVED("7");
	private final String status;	
	
	FileStatus(String status) {
		this.status = status;
	}

	public String getFileStatus()
	{
		return this.status;
	}

}
