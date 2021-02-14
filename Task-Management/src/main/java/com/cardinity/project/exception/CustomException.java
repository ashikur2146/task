package com.cardinity.project.exception;

public class CustomException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String message;
	
	public CustomException(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}
}