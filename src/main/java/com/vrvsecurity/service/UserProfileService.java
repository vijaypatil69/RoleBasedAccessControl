package com.vrvsecurity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vrvsecurity.enumfileds.Gender;
import com.vrvsecurity.enumfileds.Role;
import com.vrvsecurity.model.User;
import com.vrvsecurity.repo.UserRepo;
import com.vrvsecurity.responsebody.UserProfileResponseBody;

@Service
public class UserProfileService {
	@Autowired
	UserRepo userRepo;
     @Autowired
     UserProfileResponseBody userProfileResponseBody;
	public User getUserProfile(String emailId) {

		User user = userRepo.findByEmailId(emailId);
		return user;
	}
    public UserProfileResponseBody userProfile(User user) {
    	userProfileResponseBody.setFirstName(user.getFirstName());
    	userProfileResponseBody.setLastName(user.getLastName());
    	userProfileResponseBody.setEmailId(user.getEmailId());
    	userProfileResponseBody.setMobileNumber(user.getMobileNumber());
    	userProfileResponseBody.setRole(Role.getByValue(user.getRole()));
    	userProfileResponseBody.setGender(Gender.getByValue(user.getGender()));
    	return userProfileResponseBody;
    }
}
