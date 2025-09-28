package com.github.marleyhub.my_gallery.dto.response;

public class UserResponseDto {
	 private String email;
	 private Long id;

	 public  UserResponseDto() {
	 }
	
	 public  UserResponseDto(String email, Long id) {
		 this.email = email;
		 this.id = id;
	 }
	
	 // Getters e Setters
	 public String getEmail() {
		 return email;
	 }
	
	 public void setEmail(String email) {
		 this.email = email;
	 }

	 public Long getId() {
		 return id;
	 }

	 public void setId(Long id) {
		 this.id = id;
	 }
	
	
}
