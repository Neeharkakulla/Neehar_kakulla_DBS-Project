package com.dbs.registration.constants;

import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.http.HttpStatus;

public class Constants {

	public static final Map<HttpStatus, String> ERROR_DESC;

	private static final String EMAIL_REGULAR_EXPRESSION = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
	public static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGULAR_EXPRESSION);
	
	static {
		ERROR_DESC = Map.of(HttpStatus.INTERNAL_SERVER_ERROR, "APPLICATION INTERNAL_SERVER_ERROR", HttpStatus.LOCKED,
				"USER IS LOCKED");
	}

}
