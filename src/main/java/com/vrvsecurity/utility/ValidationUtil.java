package com.vrvsecurity.utility;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.bouncycastle.crypto.generators.Argon2BytesGenerator;
import org.bouncycastle.crypto.params.Argon2Parameters;

import com.vrvsecurity.exception.ValidationException;

public class ValidationUtil {

	private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
	private static final String MOBILE_REGEX = "^[0-9]{10}$";
	private static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@$!%*?&]{8,}$";

	public static void validateEmail(String email) {
		if (email == null || !email.matches(EMAIL_REGEX)) {
			throw new ValidationException("Invalid email format. Please provide a valid email address.");
		}
	}

	public static void validateMobileNumber(String mobileNumber) {
		if (mobileNumber == null || !mobileNumber.matches(MOBILE_REGEX)) {
			throw new ValidationException("Invalid mobile number. It should contain exactly 10 digits.");
		}
	}

	public static void validatePassword(String password) {
		if (password == null || !password.matches(PASSWORD_REGEX)) {
			throw new ValidationException(
					"Invalid password. Password must be at least 8 characters long, include one letter, and one digit.");
		}
	}

	public static void validateName(String name, String fieldName) {
		if (name == null || name.trim().isEmpty()) {
			throw new ValidationException(fieldName + " cannot be empty.");
		}
	}

	public static String hashPassword(String plainPassword) {
		Argon2BytesGenerator generator = new Argon2BytesGenerator();
		byte[] passwordBytes = plainPassword.getBytes(StandardCharsets.UTF_8);
		byte[] salt = new byte[16]; 
		byte[] hash = new byte[32]; 

		Argon2Parameters.Builder builder = new Argon2Parameters.Builder();
		builder.withIterations(3).withMemoryAsKB(16 * 1024).withParallelism(4).withSalt(salt);
		Argon2Parameters parameters = builder.build();

		generator.init(parameters);
		generator.generateBytes(passwordBytes, hash);

		return Base64.getEncoder().encodeToString(salt) + ":" + Base64.getEncoder().encodeToString(hash);
	}
}
