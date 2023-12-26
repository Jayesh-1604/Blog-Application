package com.jb.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JacksonInject.Value;
import com.jb.blog.exception.ResourceNotFoundException;
import com.jb.blog.payloads.PostDto;
import com.jb.blog.payloads.PostResponse;
import com.jb.blog.service.impl.PostServiceImpl;
import com.jb.blog.utils.AppContants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/posts")
@Tag(
		name = "REST ENDPOINT's FOR POST RESOURCE",
		description = "Operation are reflected into DB."
	)
public class PostController {

	@Autowired
	PostServiceImpl impl;
	
	
	
	@Operation(
			summary = "ADD POST TO DB",
			description = "HTTP POST REQUEST"
			)
	@ApiResponse(
			responseCode = "201" ,
			description = "HttpStatus.CREATED"
			)
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto)
	{
		PostDto post = impl.createPost(postDto);
		
		return new ResponseEntity<>(post,HttpStatus.CREATED);
	}
	
	
	
	@Operation(
			summary = "GET ALL POST FROM DB",
			description = "HTTP GET REQUEST"
			)
	@ApiResponse(
			responseCode = "200" ,
			description = "HttpStatus.OK"
			)
	@GetMapping
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value = "pageNo", defaultValue = AppContants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue =AppContants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue =AppContants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue =AppContants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
	){
		PostResponse posts = impl.getAllPost(pageNo,pageSize,sortBy,sortDir);
		return new ResponseEntity<>(posts,HttpStatus.OK);
	}
	
	
	
	
	@Operation(
			summary = "GET POST BY POST ID",
			description = "HTTP GET REQUEST"
			)
	@ApiResponse(
			responseCode = "200" ,
			description = "HttpStatus.OK"
			)
	@GetMapping("/{id}")
	public ResponseEntity<PostDto> getPostById(@PathVariable("id") Long Id)
	{
		
		PostDto postDto = impl.findPostById(Id);
		return new ResponseEntity<>(postDto,HttpStatus.OK);
	}
	
	
	
	
	
	@Operation(
			summary = "GET ALL POST BY CATEGORY",
			description = "HTTP GET REQUEST"
			)
	@ApiResponse(
			responseCode = "200" ,
			description = "HttpStatus.OK"
			)
	@GetMapping("category/{id}")
	public ResponseEntity<List<PostDto>> getAllPostByCategoryId(@PathVariable("id") Long categoryId)
	{
		System.out.println("PostController CaID\t\t"+categoryId);
		List<PostDto> postDtos =  impl.getPostsByCategory(categoryId);
		System.out.println(postDtos);
		return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
	}
	
	
	
	
	@Operation(
			summary = "UPDATE POST BY POST ID",
			description = "HTTP PUT REQUEST"
			)
	@ApiResponse(
			responseCode = "201" ,
			description = "HttpStatus.CREATED"
			)
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<PostDto> updatePostById(@Valid @RequestBody PostDto postDto, @PathVariable("id") Long Id)
	{
		PostDto dto = impl.updatePostById(postDto, Id);
		return new ResponseEntity<>(dto,HttpStatus.CREATED);
	}
	
	
	
	@Operation(
			summary = "DELETE POST BY POST ID",
			description = "HTTP DEL REQUEST"
			)
	@ApiResponse(
			responseCode = "200" ,
			description = "HttpStatus.OK"
			)
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deletePostById(@PathVariable("id") Long Id)
	{
		impl.deletePostById(Id);
		return new ResponseEntity<>("Post with "+Id+" deleted successfully...",HttpStatus.OK);
	}
	
}
