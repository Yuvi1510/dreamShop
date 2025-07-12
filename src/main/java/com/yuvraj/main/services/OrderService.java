package com.yuvraj.main.services;

import java.util.List;
import com.yuvraj.main.dto.OrderDto;

public interface OrderService {
	
	OrderDto placeOrder(Long userId);
	OrderDto getOrder(Long orderId);
	List<OrderDto> getUserOrders(Long userId);

}
