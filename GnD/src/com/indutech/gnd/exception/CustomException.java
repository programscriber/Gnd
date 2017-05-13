package com.indutech.gnd.exception;

public class CustomException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4004526682538892763L;
	private String msg;
	
	
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public CustomException(String msg){
		this.msg = msg;
	}
	
}
