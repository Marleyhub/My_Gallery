package com.github.marleyhub.my_gallery.services;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
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
	 private final String bucketName;
	 
	 public S3Service(
			 S3Client s3Client,
			 S3Presigner s3Presigner,
			 @Value("${AWS_S3_BUCKET}") String bucketName
	) {
		 this.s3Client = s3Client;
		 this.s3Presigner = s3Presigner;
		 this.bucketName = bucketName;
	 }
	 
	 public String uploadFile(MultipartFile file, String userId) throws IOException {
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
	 
	 public List<String> listUserImages(String userId) {
		  String prefix = "user_" + userId + "/";
		  
		 // Request set to list keys inside ListObjectsV2Response response (not accessible yet)
		 ListObjectsV2Request request = ListObjectsV2Request.builder()
	                .bucket(bucketName)
	                .prefix(prefix)
	                .build();
	        
	        // Call to S3 bucket to return list of keys inside a response object
		  	ListObjectsV2Response result = s3Client.listObjectsV2(request);
	        
	        // Maps each key to a presigned url and serves front end to getImage
	        return result.contents().stream()
	                .map(s3Object -> generatePresignedUrl(s3Object.key()))
	                .toList();
	 }
	 
	 public boolean deleteImage(String userId, String fileName) {
		    try {
		        String key = "user_" + userId + "/" + fileName;

		        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
		                .bucket(bucketName)
		                .key(key)
		                .build();

		        s3Client.deleteObject(deleteObjectRequest);

		        return true;
		    } catch (Exception e) {
		        e.printStackTrace();
		        return false;
		    }
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
