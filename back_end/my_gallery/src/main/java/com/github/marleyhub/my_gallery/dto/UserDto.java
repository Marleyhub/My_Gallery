package com.github.marleyhub.my_gallery.dto;

public class UserDto {

	private Long id;
	private String email;
	
	public UserDto() {
		
	}
	
	public UserDto(Long id, String email) {
		this.id = id;
		this.email = email;
	}
	
	public UserDto(com.github.marleyhub.my_gallery.entities.User user) {
		this.id = user.getId();
		this.email = user.getEmail();
	}
	
	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
