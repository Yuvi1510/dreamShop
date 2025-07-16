package com.yuvraj.main.serviceImpl;

import java.util.Optional;
import com.yuvraj.main.models.Cart;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.yuvraj.main.dto.CartDto;
import com.yuvraj.main.dto.UserDto;
import com.yuvraj.main.exception.AlreadyExistsException;
import com.yuvraj.main.exception.ResourceNotFoundException;
import com.yuvraj.main.models.User;
import com.yuvraj.main.repositories.RoleRepository;
import com.yuvraj.main.repositories.UserRepository;
import com.yuvraj.main.request.CreateUserRequest;
import com.yuvraj.main.request.UpdateUserRequest;
import com.yuvraj.main.services.UserService;
import com.yuvraj.main.dto.UserDto;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private CartServiceImpl cartService;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Override
	public UserDto  createUser(CreateUserRequest request) {
//		if(this.userRepo.existsByEmail(request.getEmail())) {
//			throw new AlreadyExistsException("User","email",request.getEmail());
//		}
//		return this.userRepo.save(this.modelMapper.map(request, User.class));
		
		User savedUser =  Optional.of(request).filter(user -> !this.userRepo.existsByEmail(request.getEmail()))
				.map(req ->{
					User user = this.modelMapper.map(request, User.class);
					user.setPassword(encoder.encode(user.getPassword()));
					this.userRepo.save(user);
					return user;
				}).orElseThrow(()-> new AlreadyExistsException("User","email",request.getEmail()));
		// create user and save first before passing it to makeCart method because the cart has to keep
		// the id of user as foreign key and if us pass user without saving it then the user will 
		// not have any id
		Cart cart = this.modelMapper.map(this.cartService.makeCart(savedUser), Cart.class);
		savedUser.setCart(cart);
		return this.modelMapper.map(savedUser, UserDto.class);
	}

	@Override
	public UserDto getUserById(Long userId) {
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","user id",userId));
		return this.modelMapper.map(user, UserDto.class);
	}

	@Override
	public UserDto updateUser(UpdateUserRequest request, Long userId) {
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","user id",userId));
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		this.userRepo.save(user);
		 
		return this.modelMapper.map(user, UserDto.class);
	}

	@Override
	public void deleteUser(Long userId) {
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","user id",userId));
		this.userRepo.delete(user);
	}

	@Override
	public User getAuthenticatedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		return this.userRepo.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("User","email",email));
	}

}
