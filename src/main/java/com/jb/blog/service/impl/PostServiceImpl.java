package com.jb.blog.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.jb.blog.exception.ResourceNotFoundException;
import com.jb.blog.model.Category;
import com.jb.blog.model.Comment;
import com.jb.blog.model.Post;
import com.jb.blog.payloads.CommentDto;
import com.jb.blog.payloads.PostDto;
import com.jb.blog.payloads.PostResponse;
import com.jb.blog.repository.CategoryRepository;
import com.jb.blog.repository.PostRepository;
import com.jb.blog.service.PostService;


@Service
public class PostServiceImpl implements PostService {

	PostRepository postRepository;
	
	CategoryRepository categoryRepository;
	
	
	public PostServiceImpl(PostRepository postRepository, CategoryRepository categoryRepository)
	{
		this.categoryRepository = categoryRepository;
		this.postRepository = postRepository;
	}
	
	
	
	@Override
	public PostDto createPost(PostDto postDto) {
		
		Category category = categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));
		
		//PostDto to Post
		Post post = mapToPost(postDto);
		post.setCategory(category);
		
		Post newPost = postRepository.save(post);
		
		return mapToPostDto(newPost);
	}

	

	@Override
	public PostResponse getAllPost(int pageNo,int pageSize, String sortBy, String sortDir) {
		
		
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortBy).ascending():
			Sort.by(sortBy).descending();
		
		Pageable pageable = PageRequest.of(pageNo, pageSize,sort);
		
		Page<Post> posts = postRepository.findAll(pageable);
		
		List<Post> ListOfPosts = posts.getContent();
		
		List<PostDto> content =  ListOfPosts.stream().map(post -> getMapToPostDto(post)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(content);
		postResponse.setPageSize(posts.getSize());
		postResponse.setPageNo(posts.getNumber());
		postResponse.setTotalPages(posts.getTotalPages());
		postResponse.setTotalElements(posts.getNumberOfElements());
		postResponse.setFirst(posts.isFirst());
		postResponse.setLast(posts.isLast());
		
		return postResponse;
	
	}
	

	
	@Override
	public PostDto findPostById(Long Id){
		Optional<Post> post = postRepository.findById(Id);
		PostDto postDto = null;
		if(post.isEmpty())
		{
			throw new ResourceNotFoundException("Post", "ID", Id);
		}
		else
		{
			postDto = getMapToPostDto(post.get());
		}
		return postDto;
	}
	

	@Override
	public PostDto updatePostById(PostDto postDto, Long Id) {

		Optional<Post> post = postRepository.findById(Id);
		Post postt = null;
		
		Category category = categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));
		
		
		if(post.isEmpty())
		{
			throw new ResourceNotFoundException("Post", "ID", Id);
		}
		else
		{
			postt = post.get();
			postt.setTitle(postDto.getTitle());
			postt.setDescription(postDto.getDescription());
			postt.setContent(postDto.getContent());
			postt.setCategory(category);
			postt = postRepository.save(postt);
		}
		return mapToPostDto(postt);
	}
	

	@Override
	public void deletePostById(Long Id) {
		
		Optional<Post> post = postRepository.findById(Id);
		
		if(post.isEmpty())
		{
			throw new ResourceNotFoundException("Post", "ID", Id);
		}
		else
		{
			Post postt = post.get();
			postRepository.delete(postt);
		}		
	}
	
	
	@Override
    public List<PostDto> getPostsByCategory(Long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        System.out.println("PostServiceImpl\t"+category.getCategoryDescription()+":"+category.getCategoryId());
        List<Post> posts = postRepository.findByCategoryCategoryId(categoryId);
        
        return posts.stream().map((post)->getMapToPostDto(post)).collect(Collectors.toList());
	}
	
	
	private PostDto getMapToPostDto(Post post)
	{
		PostDto postDto = new PostDto();
		postDto.setId(post.getId());
		postDto.setTitle(post.getTitle());
		postDto.setDescription(post.getDescription());
		postDto.setContent(post.getContent());
		Set<Comment> comments = post.getComment();
		CommentServiceImple im = new CommentServiceImple();
		Set<CommentDto> cDto = comments.stream().map((comment)->im.mapToDto(comment)).collect(Collectors.toSet());
		postDto.setComments(cDto);
		postDto.setCategoryId(post.getCategory().getCategoryId());
		return postDto;
	}
	
	
	private PostDto mapToPostDto(Post post)
	{
		PostDto postDto = new PostDto();
		postDto.setId(post.getId());
		postDto.setTitle(post.getTitle());
		postDto.setDescription(post.getDescription());
		postDto.setContent(post.getContent());
		postDto.setCategoryId(post.getCategory().getCategoryId());
		return postDto;
		
	}
	
	
	
	
	private Post mapToPost(PostDto postDto)
	{
		Post post = new Post();
		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setContent(postDto.getContent());
		return post;
	}



}
