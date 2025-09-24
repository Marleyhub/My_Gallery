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
	
	// get all users
	@Transactional(readOnly = true)
	public List<User> getUsers() {
		return userRepository.findAll();
	}
	
	// get one user by id
	@Transactional(readOnly = true)
	public Optional<User> getUser(Long id) {
		if(id == null) {
			return Optional.empty();
		}
		return userRepository.findById(id);
	}
	
	// get one user by email
	@Transactional(readOnly = true)
	public Optional<User> getOneUser(String email) {
		if(email == null) {
			return Optional.empty();
		}
		return userRepository.findByEmail(email);
	}
  
	// create user
	@Transactional
    public List<User> createUser(User body) {
    	User result = userRepository.save(body);
    	return List.of(result);
    }
    
	// delete user
    @Transactional
    public void deleteUser(Long id) {
    	userRepository.deleteById(id);
    }
    
    // update user
    @Transactional
    public Optional<User> updateUser(Long id, User body) { 
    	if (body == null && id == null) {
    		return Optional.empty();
    	}
   
    	return userRepository.findById(id).map(existingUser -> {
    		existingUser.setEmail(body.getEmail());
    		
    		return userRepository.save(existingUser);
    	});
    	
    	
    }
}
