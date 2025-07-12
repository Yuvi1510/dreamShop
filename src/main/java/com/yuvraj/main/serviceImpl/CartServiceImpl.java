package com.yuvraj.main.serviceImpl;

import java.math.BigDecimal;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuvraj.main.models.Cart;
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
	
	
	 
	@Override
	public CartDto makeCart() {
		Cart cart = new Cart();
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
		this.cartRepo.delete(cart);
	}

	@Override
	public BigDecimal getTotalPrice(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
