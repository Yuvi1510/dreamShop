package com.yuvraj.main.serviceImpl;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuvraj.main.exception.AlreadyExistsException;
import com.yuvraj.main.exception.ResourceNotFoundException;
import com.yuvraj.main.models.Category;
import com.yuvraj.main.repositories.CategoryRepository;
import com.yuvraj.main.services.CategoryService;


@Service
public class CategoryServiceImpl implements CategoryService{
	@Autowired
	CategoryRepository categoryRepo;
	
	@Autowired
	ModelMapper modelMapper;

	@Override
	public Category addCategory(Category category) {
		return Optional.of(category)
				.filter(c -> !this.categoryRepo.existsByName(c.getName()))
				.map(this.categoryRepo::save)
				.orElseThrow(()-> new AlreadyExistsException("Category","name",category.getName()));
	}

	@Override
	public void deleteCategory(Long id) {
		this.categoryRepo.findById(id).ifPresentOrElse(categoryRepo::delete, ()->{ throw new ResourceNotFoundException("Category","id",id);});
	}

	@Override
	public Category updateCategory(Category category, Long id) {
		Category existingCategory = this.categoryRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category","id",id));
		
		existingCategory.setName(category.getName());;
		this.categoryRepo.save(existingCategory);
		return existingCategory;
	}

	@Override
	public Category getCategoryById(Long id) {
		Category category = this.categoryRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category","id",id));
		return category;
	}

	@Override
	public Category getCategoryByName(String name) {
		Category category = this.categoryRepo.findByName(name);
		
		if(category == null) {
			throw new ResourceNotFoundException("Category","name",name);
		}
		return category;
	}

	@Override
	public List<Category> getCategories() {
		return this.categoryRepo.findAll();
	}

}
