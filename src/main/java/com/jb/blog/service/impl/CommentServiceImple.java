package com.jb.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jb.blog.exception.CommentForPostIdNotFoundException;
import com.jb.blog.exception.ResourceNotFoundException;
import com.jb.blog.model.Comment;
import com.jb.blog.model.Post;
import com.jb.blog.payloads.CommentDto;
import com.jb.blog.repository.CommentsRespository;
import com.jb.blog.repository.PostRepository;
import com.jb.blog.service.CommentService;

@Service
public class CommentServiceImple implements CommentService {

	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private CommentsRespository commentsRespository;
	
	
	@Override
	public CommentDto createComment(long postId, CommentDto commentDto) {
		
		Comment comment = mapToModel(commentDto);
		
		Post post = postRepository.findById(postId)
				.orElseThrow(()->new ResourceNotFoundException("post", "ID", postId));
		
		comment.setPost(post);
		commentsRespository.save(comment);
		
		return mapToDto(comment);
	}
	

	@Override
	public List<CommentDto> getAllCommentsByPostId(long postId) {
		
		List<Comment> comments = commentsRespository.findByPostId(postId);
		if(comments.isEmpty())
		{
			throw new ResourceNotFoundException("post", "ID", postId);
		}
				
		return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
	} 


	
	@Override
	public CommentDto getCommentbyPostId(long postId, long commentId) {
		Post post = postRepository.findById(postId)
				.orElseThrow(()->new ResourceNotFoundException("post", "ID", postId));
		
		Comment comment = commentsRespository.findById(commentId)
				.orElseThrow(()-> new ResourceNotFoundException("Comment", "ID", commentId));
		
		
		if(!comment.getPost().getId().equals(post.getId()))
		{
			throw new CommentForPostIdNotFoundException(postId, commentId);
		}
		
		
		return mapToDto(comment);
	}

	
	@Override
	public CommentDto updateCommentByPostId(long postId, long commentId, CommentDto commentDto) {
		Post post = postRepository.findById(postId)
				.orElseThrow(()->new ResourceNotFoundException("post", "ID", postId));
		
		Comment comment = commentsRespository.findById(commentId)
				.orElseThrow(()-> new ResourceNotFoundException("Comments", "ID", commentId));
		
		if(!comment.getPost().getId().equals(post.getId()))
		{
			throw new CommentForPostIdNotFoundException(postId, commentId);
		}
		
		comment.setId(commentDto.getId());
		comment.setName(commentDto.getName());
		comment.setEmail(commentDto.getEmail());
		comment.setBody(commentDto.getBody());
		
		comment = commentsRespository.save(comment);
		
		System.out.println("Service:\t"+postId+"\t\t"+commentId);
		System.out.println("Service: Post\t"+post);
		System.out.println("Service: CommentDto\t"+comment);
		
		return mapToDto(comment);
	}
	
	
	@Override
	public CommentDto deleteCommentByPostId(long postId, long commentId) {
		Post post = postRepository.findById(postId)
				.orElseThrow(()->new ResourceNotFoundException("post", "ID", postId));
		
		Comment comment = commentsRespository.findById(commentId)
				.orElseThrow(()-> new ResourceNotFoundException("Comment", "ID", commentId));
		System.out.println(comment.toString()+"\n\n"+post.toString());
		if(!comment.getPost().getId().equals(post.getId()))
		{
			throw new CommentForPostIdNotFoundException(postId, commentId);
		}
		//System.out.println("Jayesh Wani....");
		//System.out.println("JPA RETURN:"+commentsRespository.deleteById(commentId));
		commentsRespository.delete(comment);
		
		return mapToDto(comment);
	}

	
	
	public CommentDto mapToDto(Comment comment)
	{
		CommentDto cDto = new CommentDto();
		cDto.setId(comment.getId());
		cDto.setName(comment.getName());
		cDto.setEmail(comment.getEmail());
		cDto.setBody(comment.getBody());
		return cDto;
	}
	
	public Comment mapToModel(CommentDto cDto)
	{
		Comment comment = new Comment();
		comment.setName(cDto.getName());
		comment.setEmail(cDto.getEmail());
		comment.setBody(cDto.getBody());
		return comment;
	}

}
