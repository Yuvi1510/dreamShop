package com.yuvraj.main.services;

import java.util.List;

import com.yuvraj.main.models.Category;

public interface CategoryService {
	Category addCategory(Category category);
	void deleteCategory(Long id);
	Category updateCategory(Category category, Long id);
	Category getCategoryById(Long id);
	Category getCategoryByName(String name);
	List<Category> getCategories();
	
}
