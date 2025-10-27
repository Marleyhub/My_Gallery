package com.github.marleyhub.my_gallery.auth_controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import com.github.marleyhub.my_gallery.dto.request.LoginRequest;
import com.github.marleyhub.my_gallery.dto.response.LoginResponse;
import com.github.marleyhub.my_gallery.dto.response.UserResponseDto;
import com.github.marleyhub.my_gallery.services.JwtService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    public AuthController(
            AuthenticationManager authenticationManager,
            UserDetailsService userDetailsService,
            JwtService jwtService
    ) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        try {
            // Trigger Spring Security authentication
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            // Load user details
            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());

            // Generate JWT token
            String token = jwtService.generateToken(
                userDetails.getUsername(),
                null // if you want to include the user ID, you can load it from DB here
            );

            // Create response DTO
            UserResponseDto userResponse = new UserResponseDto(userDetails.getUsername(), null);
            return ResponseEntity.ok(new LoginResponse(userResponse, token, "Login successful"));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401)
                .body(new LoginResponse(null, null, "Invalid email or password"));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                .body(new LoginResponse(null, null, "Login error: " + e.getMessage()));
        }
    }
}
