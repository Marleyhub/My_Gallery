package com.gabriel.api.services;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;



@Service
public class UserService {

	private final Map<String, String> users = new HashMap<>();
	
	public UserService() {
		users.put("gabriel", "1234");
	}
	
	public boolean validateUser(String userId, String password) {
		String storedPassword = users.get(userId);
        return storedPassword != null && storedPassword.equals(password);
	}
	
	public void createUser(String userId, String password) {
		users.put(userId, password);
	}
}
