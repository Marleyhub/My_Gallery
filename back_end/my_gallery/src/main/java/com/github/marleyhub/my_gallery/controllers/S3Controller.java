package com.github.marleyhub.my_gallery.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.marleyhub.my_gallery.services.JwtService;
import com.github.marleyhub.my_gallery.services.S3Service;

@RestController
@RequestMapping(value = "/images")
public class S3Controller {
    private final S3Service s3Service;
    private final JwtService jwtService;
   
    public S3Controller(S3Service s3Service, JwtService jwtService) {
        this.s3Service = s3Service;
        this.jwtService = jwtService;
    }
    
    @GetMapping
    public ResponseEntity<List<String>> getUserImages(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        if (!jwtService.isTokenValid(token)) {
            return ResponseEntity.status(401).build();
        }

        String userId = jwtService.extractUserId(token);
        List<String> images = s3Service.listUserImages(userId);
        return ResponseEntity.ok(images);
    }
    
    @PostMapping
	public ResponseEntity<?> uploadFile(
			@RequestParam MultipartFile file,
			@RequestHeader("Authorization") String authHeader
			) {
    	String token = authHeader.substring(7);
    	
        if (!jwtService.isTokenValid(token)) {
            return ResponseEntity.status(401).build();
        }
        
		try {
			String userId = jwtService.extractUserId(token);
			String url = s3Service.uploadFile(file, userId);
			return ResponseEntity.ok().body(new UploadResponse(url));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body("Upload Faild" + e.getMessage());
		}
	}
    
    @DeleteMapping("/{key}")
    public ResponseEntity<?> deleteImage(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable String key
        ) {

        String token = authHeader.substring(7);
        if (!jwtService.isTokenValid(token)) {
            return ResponseEntity.status(401).build();
        }

        String userId = jwtService.extractUserId(token);

        boolean deleted = s3Service.deleteImage(userId, key);

        if (!deleted) {
            return ResponseEntity.status(404).body("Image not found");
        }

        return ResponseEntity.noContent().build();
    }
    
    
	record UploadResponse(String url) {}
}
