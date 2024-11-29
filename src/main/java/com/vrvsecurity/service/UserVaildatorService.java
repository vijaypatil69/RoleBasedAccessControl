package com.vrvsecurity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vrvsecurity.exception.ValidationException;
import com.vrvsecurity.model.User;
import com.vrvsecurity.repo.UserRepo;
import com.vrvsecurity.requestbody.UserRequestBody;
import com.vrvsecurity.utility.ValidationUtil;

@Service
public class UserVaildatorService {

	@Autowired
	UserRepo userRepo;
	@Autowired
	UserRegisterService userService;

	public String registerUser(UserRequestBody userRequestBody) {
		try {
			// Validate fields
			ValidationUtil.validateName(userRequestBody.getFirstName(), "First Name");
			ValidationUtil.validateName(userRequestBody.getLastName(), "Last Name");
			ValidationUtil.validateEmail(userRequestBody.getEmailId());
			ValidationUtil.validateMobileNumber(userRequestBody.getMobileNumber());
			ValidationUtil.validatePassword(userRequestBody.getPassword());

			User email = userRepo.findByEmailId(userRequestBody.getEmailId());
			if (email == null) {
				
				userService.userEntityStore(userRequestBody);
				return "User registered successfully!";
			} else {
				throw new IllegalArgumentException("This email is already exists!");
			}
		} catch (ValidationException ex) {
			throw ex;
		} catch (IllegalArgumentException ex) {
			throw ex;
		}
	}
}
