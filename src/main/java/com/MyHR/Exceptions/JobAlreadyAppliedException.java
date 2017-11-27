package com.MyHR.Exceptions;

public class JobAlreadyAppliedException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		
		return "You have already applied to the job";
	}
}
