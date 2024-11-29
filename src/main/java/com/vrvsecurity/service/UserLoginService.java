package com.vrvsecurity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vrvsecurity.enumfileds.Role;
import com.vrvsecurity.exception.ValidationException;
import com.vrvsecurity.model.User;
import com.vrvsecurity.repo.UserRepo;
import com.vrvsecurity.requestbody.UserLoginRequest;
import com.vrvsecurity.utility.JwtUtil;
import com.vrvsecurity.utility.ValidationUtil;

@Service
public class UserLoginService {
	@Autowired
	UserRepo userRepo;
	@Autowired
	JwtUtil jwtUtil;

	public String authenticateTheUser(UserLoginRequest userLoginRequest) {
		try {
			ValidationUtil.validateEmail(userLoginRequest.getEmailId());
			ValidationUtil.validatePassword(userLoginRequest.getPassword());

		} catch (ValidationException ex) {
			throw ex;
		}
		try {
			User user = userRepo.findByEmailId(userLoginRequest.getEmailId());
			if (user != null) {
				String hashPassword = ValidationUtil.hashPassword(userLoginRequest.getPassword());
				
				String email = userRepo.checkUserPresetOrNot(userLoginRequest.getEmailId(), hashPassword);
				
				if (email != null) {
					return jwtUtil.getJwtTokenMap(user.getEmailId(), Role.getByValue(user.getRole()));
					
				} else {
					throw new IllegalArgumentException("Password is incorrect");
				}
			} else {
				throw new IllegalArgumentException("This Account is not exits!");
			}
		} catch (IllegalArgumentException ex) {
			throw ex;
		}
	}

}
