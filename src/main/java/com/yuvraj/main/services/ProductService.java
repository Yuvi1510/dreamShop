package com.yuvraj.main.services;

import java.util.List;

import com.yuvraj.main.models.Product;
import com.yuvraj.main.request.AddProductRequest;
import com.yuvraj.main.request.UpdateProductRequest;

public interface ProductService {
	
	Product addProduct(AddProductRequest addProductRequest);
	Long countProductsByBrandAndName(String brand, String name);
	Product getProductById(Long productId);
	Product updateProduct(UpdateProductRequest product, Long productId);
	void deleteProduct(Long productId);
	List<Product> getAllProducts();
	List<Product> getProductsByCategory(String category);
	List<Product> getProductsByBrand(String brand);
	List<Product> getProductsByCategoryAndBrand(String category, String brand);
	List<Product> getProductByName(String name);
	List<Product> getProductsByBrandAndName(String brand, String name);
}
