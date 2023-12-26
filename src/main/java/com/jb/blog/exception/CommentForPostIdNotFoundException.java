package com.jb.blog.exception;

public class CommentForPostIdNotFoundException extends RuntimeException {

	private long PostId;
	private long commentId;
	
	
	public CommentForPostIdNotFoundException(long postId,long commentId) {
		super("Comments with ID :"+commentId+" is not found for Post with ID: "+postId);
	
		this.PostId = postId;
		this.commentId = commentId;
	}


	public long getPostId() {
		return PostId;
	}


	public long getCommentId() {
		return commentId;
	}


}
