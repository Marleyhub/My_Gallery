package com.github.marleyhub.my_gallery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;

@Configuration
public class S3Config {
	
	private static final String DEFAULT_REGION = "us-east-2";
	@Bean
	S3Client s3Client() {
		return S3Client.builder()
					   .region(Region.of(DEFAULT_REGION))
					   .credentialsProvider(DefaultCredentialsProvider.create())
					   .build();
	}
}
