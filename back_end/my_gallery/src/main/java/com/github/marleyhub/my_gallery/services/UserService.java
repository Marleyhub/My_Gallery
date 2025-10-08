package com.github.marleyhub.my_gallery.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.marleyhub.my_gallery.dto.UserDto;
import com.github.marleyhub.my_gallery.entities.User;
import com.github.marleyhub.my_gallery.mapper.UserMapper;
import com.github.marleyhub.my_gallery.repositories.UserRepository;

@Service
public class UserService {
	private final UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;	
	}
	
	// get all users
	@Transactional(readOnly = true)
	public List<UserDto> getUsers() {
		 return UserMapper.toDto(userRepository.findAll());
	}
	
	// get one user by id
	@Transactional(readOnly = true)
	public Optional<UserDto> getUser(Long id) {
		if(id == null) {
			return Optional.empty();
		}
		return UserMapper.toDto(userRepository.findById(id));
	}
	
	// get one user by email
	@Transactional(readOnly = true)
	public Optional<UserDto> getOneUser(String email) {
		if(email == null) {
			return Optional.empty();
		}
		return UserMapper.toDto(userRepository.findByEmail(email));
	}
  
	// create user
	@Transactional
    public UserDto createUser(UserDto body) {
    	User result = UserMapper.toEntity(body);
    	return UserMapper.toDto(result);
    }
    
	// delete user
    @Transactional
    public void deleteUser(Long id) {
    	userRepository.deleteById(id);
    }
    
    // update user
    @Transactional
    public Optional<UserDto> updateUser(Long id, UserDto body) { 
    	if (body == null && id == null) {
    		return Optional.empty();
    	}
    	return userRepository.findById(id).map(existingUser -> {
    		existingUser.setEmail(body.getEmail());
    		return UserMapper.toDto(userRepository.save(existingUser)); 
    	});	
    }
}
