package com.dbs.registration.controller.advice;

import static com.dbs.registration.constants.Constants.ERROR_DESC;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.dbs.registration.entity.response.UserResponse;
import com.dbs.registration.exception.UserException;

@ControllerAdvice
public class GlobalControllerAdvice {

	@ExceptionHandler
	public ResponseEntity<UserResponse> handleUserConsumedAllAttempts(UserException e) {
		return getErrorMessage(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	private ResponseEntity<UserResponse> getErrorMessage(String error, HttpStatus httpStatus) {

		return new ResponseEntity<UserResponse>(UserResponse.builder()
				.message(ERROR_DESC.getOrDefault(httpStatus, "APPLICATION INTERNAL SERVER ERROR")).error(error).build(),
				httpStatus);
	}

	@ExceptionHandler
	public ResponseEntity<UserResponse> handleException(Exception e) {
		return getErrorMessage(
				e.getMessage() != null && e.getMessage().contains("Duplicate") ? "User name already exists"
						: e.getMessage(),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
