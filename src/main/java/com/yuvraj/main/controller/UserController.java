package com.yuvraj.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yuvraj.main.dto.UserDto;
import com.yuvraj.main.models.User;
import com.yuvraj.main.request.CreateUserRequest;
import com.yuvraj.main.request.UpdateUserRequest;
import com.yuvraj.main.response.ApiResponse;
import com.yuvraj.main.serviceImpl.UserServiceImpl;

@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {
	
	@Autowired
	UserServiceImpl userService;
	
	@PostMapping("/add")
	public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest user){
		UserDto createdUser = this.userService.createUser(user);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User created successfully",createdUser), HttpStatus.OK);
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<ApiResponse> getUser(@PathVariable Long userId){
		UserDto user = this.userService.getUserById(userId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User fetched successfully", user),HttpStatus.OK);
	}
	
	@PutMapping("/{userId}")
	public ResponseEntity<ApiResponse> updateUser(@RequestBody UpdateUserRequest user, @PathVariable Long userId){
		UserDto updatedUser = this.userService.updateUser(user, userId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User updated successfully", updatedUser), HttpStatus.OK);
	}
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId){
		this.userService.deleteUser(userId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted sucessfully!",null),HttpStatus.OK);
	}
	
}
