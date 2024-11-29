package com.vrvsecurity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vrvsecurity.model.User;
import com.vrvsecurity.responsebody.UserProfileResponseBody;
import com.vrvsecurity.utility.JwtUtil;

import io.jsonwebtoken.JwtException;

@Service
public class UserAuthorizeService {
	@Autowired
	JwtUtil jwtUtil;
	@Autowired
	UserProfileService userProfileService;

	public UserProfileResponseBody tokenIsVaild(String token) {
		// token = token.substring(7);

		try {

			String sceretKey = jwtUtil.jwtIsVaild(token);

			String emailId = jwtUtil.extractEmailFromToken(token, sceretKey);

			User user = userProfileService.getUserProfile(emailId);

			return userProfileService.userProfile(user);

		} catch (JwtException ex) {
			throw ex;
		} catch (IllegalArgumentException ex) {
			throw ex;
		}
	}

}
