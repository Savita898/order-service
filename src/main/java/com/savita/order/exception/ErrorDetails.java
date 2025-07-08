package com.savita.order.exception;

import java.util.Date;

public class ErrorDetails {
	private String message;
	private Date timestamp;
	
	ErrorDetails(String message,Date timestamp)
	{
		this.message=message;
		this.timestamp=timestamp;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	

}
