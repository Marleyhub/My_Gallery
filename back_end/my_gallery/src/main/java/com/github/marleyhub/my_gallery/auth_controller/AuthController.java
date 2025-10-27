package com.github.marleyhub.my_gallery.auth_controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import com.github.marleyhub.my_gallery.dto.request.LoginRequest;
import com.github.marleyhub.my_gallery.dto.response.LoginResponse;
import com.github.marleyhub.my_gallery.dto.response.UserResponseDto;
import com.github.marleyhub.my_gallery.entities.User;
import com.github.marleyhub.my_gallery.repositories.UserRepository;
import com.github.marleyhub.my_gallery.services.JwtService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public AuthController(
            AuthenticationManager authenticationManager,
            UserDetailsService userDetailsService,
            JwtService jwtService,
            UserRepository userRepository
    ) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        try {
            // Trigger Spring Security authentication
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            
            // Load the full user from DB (to get ID)
            User user = userRepository.findByEmail(request.getEmail())
            		.orElseThrow(() -> new UsernameNotFoundException("User not found"));
   
            // Generate JWT token
            String token = jwtService.generateToken(user.getEmail(), String.valueOf(user.getId()));

            // Create response DTO
            UserResponseDto userResponse = new UserResponseDto(user.getEmail(), String.valueOf(user.getId()));

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
