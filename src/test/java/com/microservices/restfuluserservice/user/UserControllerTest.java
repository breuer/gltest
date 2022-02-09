package com.microservices.restfuluserservice.user;

import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.restfuluserservice.model.Phone;
import com.microservices.restfuluserservice.model.User;
import com.microservices.restfuluserservice.service.JwtUtil;
import com.microservices.restfuluserservice.service.UserRepository;
import com.microservices.restfuluserservice.service.UserService;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

	private static final ObjectMapper om = new ObjectMapper();
	
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private UserRepository mockRepository;
    
    @MockBean
    private UserService mockService;
    
    @MockBean
    private JwtUtil mockJwtUtilService;
    
    
    @Test
    public void sign_up_ok() throws Exception {
    	List<Phone> phones = new ArrayList<>();
    	phones.add(new Phone(1L, 12345678L, 261, "USA"));
    	
    	User user = new User("name", "email@gmail.com", "a2asfGfdfdf4", phones);

    	String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkYXZpZGx5bmNoMDFAZ21haWwuY29tIiwiZXhw"
    			+ "IjoxNjQ0Mzc1NDg5LCJpYXQiOjE2NDQzMzk0ODl9.EfhQjvPVkC3PktQ867p3DoVBdii-pYHLaiXLMJh9US4";
    	User savedUser = new User(1, "name", "email@gmail.com", 
    			"a2asfGfdfdf4", phones, new Date(), null, true, token);
    	
    	when(mockService.checkEmail(any(User.class))).thenReturn(true);
    	when(mockService.userExist(any(User.class))).thenReturn(false);
    	when(mockService.checkPassword(any(User.class))).thenReturn(true);
    	when(mockService.registerNewUserAccount(any(User.class))).thenReturn(savedUser);
    	 
    	mockMvc.perform(post("/sign-up")
                 .content(om.writeValueAsString(user))
                 .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                 .andExpect(status().isCreated())
                 .andExpect(jsonPath("$.id", is(1)))
                 .andExpect(jsonPath("$.name", is("name")))
                 .andExpect(jsonPath("$.email", is("email@gmail.com")))
                 .andExpect(jsonPath("$.password", is("a2asfGfdfdf4")))
                 .andExpect(jsonPath("$.isActive", is(true)))
                 ;
    }
        
    @Test
    public void sign_up_user_already_exists() throws Exception {
    	List<Phone> phones = new ArrayList<>();
    	phones.add(new Phone(1L, 12345678L, 261, "USA"));
    	User user = new User("name", "email@gmail.com", "a2asfGfdfdf4", phones);
    	
    	when(mockService.checkEmail(any(User.class))).thenReturn(true);
    	//when(mockService.checkPassword(any(User.class))).thenReturn(true);
    	when(mockService.userExist(any(User.class))).thenReturn(true);
    
    	mockMvc.perform(post("/sign-up")
                 .content(om.writeValueAsString(user))
                 .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                 .andExpect(status().isConflict())
                 .andExpect(jsonPath("$.code", is(409)))
                 .andExpect(jsonPath("$.detail", is("There is an account with that email address")));
    }

    @Test
    public void test_sign_up_user_malformed_email() throws Exception {
    	List<Phone> phones = new ArrayList<>();
    	phones.add(new Phone(1L, 12345678L, 261, "USA"));
    	User user = new User("name", "email@gmail", "a2asfGfdfdf4", phones);
    	
    	when(mockService.checkEmail(any(User.class))).thenReturn(false);
    
    	mockMvc.perform(post("/sign-up")
                 .content(om.writeValueAsString(user))
                 .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                 .andExpect(status().isNotAcceptable())
                 .andExpect(jsonPath("$.code", is(406)))
                 .andExpect(jsonPath("$.detail", is("Email does not comply with the correct "
                 		+ "format (aaaaaaa@onedomain.something)")));
    }

    @Test
    public void test_sign_up_user_malformed_password() throws Exception {
    	List<Phone> phones = new ArrayList<>();
    	phones.add(new Phone(1L, 12345678L, 261, "USA"));
    	User user = new User("name", "email@gmail.com", "a2asfGfdfdf4", phones);
    	
    	when(mockService.checkEmail(any(User.class))).thenReturn(true);
    	when(mockService.userExist(any(User.class))).thenReturn(false);
    	//when(mockService.checkPassword(any(User.class))).thenReturn(false);
    	
    	mockMvc.perform(post("/sign-up")
                 .content(om.writeValueAsString(user))
                 .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                 .andExpect(status().isNotAcceptable())
                 .andExpect(jsonPath("$.code", is(406)))
                 .andExpect(jsonPath("$.detail", is("The password must have 2 numbers and a capital letter "
                 		+ "and a minimum length of 6 and a maximum of 12 letters")));
    }

    
}
