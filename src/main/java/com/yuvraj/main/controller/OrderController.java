package com.yuvraj.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yuvraj.main.dto.OrderDto;
import com.yuvraj.main.models.Order;
import com.yuvraj.main.response.ApiResponse;
import com.yuvraj.main.serviceImpl.OrderServiceImpl;

@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {
	@Autowired
	private OrderServiceImpl orderService;
	
	
	@PostMapping("/order")
	public ResponseEntity<ApiResponse> placeOrder(@RequestParam Long userId){
		OrderDto order = this.orderService.placeOrder(userId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Order placed successfully!", order), HttpStatus.OK);
	} 
	
	
	@GetMapping("/order/{orderId}")
	public ResponseEntity<ApiResponse> getOrder(@PathVariable Long orderId){
		OrderDto order = this.orderService.getOrder(orderId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Order fetched successfully",order),HttpStatus.OK);
	}
	
	
	@GetMapping("/order")
	public ResponseEntity<ApiResponse> getUserOrders(@RequestParam Long userId){
		List<OrderDto> orders = this.orderService.getUserOrders(userId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Order fetched successfully",orders),HttpStatus.OK);
	}
	
}
