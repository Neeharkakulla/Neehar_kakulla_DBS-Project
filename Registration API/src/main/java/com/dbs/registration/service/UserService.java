package com.dbs.registration.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.dbs.registration.entity.model.User;
import com.dbs.registration.entity.request.UserRequest;
import com.dbs.registration.entity.response.UserResponse;
import com.dbs.registration.repo.UserRepo;

@Service
public class UserService {

	private final UserRepo userRepo;
	private final BCryptPasswordEncoder passwordEncoder;

	public UserService(final UserRepo userRepo, final BCryptPasswordEncoder passwordEncoder) {
		this.userRepo = userRepo;
		this.passwordEncoder = passwordEncoder;
	}

	public UserResponse saveUser(UserRequest userRequest) {
		User user = populateUserData(userRequest);
		userRepo.save(user);
		return UserResponse.builder().message("Succesflly saved User").build();
	}

	private User populateUserData(UserRequest userRequest) {

		return User.builder().email(userRequest.getEmail()).password(passwordEncoder.encode(userRequest.getPassword()))
				.failedAttempts(0).isAccountLocked(false).username(userRequest.getUsername())
				.firstName(userRequest.getFirstName()).lastName(userRequest.getLastName()).build();
	}
}
