package com.yuvraj.main.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yuvraj.main.dto.CartItemDto;
import com.yuvraj.main.models.CartItem;
import com.yuvraj.main.response.ApiResponse;
import com.yuvraj.main.serviceImpl.CartItemServiceImpl;
import com.yuvraj.main.serviceImpl.CartServiceImpl;

@RestController
@RequestMapping("${api.prefix}/cart/{cartId}/cartItem")
public class CartItemController {
	
	@Autowired
	private CartItemServiceImpl cartItemService;
	
	@Autowired
	private CartServiceImpl cartService;
	
	@Autowired
	private ModelMapper modeMapper;
	
	
	
	@PostMapping("")
	public ResponseEntity<ApiResponse> addItemToCart(@PathVariable(required=false) Long cartId, @RequestParam Long productId, @RequestParam int quantity){
		
		this.cartItemService.addItemToCart(cartId, productId, quantity);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Successfully added item to cart",null), HttpStatus.OK);
		
	}
	
	@DeleteMapping("")
	public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable Long cartId, @RequestParam Long productId){
		this.cartItemService.removeItemFromCart(cartId, productId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Item removed successfully",null),HttpStatus.OK);
	}
	
	@PutMapping("")
	public ResponseEntity<ApiResponse> updateItemQuantity(@PathVariable Long cartId, @RequestParam Long productId, @RequestParam int quantity){
		this.cartItemService.updateItemQuantity(cartId, productId, quantity);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Item quantity updated successfully",null),HttpStatus.OK);
	}
	
	@GetMapping("")
	public ResponseEntity<ApiResponse> getCartItem(@PathVariable Long cartId, @RequestParam Long productId){
		CartItem cartItem = this.cartItemService.getCartItem(cartId, productId);
		CartItemDto cartItemDto = this.modeMapper.map(cartItem, CartItemDto.class);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Cart item fetched successfully",cartItemDto),HttpStatus.OK);
	}
}
 