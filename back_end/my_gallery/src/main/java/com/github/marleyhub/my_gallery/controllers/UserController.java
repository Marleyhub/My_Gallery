package com.github.marleyhub.my_gallery.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.marleyhub.my_gallery.dto.UserDto;
import com.github.marleyhub.my_gallery.entities.User;
import com.github.marleyhub.my_gallery.services.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDto> getUsers() {
        return userService.getUsers();
    }
    
    @GetMapping(value = "/{id}")
    public Optional<UserDto> getUser(@PathVariable Long id) {
    	Optional<UserDto> result = userService.getUser(id);
    	
    	if (result.isPresent()) {
    		return result;
    	} else {
    		return Optional.empty();
    	} 
    }
    
    
    @PostMapping
    public void createUser(@RequestBody UserDto body) {
    	UserDto result = userService.createUser(body);
    	System.out.println(result);
    }
    
    @DeleteMapping(value = "/{id}")
    public void deleteUser(@PathVariable Long id) {
    	userService.deleteUser(id);
    }
    
    @PutMapping(value = "/{id}/replacement")
    public void updateUser(@PathVariable Long id, @RequestBody UserDto body) {
    	Optional<UserDto> updated = userService.updateUser(id, body);
    	
    	if(updated.isPresent()) {
    		System.out.println("User updated: " + updated.get());
    	} else {
    		System.out.println("User not found or body null");
    	}
    }
    
    
}
