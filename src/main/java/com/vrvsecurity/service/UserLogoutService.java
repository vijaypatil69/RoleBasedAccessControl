package com.vrvsecurity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vrvsecurity.utility.JwtUtil;

@Service
public class UserLogoutService {
	@Autowired
	JwtUtil jwtUtil;

	public String userLogout(String token) {

		boolean logoutStatus = jwtUtil.removeJwtFromMap(token);
		if (logoutStatus) {
			return "User Logout Sucessfully";
		} else {
			return "Something getting wrong";
		}
	}
}
