package com.indutech.gnd.enumTypes;

public enum RecordStatus {
	
	PENDING("0"),
	REJECT("1"),
	HOLD("2"),
	APPROVED("3"),
	AWB_ASSIGNED("4"),
	AUF_CONVERTED("5"),
	MANUAL_HOLD("91"),
	MANUAL_REJECT("92");
	
	private final String status;	
	
	RecordStatus(String status) {
		this.status = status;
	}

	public String getRecordStatus()
	{
		return this.status;
	}

}
