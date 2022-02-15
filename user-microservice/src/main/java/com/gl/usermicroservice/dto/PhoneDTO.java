package com.gl.usermicroservice.dto;

public class PhoneDTO {
	
	private Long number;
	private Integer citycode;
	private String contrycode;
	
	public PhoneDTO() {
		
	}
	
	public PhoneDTO(Long number, Integer citycode, String contrycode) {
		super();
		this.number = number;
		this.citycode = citycode;
		this.contrycode = contrycode;
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
}
