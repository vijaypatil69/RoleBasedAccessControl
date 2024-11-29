package com.vrvsecurity.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.jsonwebtoken.JwtException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<Map<String, String>> handleValidationException(ValidationException ex) {
		Map<String, String> response = new HashMap<>();
		response.put("error", "Validation Failed");
		response.put("message", ex.getMessage());
		return ResponseEntity.badRequest().body(response);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
		Map<String, String> response = new HashMap<>();
		response.put("error", "Illegal Argument");
		response.put("message", ex.getMessage());
		return ResponseEntity.badRequest().body(response);
	}
	@ExceptionHandler(JwtException.class)
	public ResponseEntity<Map<String, String>> handleGlobalJwtException(JwtException ex) {
		Map<String, String> response = new HashMap<>();
		response.put("error", "Jwt Token Error");
		response.put("message", ex.getMessage());
		return ResponseEntity.status(401).body(response);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, String>> handleGlobalException(Exception ex) {
		Map<String, String> response = new HashMap<>();
		response.put("error", "Internal Server Error");
		response.put("message", ex.getMessage());
		return ResponseEntity.status(500).body(response);
	}

	
}
