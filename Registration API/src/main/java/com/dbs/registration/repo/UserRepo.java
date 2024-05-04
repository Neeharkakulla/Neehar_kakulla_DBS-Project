package com.dbs.registration.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dbs.registration.entity.model.User;

public interface UserRepo extends JpaRepository<User, Long> {

	User findByUsername(String username);

}
