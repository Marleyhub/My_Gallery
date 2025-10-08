package com.github.marleyhub.my_gallery.dto.response;


public class LoginResponse{
	
	private String message;
	private UserResponseDto user;
	private String token;
	
	public LoginResponse(UserResponseDto user, String token,  String message) {
		this.user = user;
		this.token = token;
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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	
}
