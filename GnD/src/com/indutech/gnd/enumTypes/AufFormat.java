package com.indutech.gnd.enumTypes;

public enum AufFormat {
	AUF_Format_One("1"),
	AUF_Format_TWO("2");
	
	private final String status;	
	
	AufFormat(String status) {
		this.status = status;
	
	}

	public String getFileStatus()
	{
		return this.status;
	}
}
