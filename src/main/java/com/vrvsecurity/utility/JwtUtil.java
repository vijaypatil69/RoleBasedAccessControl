package com.vrvsecurity.utility;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

@Component
public class JwtUtil {
	@Autowired
	@Lazy
	JwtUtil jwtUtil;
	private final String secret_key = "3cfa76ef14937c1c0ea519f8fc057a80fcd04a7420f8e8bcd0a7567c272e007b";
	private final Map<String, String> jwtMap = new HashMap<>();

	final long TOKEN_VALIDITY = 8 * 60 * 60 * 1000;

	public String generateToken(String emailId, String role, Key key) {
	
		return Jwts.builder().setSubject(emailId).claim("role", role).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY)) // Token validity of 8 hours
				.signWith(key).compact();
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
	}

	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secret_key);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String getJwtTokenMap(String emailID, String role) {
		Key key = getSignInKey();
		String jwtToken = jwtUtil.generateToken(emailID, role, key);
		jwtMap.put(jwtToken, key + "");
		return jwtToken;

	}

	public String jwtIsVaild(String token) {
		if (jwtMap.containsKey(token)) {
			return jwtMap.get(token);
		} else {
			throw new IllegalArgumentException("Unauthorize User : Invalid Jwt Token");

		}
	}

	public boolean removeJwtFromMap(String token) {
		if (jwtMap.containsKey(token)) {
			jwtMap.remove(token);
			return true;
		} else {
			return false;

		}
	}

	public String extractEmailFromToken(String token, String secretKey) {

		try {

			return jwtUtil.extractAllClaims(token).getSubject();

		} catch (ExpiredJwtException e) {
			throw new RuntimeException("JWT token has expired.");
		} catch (UnsupportedJwtException e) {
			throw new RuntimeException("JWT token is unsupported.");
		} catch (MalformedJwtException e) {
			throw new RuntimeException("JWT token is malformed.");
		} catch (SignatureException e) {
			throw new RuntimeException("Invalid JWT signature.");
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("JWT token is empty or invalid.");
		}

	}

}