package com.savita.order.exception;

public class OrderNotFoundException extends RuntimeException{
	
	private String message;
	
	public OrderNotFoundException(String message)
	{
		super(message);
		this.message=message;
	}
	
	@Override
    public String getMessage() {
        return message;
    }

}
