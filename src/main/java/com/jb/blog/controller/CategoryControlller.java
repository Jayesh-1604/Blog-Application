package com.jb.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.jb.blog.payloads.CategoryDto;
import com.jb.blog.service.impl.CategoryServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/category")
@Tag(
		name = "REST ENDPOINT's FOR CATEGORY RESOURCE",
		description = "Operation are reflected into DB."
	)
public class CategoryControlller {
	
	@Autowired
	CategoryServiceImpl categoryServiceImpl;
	
	
	
	
	@Operation(
			summary = "GET A CATEGORY BY CATEGORY ID",
			description = "HTTP GET REQUEST"
			)
	@ApiResponse(
			responseCode = "200" ,
			description = "HttpStatus.OK"
			)
	@GetMapping("/{id}")
	public ResponseEntity<CategoryDto> getCategoryById(@PathVariable(name = "id") Long categoryId)
	{
		return new ResponseEntity<CategoryDto>(categoryServiceImpl.getCategory(categoryId),HttpStatus.OK);
	}
	
	
	
	@Operation(
			summary = "GET ALL CATEGORY FROM DB",
			description = "HTTP GET REQUEST"
			)
	@ApiResponse(
			responseCode = "200" ,
			description = "HttpStatus.OK"
			)
	@GetMapping
	public ResponseEntity<List<CategoryDto>> getAllCategory()
	{
		return new ResponseEntity<List<CategoryDto>>(categoryServiceImpl.getAllCategories(),HttpStatus.OK);
	}
	
	
	
	@Operation(
			summary = "ADD NEW CATEGORY TO DB",
			description = "HTTP POST REQUEST"
			)
	@ApiResponse(
			responseCode = "201" ,
			description = "HttpStatus.CREATED"
			)
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CategoryDto> addCategory(@Valid @RequestBody CategoryDto categoryDto)
	{
		return new ResponseEntity<CategoryDto>(categoryServiceImpl.addCategory(categoryDto),HttpStatus.CREATED) ;
	}
	
	
	
	@Operation(
			summary = "UPDATE CATEGORY BY CATEGORY ID",
			description = "HTTP PUT REQUEST"
			)
	@ApiResponse(
			responseCode = "201" ,
			description = "HttpStatus.CREATED"
			)
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,@PathVariable(name = "id") Long categoryId )
	{
		return new ResponseEntity<CategoryDto>(categoryServiceImpl.updateCategory(categoryDto,categoryId),HttpStatus.CREATED) ;
	}
	
	
	
	@Operation(
			summary = "DELETE CATEGORY BY CATEGORY ID",
			description = "HTTP DEL REQUEST"
			)
	@ApiResponse(
			responseCode = "200" ,
			description = "HttpStatus.OK"
			)
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")	
	public ResponseEntity<CategoryDto> deleteCategory(@PathVariable(name = "id") Long categoryId)
	{
		return new ResponseEntity<CategoryDto>(categoryServiceImpl.deleteCategory(categoryId),HttpStatus.OK);
	}
	
	

}
