package com.vrvsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vrvsecurity.model.User;
import com.vrvsecurity.requestbody.UserLoginRequest;
import com.vrvsecurity.requestbody.UserRequestBody;
import com.vrvsecurity.responsebody.UserProfileResponseBody;
import com.vrvsecurity.service.UserAuthorizeService;
import com.vrvsecurity.service.UserLoginService;
import com.vrvsecurity.service.UserLogoutService;
import com.vrvsecurity.service.UserProfileService;
import com.vrvsecurity.service.UserVaildatorService;

@RestController
@RequestMapping("/user")
public class SignupController {
	@Autowired
	UserVaildatorService userVaildatorService;
	@Autowired
	UserLoginService userLoginService;
	@Autowired
	UserAuthorizeService userAuthorizeService;

	@Autowired
	UserLogoutService userLogoutService;
	@PostMapping("/register")
	public ResponseEntity<?> registerNewUser(@RequestBody UserRequestBody userRequestBody) {

		String message = userVaildatorService.registerUser(userRequestBody);
		return ResponseEntity.ok(message);
	}

	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody UserLoginRequest userLoginRequest) {

		String message = userLoginService.authenticateTheUser(userLoginRequest);
		return ResponseEntity.ok(message);
	}

	@PostMapping("/profile")
	public ResponseEntity<?> userProfile(@RequestHeader("Authorization") String token) {
		UserProfileResponseBody userResponse = userAuthorizeService.tokenIsVaild(token);
		return ResponseEntity.ok(userResponse);

	}
	@PostMapping("/logout")
	public ResponseEntity<?> userLogout(@RequestHeader("Logout") String token) {
		 String mesage=userLogoutService.userLogout(token);
		return ResponseEntity.ok(mesage);

	}

}
