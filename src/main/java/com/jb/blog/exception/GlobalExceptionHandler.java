package com.jb.blog.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.jb.blog.payloads.ErrorDetails;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorDetails> resourceNotFoundExceptionHandler(ResourceNotFoundException rnfe,WebRequest webRequest)
	{	
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),rnfe.getMessage(),"404 Not Found resourceNotFound",webRequest.getDescription(false));
		return new ResponseEntity<>(errorDetails,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(CommentForPostIdNotFoundException.class)
	public ResponseEntity<ErrorDetails> commentForPostIdNotFoundExceptionHandler(CommentForPostIdNotFoundException cfpnfe,WebRequest webRequest)
	{
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),cfpnfe.getMessage(),"404 Not Found commentForPostIdNotFound",webRequest.getDescription(false));
		return new ResponseEntity<>(errorDetails,HttpStatus.NOT_FOUND);
	}	
	

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			org.springframework.http.HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		
		Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) ->{
            String fieldName = ((FieldError)error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorDetails> accessDeniedExceptionHandler(AccessDeniedException e,WebRequest webRequest) {
		ErrorDetails details = new ErrorDetails(LocalDateTime.now(), e.getMessage(), "Unauthorized User",webRequest.getDescription(false));
		return new ResponseEntity<ErrorDetails>(details, HttpStatus.UNAUTHORIZED);
	}
	
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> allExceptionHandler(Exception e,WebRequest webRequest) {
		ErrorDetails details = new ErrorDetails(LocalDateTime.now(), e.getMessage(), "Problem in exeuction",webRequest.getDescription(false));
		return new ResponseEntity<ErrorDetails>(details, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	
	
}
