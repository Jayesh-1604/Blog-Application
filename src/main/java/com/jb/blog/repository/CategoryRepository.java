package com.jb.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jb.blog.model.Category;
import com.jb.blog.model.Post;

public interface CategoryRepository extends JpaRepository<Category, Long> {


	@Query
	public List<Post> findByCategoryId(Long categoryId);

}
