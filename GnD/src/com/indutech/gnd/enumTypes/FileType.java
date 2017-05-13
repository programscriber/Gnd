package com.indutech.gnd.enumTypes;

public enum FileType {
	
	CORE("1"),
	AUF("2"),
	EMBOSS("3"),
	VIP("4");
	
	private final String fileType;
	
	FileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileType()
	{
		return this.fileType;
	}
	

}
