package com.sendemail.sendemail.utils;

public class EmailUtils {

	public static String getEmailMessage(String name, String host, String token) {
		return "Hello " + name + ",\n\nYour new account has been created. Please click the link below to verity your account. \n\n" + 
	getVerificationUrl(host,token) + "\n\nThe support Team";
	}

	public static String getVerificationUrl(String host, String token) {
		
		return host + "/api/users?token=" + token;
	}
}
