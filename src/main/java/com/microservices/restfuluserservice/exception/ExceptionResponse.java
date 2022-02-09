package com.microservices.restfuluserservice.exception;

import java.util.Date;

public class ExceptionResponse {
	
	private Date timestamp;
	private Integer code;
	private String detail;

	public ExceptionResponse(Date timestamp, Integer code, String detail) {
		super();
		this.timestamp = timestamp;
		this.code = code;
		this.detail = detail;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public Integer getCode() {
		return code;
	}

	public String getDetail() {
		return detail;
	}
}
