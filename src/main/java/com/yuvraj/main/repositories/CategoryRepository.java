package com.yuvraj.main.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yuvraj.main.models.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	Category findByName(String name);

	boolean existsByName(String name);

}
