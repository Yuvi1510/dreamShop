package com.yuvraj.main.serviceImpl;

import java.util.Optional;
import com.yuvraj.main.models.Cart;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuvraj.main.exception.AlreadyExistsException;
import com.yuvraj.main.exception.ResourceNotFoundException;
import com.yuvraj.main.models.User;
import com.yuvraj.main.repositories.UserRepository;
import com.yuvraj.main.request.CreateUserRequest;
import com.yuvraj.main.request.UpdateUserRequest;
import com.yuvraj.main.services.UserService;


@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private CartServiceImpl cartService;
	
	
	@Override
	public User createUser(CreateUserRequest request) {
//		if(this.userRepo.existsByEmail(request.getEmail())) {
//			throw new AlreadyExistsException("User","email",request.getEmail());
//		}
//		return this.userRepo.save(this.modelMapper.map(request, User.class));
		
		return Optional.of(request).filter(user -> !this.userRepo.existsByEmail(request.getEmail()))
				.map(req ->{
					User user = this.modelMapper.map(request, User.class);
					this.userRepo.save(user);
					return user;
				}).orElseThrow(()-> new AlreadyExistsException("User","email",request.getEmail()));
	}

	@Override
	public User getUserById(Long userId) {
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","user id",userId));
		return user;
	}

	@Override
	public User updateUser(UpdateUserRequest request, Long userId) {
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","user id",userId));
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		return this.userRepo.save(user);
	}

	@Override
	public void deleteUser(Long userId) {
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","user id",userId));
		this.userRepo.delete(user);
	}

}
