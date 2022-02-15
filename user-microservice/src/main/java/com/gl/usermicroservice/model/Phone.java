package com.gl.usermicroservice.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Phone {
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;
	private Long number;
	private Integer citycode;
	private String contrycode;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private User user;
	
	public Phone() {
		
	}
	
	public Phone(Long id, Long number, Integer citycode, String contrycode) {
		super();
		this.id = id;
		this.number = number;
		this.citycode = citycode;
		this.contrycode = contrycode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public Long getNumber() {
		return number;
	}
	public void setNumber(Long number) {
		this.number = number;
	}
	public Integer getCitycode() {
		return citycode;
	}
	public void setCitycode(Integer citycode) {
		this.citycode = citycode;
	}
	public String getContrycode() {
		return contrycode;
	}
	public void setContrycode(String contrycode) {
		this.contrycode = contrycode;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
