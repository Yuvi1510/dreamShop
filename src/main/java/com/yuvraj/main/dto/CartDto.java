package com.yuvraj.main.dto;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;


public class CartDto {
	private Long id;
	private BigDecimal totalAmount = BigDecimal.ZERO;
		private Set<CartItemDto> items = new HashSet<>();
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public BigDecimal getTotalAmount() {
			return totalAmount;
		}
		public void setTotalAmount(BigDecimal totalAmount) {
			this.totalAmount = totalAmount;
		}
		public Set<CartItemDto> getItems() {
			return items;
		}
		public void setItems(Set<CartItemDto> items) {
			this.items = items;
		}
		
		
	
}
