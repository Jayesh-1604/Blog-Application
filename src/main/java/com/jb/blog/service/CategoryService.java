package com.jb.blog.service;

import java.util.List;

import com.jb.blog.payloads.CategoryDto;

public interface CategoryService {
	CategoryDto addCategory(CategoryDto categoryDto);

    CategoryDto getCategory(Long categoryId);

    List<CategoryDto> getAllCategories();

    CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId);

    CategoryDto deleteCategory(Long categoryId);

}
