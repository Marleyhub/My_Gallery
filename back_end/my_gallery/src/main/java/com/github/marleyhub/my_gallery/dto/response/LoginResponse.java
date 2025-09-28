package com.github.marleyhub.my_gallery.dto.response;


public class LoginResponse{
	
	private String message;
	private UserResponseDto user;
	
	public LoginResponse(UserResponseDto user, String message) {
		this.user = user;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public UserResponseDto getUser() {
		return user;
	}

	public void setUser(UserResponseDto user) {
		this.user = user;
	}
	
	
}
