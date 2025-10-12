package com.github.marleyhub.my_gallery.auth_controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.marleyhub.my_gallery.dto.UserDto;
import com.github.marleyhub.my_gallery.dto.request.LoginRequest;
import com.github.marleyhub.my_gallery.dto.response.LoginResponse;
import com.github.marleyhub.my_gallery.dto.response.UserResponseDto;
import com.github.marleyhub.my_gallery.services.JwtService;
import com.github.marleyhub.my_gallery.services.UploadService;
import com.github.marleyhub.my_gallery.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private final UserService userService;
	private final UploadService s3service;
	private final JwtService jwtService;
	
	public AuthController(UserService userService, UploadService s3Service, JwtService jwtService) {
		this.userService = userService;
		this.s3service = s3Service;
		this.jwtService = jwtService;
	}
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request)  {
		String email = request.getEmail();
		String password = request.getPassword();
		
		// Optional validation
		Optional<UserDto> result = userService.getOneUser(email);
		if(result.isPresent()) {
	        UserDto user = result.get();
	        
	        // Password validation
			if(user.getEmail().equals(email) && user.getPassword().equals(password)) {
				
				// Creating jwt token
				String token = jwtService.generateToken(user.getEmail(), user.getId().toString());
				
				UserResponseDto safeUser = new UserResponseDto(user.getEmail(), user.getId()); 				
				return ResponseEntity.ok(new LoginResponse(safeUser, token, "Login Successful"));
			} else {
				return ResponseEntity.status(401).body(new LoginResponse(null, null, "Invalid password"));
			}
		}
		return ResponseEntity.status(401).body(new LoginResponse(null ,null, "LoginError"));
	}
}