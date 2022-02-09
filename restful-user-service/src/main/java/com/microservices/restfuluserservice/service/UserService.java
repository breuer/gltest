package com.microservices.restfuluserservice.service;

import java.util.Date;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.microservices.restfuluserservice.model.User;

@Service
public class UserService {

	@Autowired
    private UserRepository repository;
	
    @Autowired
    private JwtUtil jwtUtiService;
	
    @Value("${regex.email}")
    private String regexEmail;
    
    @Value("${regex.password}")
    private String regexPassword;
    
    //@Autowired
    //private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public User registerNewUserAccount(User user) {
		user.setCreated(new Date());
		String token = jwtUtiService.generateToken(user.getEmail());
		//BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		//user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setToken(token);
		//user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		 //int strength = 10; // work factor of bcrypt
		 //BCryptPasswordEncoder bCryptPasswordEncoder =
		 //new BCryptPasswordEncoder(strength, new SecureRandom());
		 //String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
		 //user.setPassword(encodedPassword);
		return repository.save(user);
    }
    
    public boolean userExist(User user) {
    	return repository.findByEmail(user.getEmail()) != null;
    }
    
    public Boolean checkEmail(User user) {
    	return Pattern.matches(regexEmail, user.getEmail());
    }
    
    public Boolean checkPassword(User user) {
		return 
				user.getPassword().length() >= 8 &&
				user.getPassword().length() <= 12 &&
				Pattern.matches(regexPassword, user.getPassword());
    }
    
}
