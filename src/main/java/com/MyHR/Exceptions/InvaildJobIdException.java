package com.MyHR.Exceptions;

public class InvaildJobIdException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "Invalid job id. please provide a valid job id";
	}
}
