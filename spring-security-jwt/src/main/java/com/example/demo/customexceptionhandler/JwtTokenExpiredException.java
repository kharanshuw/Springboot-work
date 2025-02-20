package com.example.demo.customexceptionhandler;


public class JwtTokenExpiredException extends RuntimeException {

	public JwtTokenExpiredException(String messsage) {
		super(messsage);
	}
	
	
	
}
