package com.indutech.gnd.enumTypes;

public enum Status {
	
	PENDING_FOR_APPROVAL("0"),
	ACTIVE("1"),
	INACTIVE("2"),
	BLOCKED("3");
	
	private final  String status;	

	
	Status(String status) {
		this.status = status;
		
	}

	public String getStatus()
	{
		return this.status;
	}
	
	
}
