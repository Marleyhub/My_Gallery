package com.github.marleyhub.my_gallery.entities;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_user")
public class User {
	public User() {}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(columnDefinition = "TEXT")
	private String email;
	private String password;
	
	public User(String email, String password) {
		this.email = email;
		this.password = password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return this.id;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEmail(){
		return this.email;
	}
	
	@Override
	public String toString() {
		return "User email: " + this.email + "User Id: " + this.id;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass()!= obj.getClass())
			return false;
		User other = (User) obj;
		
		return Objects.equals(id, other.id);
	}
}
