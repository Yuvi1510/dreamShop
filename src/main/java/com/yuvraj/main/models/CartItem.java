package com.yuvraj.main.models;

import java.math.BigDecimal;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CartItem {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id; 
	private int quantity;
	private BigDecimal unitPrice;
	private BigDecimal totalPrice;

	@ManyToOne
	@JoinColumn(name="product_id")
	private Product product;
	
	@ManyToOne
	@JoinColumn(name="cart_id")
	private Cart cart;

	
	public void setTotalPrice() {
//		this.totalPrice = totalPrice;
		this.totalPrice = this.unitPrice.multiply(new BigDecimal(quantity));
	}

	
	
	
}
