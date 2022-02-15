package com.gl.usermicroservice.dto;

import java.util.List;

public class UserCreatedDTO {

	private String id;
    private String name;
    private String email;
    private String password;
    private List<PhoneDTO> phones;
	//private LocalDateTime created;
	//private LocalDateTime lastLogin;
	private String token;
	private boolean isActive;
	
	public UserCreatedDTO() {
		
	}
	
	public UserCreatedDTO(String id, String name, String email, String password, 
			List<PhoneDTO> phones, String token, boolean isActive) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password; 
		this.phones = phones;
		this.token = token;
		this.isActive = isActive;
	}
	public String getId() {
		return id;
	}
	public void setId(String idUser) {
		this.id = idUser;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<PhoneDTO> getPhones() {
		return phones;
	}
	public void setPhones(List<PhoneDTO> phones) {
		this.phones = phones;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	
}
