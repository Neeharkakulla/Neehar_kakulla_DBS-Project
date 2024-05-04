package com.dbs.registration.exception;

public sealed class BaseException extends Exception permits UserException {

	public BaseException(String message) {
		super(message);
	}

}
