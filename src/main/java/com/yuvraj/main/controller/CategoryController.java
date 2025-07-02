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
import com.yuvraj.main.response.ApiResponse;
import com.yuvraj.main.serviceImpl.CategoryServiceImpl;

@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {
	
	@Autowired
	CategoryServiceImpl categoryService;
	
	@PostMapping("/add")
	public ResponseEntity<ApiResponse> createCategory(@RequestBody Category category){
		Category createdCategory = this.categoryService.addCategory(category);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Success", createdCategory), HttpStatus.CREATED);
	}
	
	
	@GetMapping("/all")
	public ResponseEntity<ApiResponse> getAllCategories(){
		List<Category> categories =  this.categoryService.getCategories();
		return new ResponseEntity<ApiResponse>(new ApiResponse("successfully fetched all categories", categories), HttpStatus.OK);
	}
	
	@GetMapping("/category/{id}")
	public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id){
		Category category = this.categoryService.getCategoryById(id);
		return new ResponseEntity<ApiResponse>(new ApiResponse(("Successfully fetched category with id= "+ id), category), HttpStatus.OK);
	}
	
	@GetMapping("/category")
	public ResponseEntity<ApiResponse> getCategoryByName(@RequestParam String name){
		Category category = this.categoryService.getCategoryByName(name);
		return new ResponseEntity<ApiResponse>(new ApiResponse(("Successfully fetched category with name= "+ name), category), HttpStatus.OK);
	}
	
	@DeleteMapping("/category/{id}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id){
		this.categoryService.deleteCategory(id);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Successfully deleted!",null), HttpStatus.OK);
	}
	
	
	@PutMapping("/category/{id}")
	public ResponseEntity<ApiResponse> updateCategory(@RequestBody Category category, @PathVariable Long id){
		Category updatedCategory = this.categoryService.updateCategory(category, id);
		
		return new ResponseEntity<>(new ApiResponse("Successfully updated", updatedCategory), HttpStatus.OK);
	}
	
	

}















