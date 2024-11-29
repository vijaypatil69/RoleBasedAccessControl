package com.vrvsecurity.responsebody;

import org.springframework.stereotype.Component;

@Component
public class JwtResponseBody {
	
	private String jwtToken;
	private String secretKey;

	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public JwtResponseBody(String jwtToken, String secretKey) {
		super();
		this.jwtToken = jwtToken;
		this.secretKey = secretKey;
	}

	public JwtResponseBody() {
		super();
		// TODO Auto-generated constructor stub
	}

}
