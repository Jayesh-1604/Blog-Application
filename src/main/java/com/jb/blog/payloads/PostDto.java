package com.jb.blog.payloads;

import java.util.Set;

import com.jb.blog.model.Comment;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostDto {

	private Long Id;
	
	
	// Title - Should not be null 
    // Title - Should not be less than 2 characters
	@NotEmpty
	@Size(min = 2, message = "Title shoud contains atleast 2 characters.")
	private String title;
	
	
	// Description - Should not be null 
    // Description - Should not be less than 10 characters
	@NotEmpty
	@Size(min = 10, message = "Description shoud contains atleast 10 characters.")
	private String description;
	
	// Content - Should not be null 
    // Content - Should not be less than 10 characters
	@NotEmpty
	@Size(min = 15, message = "Description shoud contains atleast 15 characters.")
	private String content;
	
    private Set<CommentDto> comments;

    private Long categoryId;
}

