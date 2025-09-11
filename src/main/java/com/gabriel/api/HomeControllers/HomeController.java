package com.gabriel.api.HomeControllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.gabriel.api.services.UserService;
import com.gabriel.api.dto.UserDto;


@RestController
@RequestMapping("/api")
public class HomeController  {

    private final UserService userService;
    
	public HomeController(UserService userService) {
		this.userService = userService;
	}
	
    @PostMapping("/user")
    public Map<String, Object>  processUser(@RequestBody UserDto user) {
    	boolean isValid = userService.validateUser(user.getUserId(), user.getPassword());
    	
    	Map<String, Object> response = new HashMap<>();
        response.put("success", isValid);
        response.put("message", isValid ? "Login successful" : "Invalid credentials");

        return response;
    	
	}
}
