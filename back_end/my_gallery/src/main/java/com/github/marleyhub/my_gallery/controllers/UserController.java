package com.github.marleyhub.my_gallery.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.github.marleyhub.my_gallery.dto.UserDto;
import com.github.marleyhub.my_gallery.services.JwtService;
import com.github.marleyhub.my_gallery.services.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;
   
    public UserController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers() {
    	List<UserDto> users = userService.getUsers();
    	
    	if(users.isEmpty()) {
    		return ResponseEntity.noContent().build();
    	}
    	return ResponseEntity.ok(users);
    }
    
    @GetMapping(value = "/{id}")
    public ResponseEntity<Optional<UserDto>> getUser(@PathVariable Long id) {
    	Optional<UserDto> result = userService.getUser(id);
    	
    	if(result.isEmpty()) {
    		return ResponseEntity.noContent().build();
    	}
    	return ResponseEntity.ok(result);
    }
    
    @PostMapping
    public ResponseEntity<Map<String, String>> createUser(@RequestBody UserDto body) {
        try {
            userService.createUser(body);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(Map.of("message", "User created successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message","User creation failed: " + e.getMessage()));
        }
    }
    
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
    	boolean deleted = userService.deleteUser(id);
    	
    	if(!deleted) {
    		return ResponseEntity
    				.status(HttpStatus.NOT_FOUND)
    				.body("User not found");
    	}
    	return ResponseEntity
    			.status(HttpStatus.NO_CONTENT)
    			.body("User deleted");
    }
    
    @PutMapping(value = "/{id}/replacement")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UserDto body) {
    	Optional<UserDto> updated = userService.updateUser(id, body);
    
    	if(updated.isPresent()) {
    		return ResponseEntity
    				.status(HttpStatus.OK)
    				.body("User: " + updated.get() + " -> Updated Succesfully");
    	}
    	return ResponseEntity
    			.status(HttpStatus.BAD_REQUEST)
    			.body("User not found");
    }
    
	record UploadResponse(String url) {}
}
