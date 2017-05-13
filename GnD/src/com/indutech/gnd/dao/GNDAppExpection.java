package com.indutech.gnd.dao;

@SuppressWarnings("serial")
public class GNDAppExpection extends Exception {

	private String message = null;

	public GNDAppExpection() {
		super();

	}

	public GNDAppExpection(String message) {
		super(message);
		this.message = message;

	}

	public GNDAppExpection(Throwable cause) {
		super(cause);

	}

	@Override
	public String toString() {

		return message;

	}

	@Override
	public String getMessage() {

		return message;

	}
}
