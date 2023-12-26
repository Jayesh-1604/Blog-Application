package com.jb.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jb.blog.model.Comment;

public interface CommentsRespository extends JpaRepository<Comment, Long> {

	@Query
	public List<Comment> findByPostId(long postId); 
	
	@Query
	public Comment deleteById(long commentId);
}
