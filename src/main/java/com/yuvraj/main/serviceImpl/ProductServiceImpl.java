package com.yuvraj.main.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuvraj.main.dto.ProductDto;
import com.yuvraj.main.exception.AlreadyExistsException;
import com.yuvraj.main.exception.ResourceNotFoundException;
import com.yuvraj.main.models.Category;
import com.yuvraj.main.models.Product;
import com.yuvraj.main.repositories.CategoryRepository;
import com.yuvraj.main.repositories.ProductRepository;
import com.yuvraj.main.request.AddProductRequest;
import com.yuvraj.main.request.UpdateProductRequest;
import com.yuvraj.main.services.ProductService;

@Service
public class ProductServiceImpl implements ProductService{
	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private CategoryRepository categoryRepo;
	
	@Autowired
	ModelMapper modelMapper;

	@Override
	public ProductDto addProduct(AddProductRequest addProductRequest) {
		// check if the product is in the db
		//if yes, save the product
		// if not , save the category first
		// then save the product
		if(this.productExists(addProductRequest.getName(), addProductRequest.getBrand())) {
			throw new AlreadyExistsException(addProductRequest.getBrand() + " " + addProductRequest.getName()   + " already exists");
		}
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
		
		return this.modelMapper.map(savedProduct, ProductDto.class);
	}
	
	
	private boolean productExists(String name, String brand) {
		return this.productRepo.existsByNameAndBrand(name, brand);
	}

	@Override
	public ProductDto getProductById(Long productId) {
		Product product = this.productRepo.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product","id",productId));
		return this.modelMapper.map(product, ProductDto.class);
	}

	@Override
	public ProductDto updateProduct(UpdateProductRequest updateProductRequest, Long productId) {
		// check if the product we want to update exists or not
		Product product = this.productRepo.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product","id",productId));
		
		// map the product from request to the existing product
		this.modelMapper.map(updateProductRequest, product);
		// updateProductRequest does not have id so when we map, the product will not have the id 
		// or else we need to send id in through updateProductRequest
		product.setId(productId);
		// save the product
		Product savedProduct = this.productRepo.save(product);
		return this.modelMapper.map(savedProduct, ProductDto.class);
	}

	@Override
	public void deleteProduct(Long productId) {
		this.productRepo.findById(productId)
					.ifPresentOrElse(productRepo::delete, ()-> new ResourceNotFoundException("Product","id",productId));
	}

	@Override
	public List<ProductDto> getAllProducts() { 
		List<Product> products = this.productRepo.findAll();
		List<ProductDto> productDtos = new ArrayList<>();
		
		if(products.isEmpty()) {
			throw new ResourceNotFoundException("There is no products");
		}else {
			for(Product product: products) {
				productDtos.add(this.modelMapper.map(product, ProductDto.class));
			}
			
			return productDtos;
		}
	}

	@Override
	public List<ProductDto> getProductsByCategory(String category) { 
		List<Product> products = this.productRepo.findByCategoryName(category);
List<ProductDto> productDtos = new ArrayList<>();
		
		if(products.isEmpty()) {
			throw new ResourceNotFoundException("There is no products");
		}else {
			for(Product product: products) {
				productDtos.add(this.modelMapper.map(product, ProductDto.class));
			}
			
			return productDtos;
		}
	}

	@Override
	public List<ProductDto> getProductsByBrand(String brand) {
		List<Product> products =  this.productRepo.findByBrand(brand);
List<ProductDto> productDtos = new ArrayList<>();
		
		if(products.isEmpty()) {
			throw new ResourceNotFoundException("There is no products");
		}else {
			for(Product product: products) {
				productDtos.add(this.modelMapper.map(product, ProductDto.class));
			}
			
			return productDtos;
		}
		
	}

	@Override
	public List<ProductDto> getProductsByCategoryAndBrand(String category, String brand) {
		List<Product> products =  this.productRepo.findByCategoryNameAndBrand(category,brand);
List<ProductDto> productDtos = new ArrayList<>();
		
		if(products.isEmpty()) {
			throw new ResourceNotFoundException("There is no products");
		}else {
			for(Product product: products) {
				productDtos.add(this.modelMapper.map(product, ProductDto.class));
			}
			
			return productDtos;
		}
	}

	@Override
	public List<ProductDto> getProductByName(String name) {
		List<Product> products =  this.productRepo.findByName(name); 
List<ProductDto> productDtos = new ArrayList<>();
		
		if(products.isEmpty()) {
			throw new ResourceNotFoundException("There is no products");
		}else {
			for(Product product: products) {
				productDtos.add(this.modelMapper.map(product, ProductDto.class));
			}
			
			return productDtos;
		}
	}

	@Override
	public List<ProductDto> getProductsByBrandAndName(String brand, String name) {
		List<Product> products =  this.productRepo.findByBrandAndName(brand, name);
List<ProductDto> productDtos = new ArrayList<>();
		
		if(products.isEmpty()) {
			throw new ResourceNotFoundException("There is no products");
		}else {
			for(Product product: products) {
				productDtos.add(this.modelMapper.map(product, ProductDto.class));
			}
			
			return productDtos;
		}
	}
	
	@Override
	public Long countProductsByBrandAndName(String brand, String name) {
		return this.productRepo.countByBrandAndName(brand, name);
	}

}
