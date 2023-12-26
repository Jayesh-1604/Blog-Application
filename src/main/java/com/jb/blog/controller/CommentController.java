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

import com.jb.blog.payloads.CommentDto;
import com.jb.blog.service.impl.CommentServiceImple;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/posts")
@Tag(
		name = "REST ENDPOINT's FOR COMMENT RESOURCE",
		description = "Operation are reflected into DB."
	)
public class CommentController {

	@Autowired
	private CommentServiceImple commentServiceImple;
	
	
	
	@Operation(
			summary = "ADD COMMENT TO POST",
			description = "HTTP POST REQUEST"
			)
	@ApiResponse(
			responseCode = "201" ,
			description = "HttpStatus.CREATED"
			)
	@PostMapping("/{postId}/comments")
	@PreAuthorize("hasRole('ADMIN')")
	ResponseEntity<CommentDto> createComment(@PathVariable("postId") long postId ,
			@Valid @RequestBody CommentDto commentDto)
	{
		return new ResponseEntity<CommentDto>(commentServiceImple.createComment(postId, commentDto), HttpStatus.CREATED);
	}
	
	
	
	@Operation(
			summary = "GET ALL COMMENT OF POST BY POST ID",
			description = "HTTP GET REQUEST"
			)
	@ApiResponse(
			responseCode = "200" ,
			description = "HttpStatus.OK"
			)
	@GetMapping("/{postId}/comments")
	ResponseEntity<List<CommentDto>> getCommentsById(@PathVariable("postId") long postId)
	{
		
		return new ResponseEntity<>(commentServiceImple.getAllCommentsByPostId(postId),HttpStatus.OK);
	}
	
	
	
	
	@Operation(
			summary = "GET A COMMENT OF POST BY COMMENT & POST ID's",
			description = "HTTP GET REQUEST"
			)
	@ApiResponse(
			responseCode = "200" ,
			description = "HttpStatus.OK"
			)
	@GetMapping("/{postId}/comment/{commentId}")
	ResponseEntity<CommentDto> findCommentByPostId(@PathVariable("postId") long postId,
		 @PathVariable("commentId")long commentId)
	{
		System.out.println("Controller : POSTID :"+postId+" "+"CommentId "+ commentId);
		return new ResponseEntity<>(commentServiceImple.getCommentbyPostId(postId, commentId),HttpStatus.OK);
	}
	
	
	
	
	@Operation(
			summary = "UPDATE COMMENT  BY COMMENT & POST ID's",
			description = "HTTP PUT REQUEST"
			)
	@ApiResponse(
			responseCode = "201" ,
			description = "HttpStatus.CREATED"
			)
	@PutMapping("/{postId}/comments/{commentId}")
	@PreAuthorize("hasRole('ADMIN')")
	ResponseEntity<CommentDto> UpdateCommentById(@PathVariable("postId") long postId,
			 @PathVariable("commentId")	long commentId, @Valid @RequestBody CommentDto commentDto)
	{
		System.out.println("Controller:\t"+postId+"\t\t"+commentId);
		System.out.println("Cntroller: CommentDto\t"+commentDto);
		return new ResponseEntity<>(commentServiceImple.updateCommentByPostId(postId, commentId, commentDto),HttpStatus.CREATED);
		
	}
	
	
	
	
	@Operation(
			summary = "DELETE COMMENT  BY COMMENT ID's",
			description = "HTTP DEL REQUEST"
			)
	@ApiResponse(
			responseCode = "200" ,
			description = "HttpStatus.OK"
			)
	@DeleteMapping("/{postId}/comment/{commentId}")
	@PreAuthorize("hasRole('ADMIN')")
	ResponseEntity<CommentDto> DeleteCommentByPostId(@PathVariable("postId") long postId,
			 @PathVariable("commentId")	long commentId)
	{
		return new ResponseEntity<>(commentServiceImple.deleteCommentByPostId(postId, commentId),HttpStatus.OK);
	}
	
	
	
}
