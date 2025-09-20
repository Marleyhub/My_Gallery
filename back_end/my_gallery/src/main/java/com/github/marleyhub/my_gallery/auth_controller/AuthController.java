package com.github.marleyhub.my_gallery.auth_controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {
	
	@PostMapping("/login")
	public ResponseEntity <Map<String, String>> login(@RequestBody Map<String, String> credentials) {
		String email = credentials.get("email");
		String password = credentials.get("password");
		
		if("sabadaopelalapa@gmail.com".equals(email) && "1234567".equals(password)) {
			return ResponseEntity.ok(Map.of("massage", "login successful"));
		} else {
			return ResponseEntity.status(401).body(Map.of("message", "Invalid user or password"));
		}
		
	}
}
