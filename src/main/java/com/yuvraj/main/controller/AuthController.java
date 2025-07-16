package com.yuvraj.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yuvraj.main.exception.InvalidCredentialsException;
import com.yuvraj.main.request.LoginRequest;
import com.yuvraj.main.response.ApiResponse;
import com.yuvraj.main.response.JwtResponse;
import com.yuvraj.main.security.jwt.JwtUtils;
import com.yuvraj.main.security.user.ShopUserDetails;

import jakarta.validation.Valid;

@RestController
@RequestMapping("${api.prefix}/auth")
public class AuthController {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtUtils jwtUtils;
	
	@PostMapping("/login")
	public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest request){
		try {
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			ShopUserDetails user = (ShopUserDetails) authentication.getPrincipal();
			String token = jwtUtils.generateTokenForuser(authentication);
			JwtResponse res = new JwtResponse(user.getId(), token);
			
			return new ResponseEntity<>(new ApiResponse("Login successful", res), HttpStatus.OK);
		}catch(AuthenticationException e) {
			throw new InvalidCredentialsException("Invalid username or password");
		}
	}
}
