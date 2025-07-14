package com.yuvraj.main.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yuvraj.main.exception.ResourceNotFoundException;
import com.yuvraj.main.models.Cart;
import com.yuvraj.main.repositories.CartItemRepository;
import com.yuvraj.main.repositories.CartRepository;
import com.yuvraj.main.repositories.ProductRepository;
import com.yuvraj.main.services.CartItemService;
import com.yuvraj.main.models.Product;
import com.yuvraj.main.models.CartItem;

@Service
public class CartItemServiceImpl implements CartItemService{
	
	@Autowired
	private CartItemRepository cartItemRepo;
	
	@Autowired
	private CartRepository cartRepo;
	
	@Autowired
	private ProductRepository productRepo;
	
	
	@Override
	public void addItemToCart(Long cartId, Long productId, int quantity) {
		// get the cart
		// get the product
//		check if product already in the cart 
		// if yes, then increase the quantity with the requested quantity
		// if not, then initiate a new cart item 
		
		Cart cart = this.cartRepo.findById(cartId).orElseThrow(()-> new ResourceNotFoundException("cart", "id", cartId));
		Product product = this.productRepo.findById(productId).orElseThrow(()-> new ResourceNotFoundException("product", "id", productId));
		
		CartItem cartItem =cart.getItems().stream().filter((item)->
			item.getProduct().getId().equals(productId)).findFirst().orElse(new CartItem());
		
		
		if(cartItem.getProduct() == null){
			cartItem.setCart(cart);
			cartItem.setProduct(product);
			cartItem.setQuantity(quantity);
//			System.out.println(product.getPrice());
			cartItem.setUnitPrice(product.getPrice());
			
		}else {
			cartItem.setQuantity(cartItem.getQuantity() + quantity);
		}
		
		cartItem.setTotalPrice();
		cart.addItem(cartItem);
		
		this.cartItemRepo.save(cartItem);
		this.cartRepo.save(cart);
	}

	@Override
	public void removeItemFromCart(Long cartId, Long productId) {
		Cart cart = this.cartRepo.findById(cartId)
				.orElseThrow(()-> new ResourceNotFoundException("Cart","id",cartId));
		CartItem itemToRemove = getCartItem( cartId,  productId);
		
		cart.removeItem(itemToRemove);
		this.cartItemRepo.delete(itemToRemove);
		this.cartRepo.save(cart);
	}

	@Override
	public void updateItemQuantity(Long cartId, Long productId, int quantity) {
		Cart cart = this.cartRepo.findById(cartId).orElseThrow(()-> new ResourceNotFoundException("Cart","id",cartId));
		cart.getItems().stream().filter((item)->{
			return item.getProduct().getId().equals(productId);
		}).findFirst().ifPresent(item ->{
			item.setQuantity(quantity);
			item.setTotalPrice();
		});

		cart.updateTotalAmount();
		this.cartRepo.save(cart);
	}

	@Override
	public CartItem getCartItem(Long cartId, Long productId) {
		Cart cart = this.cartRepo.findById(cartId).orElseThrow(()-> new ResourceNotFoundException("Cart","id",cartId));
		
		CartItem cartItem = cart.getItems().stream().filter((item)->{
			return item.getProduct().getId().equals(productId);
		}).findFirst().orElseThrow(()-> new ResourceNotFoundException("Cart item","product id", productId));
		
		return cartItem;
	}
	
}
