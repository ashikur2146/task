package com.cardinity.data.security;

import java.io.Serializable;

public class JWTTokenResponse implements Serializable {

	private static final long serialVersionUID = -661129926814379419L;
	private final String token;
	
	public JWTTokenResponse(String token) {
		super();
		this.token = token;
	}
	
	public String getToken() {
		return token;
	}
}