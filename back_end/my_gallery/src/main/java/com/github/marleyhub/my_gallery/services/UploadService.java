package com.github.marleyhub.my_gallery.services;

import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class UploadService {
	 private final S3Client s3Client;
	 private final String bucketName = "marley-gallery-bucket";
	 
	 public UploadService(S3Client s3Client) {
		 this.s3Client = s3Client;	 
	 }
	 
	 
	 public String uploadFile(MultipartFile file) throws IOException {
		 
		 // media unique identifier for url safe with uuid
		 String key = UUID.randomUUID() + "-" + file.getOriginalFilename();
		 
		 // param for the real put object method
		 PutObjectRequest putObjectRequest = PutObjectRequest.builder()
				 .bucket(bucketName)
				 .key(key)
				 .contentType(file.getContentType())
				 .build();
		 
		 s3Client.putObject(putObjectRequest,
				 software.amazon.awssdk.core.sync.RequestBody.fromBytes(file.getBytes()));
		 
		 return "https://" + bucketName + ".s3.us-east-2.amazonaws.com/" + key;
	 }
}
