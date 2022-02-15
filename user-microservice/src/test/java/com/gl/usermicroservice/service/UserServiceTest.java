package com.gl.usermicroservice.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.gl.usermicroservice.dto.UserRequestDTO;
import com.gl.usermicroservice.exception.InputDataException;
import com.gl.usermicroservice.exception.UserAlreadyExistsException;
import com.gl.usermicroservice.model.User;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;
    
    @MockBean
    private ModelMapper modelMapper;
    
	@Autowired
	private UserServiceImpl userService;
    
    @Test(expected = UserAlreadyExistsException.class)
    public void user_already_exist_test() throws Exception {
    	
    	User user = new User();
    	user.setEmail("davidlynch@gmail.com");
    	
    	when(modelMapper.map(any(), any())).thenReturn(user);
    	when(userRepository.findByEmail(anyString())).thenReturn(new User());
    	
    	userService.createUser(new UserRequestDTO());
    }
    
    @Test(expected = InputDataException.class)
    public void email_mal_formed_test() throws Exception {
    	
    	User user = new User();
    	user.setEmail("davidlynch@");
    	
    	when(modelMapper.map(any(), any())).thenReturn(user);
    	
    	userService.createUser(new UserRequestDTO());
    }
    
    @Test(expected = InputDataException.class)
    public void password_mal_formed_test() throws Exception {
    	
    	User user = new User();
    	user.setEmail("davidlynch@gmail.com");
    	user.setPassword("1234");
    	
    	when(modelMapper.map(any(), any())).thenReturn(user);
    	when(userRepository.findByEmail(anyString())).thenReturn(null);
    	
    	userService.createUser(new UserRequestDTO());
    }

}
