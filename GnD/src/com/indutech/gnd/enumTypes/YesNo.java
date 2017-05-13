package com.indutech.gnd.enumTypes;

public enum YesNo {
	No("0"),
	Yes("1");
	
	private String yesNo;
	
	private YesNo(String yesNo) {
		this.yesNo=yesNo;
	}
	
	public String getValueYesNO(){
		return yesNo;
	}
	
}
