package com.dbs.registration.utils;

import static com.dbs.registration.constants.Constants.EMAIL_PATTERN;

import java.util.Objects;
import java.util.regex.Matcher;

public class CommonUtils {

	public static boolean isPasswordValid(String password) {
		if (Objects.isNull(password) || password.length()<8)
			return false;
		char charArray[]=password.toCharArray();
		boolean hasDigit=false;
		boolean hasUpper=false;
		boolean hasLower=false;
		boolean hasSpecialChar=false;
		for(Character ch:charArray) {
			if(hasDigit&&hasLower&&hasSpecialChar&&hasUpper)
				return true;
			if(Character.isUpperCase(ch))
				hasUpper=true;
			else if(Character.isLowerCase(ch))
				hasLower=true;
			else if(Character.isDigit(ch))
				hasDigit=true;
			else if(isSpecialCharacter(ch))
				hasSpecialChar=true;
		}
		return false;
	}
	
	private static boolean isSpecialCharacter(char c) {
        String specialCharacters = "!@#$%&*()-=][|';:/?.><,\\\"";
        return specialCharacters.indexOf(c) != -1;
    }

	public static boolean isEmailValid(String email) {
		if (Objects.isNull(email))
			return false;
		Matcher matcher = EMAIL_PATTERN.matcher(email);
		return matcher.matches();
	}

}
