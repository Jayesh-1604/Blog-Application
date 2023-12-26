package com.jb.blog.service;

import java.util.List;

import com.jb.blog.payloads.CommentDto;

public interface CommentService {
	
	public CommentDto createComment(long postId, CommentDto commentDto);
	public List<CommentDto> getAllCommentsByPostId(long postId);
	public CommentDto getCommentbyPostId(long postId, long commentId);
	public CommentDto updateCommentByPostId(long postId, long commentId, CommentDto commentDto);
	public CommentDto deleteCommentByPostId(long postId, long commentId);
	
}
