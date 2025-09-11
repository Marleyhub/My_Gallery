package com.gabriel.java_API.HomeControllers;


import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//import java.util.Map;



@RestController
public class HomeController  {
	
    @PostMapping("/api/user")
    public String processUser() {
    	System.out.println("foi"); 
        return ""; // echoes back
	}
}
