package com.github.marleyhub.my_gallery.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.marleyhub.my_gallery.services.S3Service;

@RestController
@RequestMapping("/uploads")
public class UploadController {

	private final S3Service s3Service;
	
	public UploadController(S3Service s3Service) {
		this.s3Service = s3Service;
	}
	
	@PostMapping("/upload")
	public ResponseEntity<?> uploadFile(@RequestParam MultipartFile file) {
		try {
			String url = s3Service.uploadFile(file);
			return ResponseEntity.ok().body(new UploadResponse(url));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body("Upload Faild" + e.getMessage());
		}
	}
	record UploadResponse(String url) {}
}
