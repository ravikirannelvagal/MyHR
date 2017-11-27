package com.MyHR.Exceptions;

public class InvalidApplicationUpdateStatusException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "Job Application is in invalid status";
	}
}
