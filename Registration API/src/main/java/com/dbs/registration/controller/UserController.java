package com.dbs.registration.controller;

import static com.dbs.registration.utils.CommonUtils.isEmailValid;
import static com.dbs.registration.utils.CommonUtils.isPasswordValid;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dbs.registration.entity.request.UserRequest;
import com.dbs.registration.entity.response.UserResponse;
import com.dbs.registration.exception.UserException;
import com.dbs.registration.service.UserDetailsImpl;
import com.dbs.registration.service.UserService;;

@RestController
@RequestMapping("/api/v1/dbs")
@CrossOrigin("http://localhost:4200/")
public class UserController {

	@Autowired
	UserService userService;

	@PostMapping("/registration")
	public ResponseEntity<UserResponse> registerUser(@RequestBody final UserRequest userRequest) throws UserException {

		validateUserRequest(userRequest);
		return new ResponseEntity<UserResponse>(userService.saveUser(userRequest), HttpStatus.CREATED);
	}

	private void validateUserRequest(UserRequest userRequest) throws UserException {
		if (Objects.isNull(userRequest))
			UserException.userRequestInvalid("User request cannot be null");
		if (Objects.isNull(userRequest.getEmail()) || Objects.isNull(userRequest.getPassword())
				|| Objects.isNull(userRequest.getUsername()))
			UserException.userRequestInvalid("User request mandatory fields Email/Username/Password are missing");
		if (!isEmailValid(userRequest.getEmail()))
			UserException.userRequestInvalid("User email is Invaid");
		if (!isPasswordValid(userRequest.getPassword()))
			UserException.userRequestInvalid(
					"User password is Invaid(should be conatain Minimum eight characters, at least one letter, one number and one special character ");
	}

	@GetMapping("/home")
	public ResponseEntity<UserRequest> home(UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {

		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) usernamePasswordAuthenticationToken.getPrincipal();

		return new ResponseEntity<UserRequest>(UserRequest.builder().username(userDetailsImpl.getUsername()).build(),
				HttpStatus.OK);
	}

}
