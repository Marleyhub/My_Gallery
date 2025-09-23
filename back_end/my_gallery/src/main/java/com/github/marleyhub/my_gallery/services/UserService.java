package com.github.marleyhub.my_gallery.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.marleyhub.my_gallery.entities.User;
import com.github.marleyhub.my_gallery.repositories.UserRepository;

@Service
public class UserService {
	private final UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Transactional(readOnly = true)
	public List<User> printAllUsers() {
		return userRepository.findAll();
	}
  
	@Transactional
    public List<User> createUser(User body) {
    	User result = userRepository.save(body);
    	return List.of(result);
    }
    
    @Transactional
    public void deleteUser(Long id) {
    	userRepository.deleteById(id);
    }
    
    @Transactional
    public Optional<User> updateUser(Long id, User body) {
    	if (body == null) {
    		return Optional.empty();
    	}
    	
    	return userRepository.findById(id).map(existingUser -> {
    		existingUser.setEmail(body.getEmail());
    		
    		return userRepository.save(existingUser);
    	});
    	
    	
    }
}
