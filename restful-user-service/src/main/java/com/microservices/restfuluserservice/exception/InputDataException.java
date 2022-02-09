package com.microservices.restfuluserservice.exception;

public class InputDataException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InputDataException(String msg) {
		super(msg);
	}

}
