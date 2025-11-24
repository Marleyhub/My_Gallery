package com.github.marleyhub.my_gallery.config;

import com.github.marleyhub.my_gallery.security.JwtAuthenticationFilter;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	  private final UserDetailsService userDetailsService;
	  private final JwtAuthenticationFilter jwtAuthenticationFilter;
	
	  public SecurityConfig(UserDetailsService userDetailsService,
			  				JwtAuthenticationFilter jwtAuthenticationFilter) {
	        this.userDetailsService = userDetailsService;
	        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	    }

	  @Bean
	  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	      http
	          .csrf(csrf -> csrf.disable())
	          .cors(cors -> cors.configurationSource(corsConfigurationSource()))
	          .authorizeHttpRequests(auth -> auth
	              .requestMatchers("/auth/**", "/public/**").permitAll()  // ONLY public endpoints
	              .anyRequest().authenticated()                            // everything else requires token
	          )
	          .userDetailsService(userDetailsService)
	          .sessionManagement(session -> 
	              session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	          )
	          .formLogin(form -> form.disable())
	          .httpBasic(basic -> basic.disable());

	      http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

	      return http.build();
	  }

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
	    var configuration = new org.springframework.web.cors.CorsConfiguration();
	    configuration.setAllowedOrigins(
	        java.util.List.of("https://marleyhub.github.io")
	    );
	    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
	    configuration.setAllowedHeaders(List.of("*", "Authorization", "Content-Type"));
	    configuration.setExposedHeaders(List.of("*"));
	    configuration.setAllowCredentials(true);

	    var source = new org.springframework.web.cors.UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", configuration);
	    return source;
	}

	
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}