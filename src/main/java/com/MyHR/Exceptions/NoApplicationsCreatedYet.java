package com.MyHR.Exceptions;

public class NoApplicationsCreatedYet extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public String getMessage() {
		return "No Applications created yet";
	}

}
