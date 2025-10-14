package com.github.marleyhub.my_gallery.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.marleyhub.my_gallery.dto.UserDto;
import com.github.marleyhub.my_gallery.services.JwtService;
import com.github.marleyhub.my_gallery.services.S3Service;
import com.github.marleyhub.my_gallery.services.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    private final UserService userService;
    private final S3Service s3Service;
    private final JwtService jwtService;
   
    public UserController(UserService userService, S3Service s3Service, JwtService jwtService) {
        this.userService = userService;
        this.s3Service = s3Service;
        this.jwtService = jwtService;
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
    
    @GetMapping("/images")
    public ResponseEntity<List<String>> getUserImages(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        if (!jwtService.isTokenValid(token)) {
            return ResponseEntity.status(401).build();
        }

        String userId = jwtService.extractUserId(token); // get user id
        List<String> images = s3Service.listUserImages(userId);
        return ResponseEntity.ok(images);
    }
    
    @PostMapping("/upload")
	public ResponseEntity<?> uploadFile(@RequestParam MultipartFile file,  @RequestParam String userId) {
		try {
			String url = s3Service.uploadFile(file, userId);
			return ResponseEntity.ok().body(new UploadResponse(url));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body("Upload Faild" + e.getMessage());
		}
	}
	record UploadResponse(String url) {}
}
