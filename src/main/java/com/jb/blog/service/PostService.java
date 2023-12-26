package com.jb.blog.service;



import java.util.List;

import com.jb.blog.payloads.PostDto;
import com.jb.blog.payloads.PostResponse;

public interface PostService {

	public PostDto createPost(PostDto postDto);
	public PostResponse getAllPost(int pageNo, int pageSize,String sortBy, String sortDir);
	public PostDto findPostById(Long Id);
	public PostDto updatePostById(PostDto postDto, Long Id);
	public void deletePostById(Long Id);
	public List<PostDto> getPostsByCategory(Long categoryId);
	
}


