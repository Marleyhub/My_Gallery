package com.github.marleyhub.my_gallery.auth_controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.marleyhub.my_gallery.dto.UserDto;
import com.github.marleyhub.my_gallery.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private final UserService userService;
	
	public AuthController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("/login")
	public ResponseEntity <Map<String, String>> login(@RequestBody Map<String, String> credentials) {
		String email = credentials.get("email");
		String password = credentials.get("password");
		
		Optional<UserDto> result = userService.getOneUser(email);
		String userEmail = result.map(u -> u.getEmail()).orElse("Wrong user or password");
		String userPassword = result.map(u -> u.getPassword()).orElse("Wrong user or password");
		
		if(userEmail.equals(email) && userPassword.equals(password)) {
			return ResponseEntity.ok(Map.of("massage", "login successful"));
		} else {
			return ResponseEntity.status(401).body(Map.of("message", "Invalid user or password"));
		}
		
	}
}
