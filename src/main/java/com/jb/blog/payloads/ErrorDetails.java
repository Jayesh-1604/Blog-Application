package com.jb.blog.payloads;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDetails {
	private LocalDateTime time;
	private String msg;
	private String status;
	private String details;

}
