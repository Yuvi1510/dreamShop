package com.yuvraj.main.services;

import com.yuvraj.main.models.User;
import com.yuvraj.main.request.CreateUserRequest;
import com.yuvraj.main.request.UpdateUserRequest;

public interface UserService {
	User createUser(CreateUserRequest request);
	User getUserById(Long UserId);
	User updateUser(UpdateUserRequest request, Long userId);
	void deleteUser(Long userId);
}
