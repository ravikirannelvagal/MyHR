package com.MyHR.Exceptions;

public class JobTitleAlreadyExistsException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "The job title already exists";
	}
}
