package com.dbs.registration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.dbs.registration.entity.model.User;
import com.dbs.registration.exception.UserException;
import com.dbs.registration.repo.UserRepo;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepo userdao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userdao.findByUsername(username);

		UserDetailsImpl userDetails = null;

		if (user != null) {
			if (user.isAccountLocked() || user.getFailedAttempts() == 3) {
				user.setAccountLocked(true);
			} else {
				user.setAccountLocked(false);
				user.setFailedAttempts(0);
			}
			userDetails = new UserDetailsImpl();
			userDetails.setUser(user);
		} else {
			throw new UsernameNotFoundException(username + " user doesn't not exists");
		}

		return userDetails;

	}

}