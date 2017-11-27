package com.MyHR.Exceptions;

public class InvalidEmailIdPatternException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Override
	public String getMessage() {
		return "The entered email id is not a valid email pattern. Please enter email id in a valid pattern";
	}
}
