package com.github.marleyhub.my_gallery.services;

import java.security.Key;
import java.time.Instant;
import java.util.Date;

import javax.crypto.SecretKey;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Service;

@Service
public class JwtService {
	public JwtService () {
		
	}
	// Must be at least 32 chars for HS256 instanced at compile time
	private static final String SECRET_KEY = "thisismyveryverysecretphrasethatshouldbechangedlater";
	// not static because it is instanced at runTime 
	private final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

	/*
	 *  SIGNED JWT
	 *  The jwt flow begins at login where, when successfully logged it calls "generateTokens()"
	*/ 
	public String generateToken(String email, String userId) {
	        Instant now = Instant.now();
	        return Jwts.builder()
	                .subject(userId)  // This sets the standard claim "sub"
	                .claim("email", email) // This adds a custom claim
	                .issuedAt(Date.from(now))
	                .expiration(Date.from(now.plusSeconds(10))) // expires in 10s for test
	                /*
	                 * the compiler finds the right MacAlgorithm,
	                 * so you don't need the second param: (key, MacAlgoritim)
	                */ 
	                .signWith(key) 
	                .compact();
	    }
	
	// Only verify if it is valid but do not tell who is the token user 
	public Boolean isTokenValid(String token) {
		try {
			Jwts.parser()
				.verifyWith(key)
				.build()
				.parseSignedClaims(token);
			return true;
		} catch (JwtException e){
			return false;
		}
	}
	
	// extract userId
	public String extractUserId(String token) {
		return Jwts.parser()
				   .verifyWith(key)
				   .build()  // the parser is created here with above configs
				   .parseSignedClaims(token) // decodes and validations
				   .getPayload()
				   .getSubject();
	}
	
	 // Extract email
    public String extractEmail(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("email", String.class);
    }
}
