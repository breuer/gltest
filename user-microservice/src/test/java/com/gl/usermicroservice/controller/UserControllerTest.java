package com.gl.usermicroservice.controller;

import static org.mockito.Mockito.*;

import org.junit.Before;
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
import com.gl.usermicroservice.constants.Constants;
import com.gl.usermicroservice.dto.PhoneDTO;
import com.gl.usermicroservice.dto.UserCreatedDTO;
import com.gl.usermicroservice.dto.UserRequestDTO;
import com.gl.usermicroservice.exception.InputDataException;
import com.gl.usermicroservice.exception.UserAlreadyExistsException;
import com.gl.usermicroservice.service.JwtService;
import com.gl.usermicroservice.service.UserRepository;
import com.gl.usermicroservice.service.UserServiceImpl;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
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
    private UserServiceImpl mockService;
    
    @MockBean
    private JwtService mockJwtUtilService;
    

    private UserRequestDTO userRequest; 
    
    
    @Before
    public void init() {
    	List<PhoneDTO> phones = new ArrayList<>();
    	phones.add(new PhoneDTO(12345678L, 261, "USA"));
    	userRequest = new UserRequestDTO("name", "email@gmail.com", 
    			"a2asfGfdfdf4", phones);
    }
    
    @Test
    public void sign_up_ok() throws Exception {
    	List<PhoneDTO> phones = new ArrayList<>();
    	String token = "12245";
    	UserCreatedDTO userCreated = new UserCreatedDTO("1", "name", "email@gmail.com", 
    			"a2asfGfdfdf4", phones, token, true);
    	
    	when(mockService.createUser(any(UserRequestDTO.class))).thenReturn(userCreated);
    	 
    	mockMvc.perform(post("/sign-up")
                 .content(om.writeValueAsString(userRequest))
                 .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                 .andExpect(status().isCreated())
                 //.andExpect(jsonPath("$.id", is(1)))
                 .andExpect(jsonPath("$.name", is("name")))
                 .andExpect(jsonPath("$.email", is("email@gmail.com")))
                 .andExpect(jsonPath("$.password", is("a2asfGfdfdf4")))
                 .andExpect(jsonPath("$.active", is(true)));
    }
        
    @Test
    public void sign_up_user_already_exists() throws Exception {
    
    	when(mockService.createUser(any(UserRequestDTO.class)))
    		.thenThrow(new UserAlreadyExistsException(Constants.USER_ALREADY_EXISTS));
    	
    	mockMvc.perform(post("/sign-up")
                 .content(om.writeValueAsString(userRequest))
                 .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                 .andExpect(status().isConflict())
                 .andExpect(jsonPath("$.code", is(409)))
                 .andExpect(jsonPath("$.detail", is("There is an account with that email address")));
    }

    @Test
    public void test_sign_up_user_malformed_email() throws Exception {
    	
    	when(mockService.createUser(any(UserRequestDTO.class)))
		.thenThrow(new InputDataException(Constants.EMAIL_MALFORMED));
    
    	mockMvc.perform(post("/sign-up")
                 .content(om.writeValueAsString(userRequest))
                 .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                 .andExpect(status().isNotAcceptable())
                 .andExpect(jsonPath("$.code", is(406)))
                 .andExpect(jsonPath("$.detail", is("Email does not comply with the correct format")));
    }

    @Test
    public void test_sign_up_user_malformed_password() throws Exception {
    	
    	when(mockService.createUser(any(UserRequestDTO.class)))
		.thenThrow(new InputDataException(Constants.PASSWORD_MALFORMED));
    	
    	mockMvc.perform(post("/sign-up")
                 .content(om.writeValueAsString(userRequest))
                 .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                 .andExpect(status().isNotAcceptable())
                 .andExpect(jsonPath("$.code", is(406)))
                 .andExpect(jsonPath("$.detail", is("Password does not comply with the correct format")));
    }

    
}
