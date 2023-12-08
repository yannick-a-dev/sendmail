package com.sendemail.sendemail.service;

import com.sendemail.sendemail.entities.User;

public interface UserService {

	User saveUser(User user);
	Boolean verifyToken(String token);
}
