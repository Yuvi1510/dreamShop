package com.yuvraj.main.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yuvraj.main.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

//	this will map with the name field in the category class
	List<Product> findByCategoryName(String category);

	List<Product> findByBrand(String brand);

	List<Product> findByCategoryNameAndBrand(String category, String brand);

	List<Product> findByName(String name);

	List<Product> findByBrandAndName(String brand, String name);

	Long countByBrandAndName(String brand, String name);

	boolean existsByNameAndBrand(String name, String brand);

}
