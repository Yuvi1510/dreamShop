package com.yuvraj.main.serviceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuvraj.main.dto.OrderDto;
import com.yuvraj.main.enums.OrderStatus;
import com.yuvraj.main.exception.ResourceNotFoundException;
import com.yuvraj.main.models.Cart;
import com.yuvraj.main.models.Order;
import com.yuvraj.main.models.OrderItem;
import com.yuvraj.main.models.Product;
import com.yuvraj.main.repositories.CartRepository;
import com.yuvraj.main.repositories.OrderRepository;
import com.yuvraj.main.repositories.ProductRepository;
import com.yuvraj.main.services.OrderService;

import jakarta.transaction.Transactional;

@Service
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	private OrderRepository orderRepo;
	
	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private CartRepository cartRepo;
	
	@Autowired
	private CartServiceImpl cartService;
	
	@Autowired
	private ModelMapper modelMapper;

	@Transactional
	@Override
	public OrderDto placeOrder(Long userId) {
		Cart cart = this.cartRepo.findByUserId(userId).orElseThrow(()-> new ResourceNotFoundException("Cart","user id", userId));
		Order order = this.createOrder(cart);
		List<OrderItem> orderItems= this.createOrderItems(order, cart);
		order.setOrderItems(new HashSet<>(orderItems));
		order.setTotalAmount(calculateTotalAmount(orderItems));	
		Order placedOrder = this.orderRepo.save(order);
		
		cartService.clearCart(cart.getId());
		
		return this.modelMapper.map(placedOrder, OrderDto.class);
	}
	
	
	private Order createOrder(Cart cart) {
		Order order = new Order();
		order.setUser(cart.getUser());
		order.setOrderStatus(OrderStatus.PENDING); 
		order.setOrderDate(LocalDate.now());
		
		return order;
	}
	 
	
	private List<OrderItem> createOrderItems(Order order, Cart cart){
		return cart.getItems().stream().map((cartItem)->{
			Product product = cartItem.getProduct();
			product.setInventory(product.getInventory() - cartItem.getQuantity());
			this.productRepo.save(product);
			
			return new OrderItem(order, product, cartItem.getQuantity(), cartItem.getUnitPrice());
		}).collect(Collectors.toList()); 
	}
	
	private BigDecimal calculateTotalAmount(List<OrderItem> orderItems) {
		return orderItems.stream().map((item)->{
			return item.getPrice().multiply(new BigDecimal(item.getQuantity()));
		}).reduce(BigDecimal.ZERO,(a,b)->a.add(b)); // .reduce(accumulator's initial value, (accumulator, next value) -> accumulator.add(next value));
		// or we could also use .reduce(BigDecimal.ZERO, BigDecimal::add) // using method reference
	}
	
	
	

	@Override
	public OrderDto getOrder(Long orderId) {
		Order order = this.orderRepo.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("Order","order id",orderId));
		return this.modelMapper.map(order, OrderDto.class);
	}
	
	@Override
	public List<OrderDto> getUserOrders(Long userId){
		return this.orderRepo.findbyUserId(userId)
				.stream().map((order)->{
					return this.modelMapper.map(order, OrderDto.class);
				}).collect(Collectors.toList());
	}

}
