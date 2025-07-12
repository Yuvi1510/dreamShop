package com.yuvraj.main.services;

import java.math.BigDecimal;

import com.yuvraj.main.dto.CartDto;
import com.yuvraj.main.models.User;

public interface CartService {
	CartDto makeCart(User user);
	CartDto getCart(Long id);
	CartDto getCartByUserId(Long userId);
	void clearCart(Long id);
	BigDecimal getTotalPrice(Long id);

}
