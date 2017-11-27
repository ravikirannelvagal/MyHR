package com.MyHR.Exceptions;

public class InvalidApplicationIdException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public String getMessage() {
		return "Invalid job application id";
	}

}
