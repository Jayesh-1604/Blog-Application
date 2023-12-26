package com.jb.blog.service.impl;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.jb.blog.exception.ResourceNotFoundException;
import com.jb.blog.model.Category;
import com.jb.blog.payloads.CategoryDto;
import com.jb.blog.repository.CategoryRepository;
import com.jb.blog.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	CategoryRepository categoryRepository;
	
	public CategoryServiceImpl(CategoryRepository categoryRepository)
	{
		this.categoryRepository = categoryRepository;
	}
	
	@Override
	public CategoryDto addCategory(CategoryDto categoryDto) {
		Category category = maptoModel(categoryDto);
		Category savedCategory =  categoryRepository.save(category);
		return maptoDto(savedCategory);
	}

	@Override
	public CategoryDto getCategory(Long categoryId) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "ID", categoryId));
		
		return maptoDto(category);
	}

	@Override
	public List<CategoryDto> getAllCategories() {
		
		List<Category> categories = categoryRepository.findAll();
	    List<CategoryDto> categoryDtos = categories
	    		.stream()
	    		.map((category)-> maptoDto(category))
	    		.collect(Collectors.toList()); 
		
		return categoryDtos;
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId) {
		
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Categorsy", "ID", categoryId));
		
		Category newCategory = maptoModel(categoryDto);
		newCategory.setCategoryId(category.getCategoryId());
		newCategory =  categoryRepository.save(newCategory);
		return maptoDto(newCategory);
		
	}

	@Override
	public CategoryDto deleteCategory(Long categoryId) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Categorsy", "ID", categoryId));
		
		categoryRepository.deleteById(categoryId);
		
		return maptoDto(category);
	}
	
	public static CategoryDto maptoDto(Category category)
	{
		CategoryDto cDto = new CategoryDto();
		cDto.setCategoryId(category.getCategoryId());
		cDto.setCategoryName(category.getCategoryName());
		cDto.setCategoryDescription(category.getCategoryDescription());
		return cDto;
	}
	
	public static Category maptoModel(CategoryDto categoryDto)
	{
		Category category = new Category();
		category.setCategoryId(categoryDto.getCategoryId());
		category.setCategoryName(categoryDto.getCategoryName());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		return category;
	}

}
