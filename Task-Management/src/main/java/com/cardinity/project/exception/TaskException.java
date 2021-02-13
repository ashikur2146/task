package com.cardinity.project.exception;

public class TaskException extends Exception {

	private static final long serialVersionUID = 1L;
	private String message;
	
	public TaskException(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}
}