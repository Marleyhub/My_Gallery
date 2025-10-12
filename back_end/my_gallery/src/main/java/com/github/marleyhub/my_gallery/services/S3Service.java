package com.github.marleyhub.my_gallery.services;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.time.Duration;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

@Service
public class S3Service {
	 private final S3Client s3Client;
	 private final S3Presigner s3Presigner;
	 private final String bucketName = "marley-gallery-bucket";
	 
	 public S3Service(S3Client s3Client, S3Presigner s3Presigner) {
		 this.s3Client = s3Client;
		 this.s3Presigner = s3Presigner;
	 }
	 
	 public String uploadFile(String userId, MultipartFile file) throws IOException {
		 // media unique identifier for url safe with uuid
		 String key = "user_" + userId + "/" + UUID.randomUUID() + "-" + file.getOriginalFilename();
		 
		 // param for the real upload method
		 PutObjectRequest putObjectRequest = PutObjectRequest.builder()
				 .bucket(bucketName)
				 .key(key)
				 .contentType(file.getContentType())
				 .build();
		 
		 // Uploading image
		 s3Client.putObject(putObjectRequest,
				 software.amazon.awssdk.core.sync.RequestBody.fromBytes(file.getBytes()));
		 
		 return "https://" + bucketName + ".s3.us-east-2.amazonaws.com/" + key;
	 }
	 
	 public List<String> getUserImages(String userId) {
		 // Request set to list keys inside ListObjectsV2Response response (not accessible yet)
		 ListObjectsV2Request request = ListObjectsV2Request.builder()
	                .bucket(bucketName)
	                .prefix("user_" + userId + "/")
	                .build();
	        
	        // Call to S3 bucket to return list os keys inside a response object
	        ListObjectsV2Response response = s3Client.listObjectsV2(request);
	        
	        // Maps each key to a presigned url and serves front end to getImage
	        return response.contents().stream()
	                .map(s3Object -> generatePresignedUrl(s3Object.key()))
	                .toList();
	 }
	 
	 // called into map loop for each url that s3 returned
	 private String generatePresignedUrl(String key) {
		 	// Creates a get request for a specified "bucket + key"
	        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
	                .bucket(bucketName)
	                .key(key)
	                .build();
	        
	        // transforms that raw request into a presigned version
	        PresignedGetObjectRequest presignedRequest = s3Presigner.presignGetObject(
	                b -> b.signatureDuration(Duration.ofMinutes(15))
	                      .getObjectRequest(getObjectRequest)
	        );
		 
		 return presignedRequest.url().toString();
	 }
	       
}
