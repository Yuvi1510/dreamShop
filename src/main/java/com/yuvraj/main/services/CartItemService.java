package com.yuvraj.main.services;

import com.yuvraj.main.models.CartItem;

public interface CartItemService {
	void addItemToCart(Long cartId, Long productId, int quantity);
	void removeItemFromCart(Long cartId, Long productId);
	void updateItemQuantity(Long cartId, Long productId, int quantity);
	CartItem getCartItem(Long cartId, Long productId);
	
}
