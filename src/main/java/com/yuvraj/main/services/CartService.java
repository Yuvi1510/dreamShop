package com.yuvraj.main.services;

import java.math.BigDecimal;

import com.yuvraj.main.dto.CartDto;

public interface CartService {
	CartDto makeCart();
	CartDto getCart(Long id);
	void clearCart(Long id);
	BigDecimal getTotalPrice(Long id);

}
