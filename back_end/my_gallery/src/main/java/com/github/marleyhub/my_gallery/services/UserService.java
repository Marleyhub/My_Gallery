package com.github.marleyhub.my_gallery.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.marleyhub.my_gallery.entities.User;
import com.github.marleyhub.my_gallery.repositories.UserRepository;

@Service
public class UserService {
	private final UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public List<User> printAllUsers() {
		return userRepository.findAll();
	}
}
