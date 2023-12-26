package com.jb.blog.payloads;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


//This class is used for client to get all information about Fetching all posts.
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {


	private List<PostDto> content;
	private int pageNo;
	private int pageSize;
	private long totalElements;
	private int totalPages;
	private boolean isFirst;
	private boolean isLast;
}
