package com.github.marleyhub.my_gallery.auth_controller;

import java.util.Map;
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
import com.github.marleyhub.my_gallery.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private final UserService userService;
	
	public AuthController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request)  {
		String email = request.getEmail();
		String password = request.getPassword();
		
		Optional<UserDto> result = userService.getOneUser(email);
		
		if(result.isPresent()) {
	        UserDto user = result.get();
		
			if(user.getEmail().equals(email) && user.getPassword().equals(password)) {
				UserResponseDto safeUser = new UserResponseDto(user.getEmail(), user.getId()); 
				return ResponseEntity.ok(new LoginResponse(safeUser ,"Login Successful"));
			} else {
				return ResponseEntity.status(401).body(new LoginResponse(null, "Invalid password"));
			}
		}
		return ResponseEntity.status(401).body(new LoginResponse(null ,"LoginError"));
	}
}