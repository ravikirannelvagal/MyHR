package com.MyHR.Exceptions;

public class NoJobOffersCreatedYet extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "No Job offers created yet";
	}
}
