package com.yuvraj.main.services;

import java.util.List;

import com.yuvraj.main.dto.ProductDto;
import com.yuvraj.main.models.Product;
import com.yuvraj.main.request.AddProductRequest;
import com.yuvraj.main.request.UpdateProductRequest;

public interface ProductService {
	
	ProductDto addProduct(AddProductRequest addProductRequest);
	Long countProductsByBrandAndName(String brand, String name);
	ProductDto getProductById(Long productId);
	ProductDto updateProduct(UpdateProductRequest product, Long productId);
	void deleteProduct(Long productId);
	List<ProductDto> getAllProducts();
	List<ProductDto> getProductsByCategory(String category);
	List<ProductDto> getProductsByBrand(String brand);
	List<ProductDto> getProductsByCategoryAndBrand(String category, String brand);
	List<ProductDto> getProductByName(String name);
	List<ProductDto> getProductsByBrandAndName(String brand, String name);
}
