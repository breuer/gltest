package com.microservices.restfuluserservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.restfuluserservice.exception.InputDataException;
import com.microservices.restfuluserservice.exception.UserAlreadyExistsException;
import com.microservices.restfuluserservice.model.User;
import com.microservices.restfuluserservice.service.UserService;


@RestController 
public class UserController {
	
	@Autowired
	private UserService service;
	
	@Value("${message.email_malformed}")
	private String msgEmailMalformed;
	@Value("${message.password_malformed}")
	private String msgPasswordMalformed;
	@Value("${message.user_alreade_exists}")
	private String msgUserAlreadyExists;
		
	@PostMapping("/sign-up")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		if(!service.checkEmail(user)) 
			throw new InputDataException(msgEmailMalformed);
		if(service.userExist(user)) 
			throw new UserAlreadyExistsException(msgUserAlreadyExists);
		if(!service.checkPassword(user))
			throw new InputDataException(msgPasswordMalformed);
		
		User savedUser = service.registerNewUserAccount(user);

		return new ResponseEntity<User>(savedUser, null, HttpStatus.CREATED);
	}
}
