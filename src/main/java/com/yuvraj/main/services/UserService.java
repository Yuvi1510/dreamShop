package com.yuvraj.main.services;

import com.yuvraj.main.dto.UserDto;
import com.yuvraj.main.models.User;
import com.yuvraj.main.request.CreateUserRequest;
import com.yuvraj.main.request.UpdateUserRequest;

public interface UserService {
	UserDto createUser(CreateUserRequest request);
	UserDto getUserById(Long UserId);
	UserDto updateUser(UpdateUserRequest request, Long userId);
	void deleteUser(Long userId);
}
