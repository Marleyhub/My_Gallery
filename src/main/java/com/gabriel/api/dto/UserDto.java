package com.gabriel.api.dto;

public class UserDto {
	
	private String userId;
	private String password;
	
	public UserDto(String userId, String password) {
		this.password = password;
		this.userId = userId;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
