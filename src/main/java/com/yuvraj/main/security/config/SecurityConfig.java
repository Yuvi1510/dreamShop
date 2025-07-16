package com.yuvraj.main.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.yuvraj.main.security.jwt.JwtAuthEntryPoint;
import com.yuvraj.main.security.jwt.JwtFilter;
import com.yuvraj.main.security.user.ShopUserDetailService;

@EnableWebSecurity
@EnableMethodSecurity()
@Configuration 
public class SecurityConfig {
	
	@Autowired
	ShopUserDetailService shopUserDetailService;
	
	@Autowired
	JwtAuthEntryPoint jwtAuthEntryPoint;
	
	@Autowired
	JwtFilter jwtFilter;
	
	
//	@Autowired
//	PasswordEncoder encoder;
//	
	@Bean
	PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
		return config.getAuthenticationManager();
	}
	
	@Bean
	AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider(shopUserDetailService);
		provider.setPasswordEncoder(encoder());
		return provider;
	}
	
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http
			.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(request -> request
						.requestMatchers(HttpMethod.POST,"/api/v1/users/add","/api/v1/auth/login")
						.permitAll()
						.anyRequest().authenticated())
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthEntryPoint))
			.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
			
	}
	
	
	
	
	

}
