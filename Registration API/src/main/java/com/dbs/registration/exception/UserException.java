package com.dbs.registration.exception;

import org.springframework.http.HttpStatus;

import static com.dbs.registration.constants.Constants.ERROR_DESC;;

public final class UserException extends BaseException {

	public UserException(String message) {
		super(message);
	}

	public static void userLocked(HttpStatus status) throws UserException {
		throw new UserException(ERROR_DESC.get(status));
	}
	
	public static void userRequestInvalid(String message) throws UserException{
		throw new UserException(message);
	}

}
