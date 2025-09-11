package com.gabriel.api.HomeControllers;


import org.springframework.web.bind.annotation.*;

import com.gabriel.api.dto.UserDto;




@RestController
@RequestMapping("/api")
public class HomeController  {
	
    @PostMapping("/user")
    public UserDto  processUser(@RequestBody UserDto user) {
    	System.out.println(user.getUserId() + " - " + user.getPassword());
        return user; // echoes back
	}
}
