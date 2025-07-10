package com.yuvraj.main.serviceImpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.yuvraj.main.exception.ResourceNotFoundException;
import com.yuvraj.main.models.Category;
import com.yuvraj.main.models.Product;
import com.yuvraj.main.repositories.CategoryRepository;
import com.yuvraj.main.repositories.ProductRepository;
import com.yuvraj.main.request.AddProductRequest;
import com.yuvraj.main.request.UpdateProductRequest;
import com.yuvraj.main.services.ProductService;


public class ProductServiceImpl implements ProductService{
	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private CategoryRepository categoryRepo;
	
	@Autowired
	ModelMapper modelMapper;

	@Override
	public Product addProduct(AddProductRequest addProductRequest) {
		// check if the product is in the db
		//if yes, save the product
		// if not , save the category first
		// then save the product
		Category category = this.categoryRepo.findByName(addProductRequest.getCategory().getName());
		if(category == null) {
			category = this.categoryRepo.save(new Category(addProductRequest.getCategory().getName()));
		}
		
		Product product = this.modelMapper.map(addProductRequest, Product.class);
		//if i use only modelmapper then the category will be set to product but 
		//that category will not be the one which i saved using categoryRepository
		// so i need to use the setter to set the category which has been saved
		
		product.setCategory(category);
		Product savedProduct = this.productRepo.save(product);
		
		return savedProduct;
	}

	@Override
	public Product getProductById(Long productId) {
		Product product = this.productRepo.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product","id",productId));
		return product;
	}

	@Override
	public Product updateProduct(UpdateProductRequest updateProductRequest, Long productId) {
		// check if the product we want to update exists or not
		Product product = this.productRepo.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product","id",productId));
		
		// map the product from request to the existing product
		this.modelMapper.map(updateProductRequest, product);
		
		// save the product
		Product savedProduct = this.productRepo.save(product);
		return savedProduct;
	}

	@Override
	public void deleteProduct(Long productId) {
		this.productRepo.findById(productId)
					.ifPresentOrElse(productRepo::delete, ()-> new ResourceNotFoundException("Product","id",productId));
	}

	@Override
	public List<Product> getAllProducts() {
		return this.productRepo.findAll();
	}

	@Override
	public List<Product> getProductsByCategory(String category) {
		return this.productRepo.findByCategoryName(category);
	}

	@Override
	public List<Product> getProductsByBrand(String brand) {
		return this.productRepo.findByBrand(brand);
	}

	@Override
	public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
		return this.productRepo.findByCategoryNameAndBrand(category,brand);
	}

	@Override
	public List<Product> getProductByName(String name) {
		return this.productRepo.findByName(name); 
	}

	@Override
	public List<Product> getProductsByBrandAndName(String brand, String name) {
		return this.productRepo.findByBrandAndName(brand, name);
	}
	
	@Override
	public Long countProductsByBrandAndName(String brand, String name) {
		return this.productRepo.countByBrandAndName(brand, name);
	}

}
