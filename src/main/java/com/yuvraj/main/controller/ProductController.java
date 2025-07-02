package com.yuvraj.main.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yuvraj.main.models.Category;
import com.yuvraj.main.models.Product;
import com.yuvraj.main.request.AddProductRequest;
import com.yuvraj.main.request.UpdateProductRequest;
import com.yuvraj.main.response.ApiResponse;
import com.yuvraj.main.serviceImpl.CategoryServiceImpl;
import com.yuvraj.main.serviceImpl.ProductServiceImpl;

@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {
	
	@Autowired
	private ProductServiceImpl productService;
	
	
	@PostMapping("")
	public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest productRequest){
		
		Product savedProduct = this.productService.addProduct(productRequest);
		
		return new ResponseEntity<>(new ApiResponse("product successfully added",savedProduct),HttpStatus.CREATED);
	}
	
	@GetMapping("")
	public ResponseEntity<ApiResponse> getAllProducts(){
		List<Product> products = this.productService.getAllProducts();
		return new ResponseEntity<ApiResponse>(new ApiResponse("Fetched all products",products), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse> getProductById(@PathVariable Long id){
		Product product = this.productService.getProductById(id);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Fetched product with id"+id, product), HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse> updateProduct(@RequestBody UpdateProductRequest productRequest, @PathVariable Long id){
		Product updatedProduct = this.productService.updateProduct(productRequest, id);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("Product Successfully updated",updatedProduct),HttpStatus.OK);
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id){
		this.productService.deleteProduct(id);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Product successfully deleted",null), HttpStatus.OK);
	} 
	
	@GetMapping("/by-category")
	public ResponseEntity<ApiResponse> getProductsByCategory(@RequestParam String category){
		List<Product> products = this.productService.getProductsByCategory(category);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Successfully fetched product of category "+category, products), HttpStatus.OK);
	}

	@GetMapping("/by-brand")
	public ResponseEntity<ApiResponse> getProductsByBrand(@RequestParam String brand){
		List<Product> products = this.productService.getProductsByCategory(brand);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Successfully fetched product of category "+brand, products), HttpStatus.OK);
	}
	
	
	@GetMapping("/by-category-brand")
	public ResponseEntity<ApiResponse> getProductsByCategoryAndBrand(@RequestParam String category, @RequestParam String brand){
		List<Product> products = this.productService.getProductsByCategoryAndBrand(category, brand);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Successfully fetched products of category "+category+" and brand "+brand, products), HttpStatus.OK);
	}
	
	@GetMapping("/by-name")
	public ResponseEntity<ApiResponse> getProductsByName(@RequestParam String name){
		List<Product> products = this.productService.getProductByName(name);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Successfully fetched product of category "+name, products), HttpStatus.OK);
	}

	
	@GetMapping("/by-brand-name")
	public ResponseEntity<ApiResponse> getProductsByBrandAndName(@RequestParam String brand, @RequestParam String name){
		List<Product> products = this.productService.getProductsByBrandAndName(brand, name);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Successfully fetched products of brand "+brand+" and name "+name, products),HttpStatus.OK);
	}

}























