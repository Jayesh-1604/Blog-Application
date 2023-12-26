package com.jb.blog.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

	private long id;
	
	@NotEmpty
	@Size(min = 5, message = "Name should not be less than 5 characters")
	private String name;

	@Email
	@NotEmpty
	private String email;
	
	@NotEmpty
	@Size(min = 10, message = "Body should not be less than 10 characters")
	private String body;
}
