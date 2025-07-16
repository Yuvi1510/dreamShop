package com.yuvraj.main.serviceImpl;

import java.math.BigDecimal;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuvraj.main.models.Cart;
import com.yuvraj.main.models.User;
import com.yuvraj.main.repositories.CartItemRepository;
import com.yuvraj.main.repositories.CartRepository;
import com.yuvraj.main.services.CartService;
import com.yuvraj.main.dto.CartDto;
import com.yuvraj.main.exception.ResourceNotFoundException;

@Service
public class CartServiceImpl implements CartService {
	@Autowired
	private CartRepository cartRepo;
	

	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private CartItemServiceImpl cartItemService;
	
	
	 
	@Override
	public CartDto makeCart(User user) {
		Cart cart = new Cart();
		cart.setUser(user);
		cart.setTotalAmount(BigDecimal.ZERO);
		Cart savedCart =  this.cartRepo.save(cart);
		return this.modelMapper.map(savedCart, CartDto.class);
	}
	
	@Override
	public CartDto getCart(Long id) {
		Cart cart = this.cartRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Cart","id",id));
		return this.modelMapper.map(cart, CartDto.class);
	}

	@Override
	public void clearCart(Long id) {
		Cart cart = this.cartRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Cart","id",id));
		cart.getItems().forEach((item)->{
			this.cartItemService.removeItemFromCart(cart.getId(), item.getProduct().getId());
		});
	}

	@Override
	public BigDecimal getTotalPrice(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CartDto getCartByUserId(Long userId) {
		Cart cart = this.cartRepo.findByUserId(userId).orElseThrow(()-> new ResourceNotFoundException("Cart","user id", userId));
		return this.modelMapper.map(cart, CartDto.class);
	}

	
}
