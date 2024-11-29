package com.vrvsecurity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vrvsecurity.enumfileds.Gender;
import com.vrvsecurity.enumfileds.Role;
import com.vrvsecurity.model.User;
import com.vrvsecurity.repo.UserRepo;
import com.vrvsecurity.requestbody.UserRequestBody;
import com.vrvsecurity.utility.ValidationUtil;

@Service
public class UserRegisterService {
	@Autowired
	UserRepo userRepo;
	User user = new User();
	

	public void userEntityStore(UserRequestBody userRequestBody) {
    	 
    	 user.setFirstName(userRequestBody.getFirstName());
    	 user.setLastName(userRequestBody.getLastName());
    	 
    	 user.setEmailId(userRequestBody.getEmailId());
    	 user.setMobileNumber(userRequestBody.getMobileNumber());
    	 
    	 user.setPassword(ValidationUtil.hashPassword(userRequestBody.getPassword()));
    	 user.setGender(userRequestBody.getGender());
    	 user.setRole(userRequestBody.getRole());
    	 
    	// System.out.println(Role.roleFromString(userRequestBody.getRole()));
    	 // System.out.println(Gender.genderFromShortCode(userRequestBody.getGender()));
    	
    	 userRepo.insertUser(user.getFirstName(), 
    			 user.getLastName(),
    			 user.getEmailId(), 
    			 user.getMobileNumber(),
    			 user.getPassword(),
    			 user.getGender(),
    			 user.getRole());
    			
    			
    			
     }
}
