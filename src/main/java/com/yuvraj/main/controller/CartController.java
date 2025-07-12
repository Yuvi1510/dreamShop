package com.yuvraj.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yuvraj.main.dto.CartDto;
import com.yuvraj.main.models.Cart;
import com.yuvraj.main.response.ApiResponse;
import com.yuvraj.main.serviceImpl.CartServiceImpl;

@RestController
@RequestMapping("${api.prefix}/carts")
public class CartController {
	
	@Autowired
	CartServiceImpl cartService;
	
//	@PostMapping
//	public ResponseEntity<ApiResponse> makeCart(){
//		CartDto cart = this.cartService.makeCart();
//		return new ResponseEntity<ApiResponse>(new ApiResponse("New cart created",cart),HttpStatus.OK);
//	}
//	
	@GetMapping("/{cartId}")
	public ResponseEntity<ApiResponse> getCart(@PathVariable Long cartId){
		CartDto cart = this.cartService.getCart(cartId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("success",cart), HttpStatus.OK);
	}
	
	@GetMapping("")
	public ResponseEntity<ApiResponse> getCartByUserId(@RequestParam Long userId){
		CartDto cart = this.cartService.getCartByUserId(userId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Cart fetched",cart), HttpStatus.OK);
	}
	
	@DeleteMapping("/{cartId}")
	public ResponseEntity<ApiResponse> clearCart(@PathVariable Long cartId){
		 this.cartService.clearCart(cartId);
		 return new ResponseEntity<ApiResponse>(new ApiResponse("successfully cleared cart",null),HttpStatus.OK);
		
	}

}
