package com.cardinity.data.model;

public enum Status {
	OPEN("OPEN"), INPROGRESS("IN PROGRESS"), CLOSED("CLOSED");
	private String status;
	 
    Status(String status) {
        this.status = status;
    }
 
    public String getStatus() {
        return status;
    }
}
