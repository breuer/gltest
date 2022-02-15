package com.gl.usermicroservice.constants;

public class Constants {

	public static final String EMAIL_MALFORMED = "Email does not comply with the correct format";
	public static final String PASSWORD_MALFORMED = "Password does not comply with the correct format";
	public static final String REGEX_EMAIL = "^(.+)@(\\S+)$";
	public static final String REGEX_PASSWORD = "(?=(?:\\D*\\d){2}\\D*$)(?:[^A-Z]*[A-Z]){1}[^A-Z]*$";
	public static final String USER_ALREADY_EXISTS = "There is an account with that email address";
}
