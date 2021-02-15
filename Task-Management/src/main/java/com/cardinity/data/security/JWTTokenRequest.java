package com.cardinity.data.security;

public class JWTTokenRequest {
	
	private String username;
	private String password;

	public JWTTokenRequest() {
		super();
	}

	public JWTTokenRequest(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
}
