package com.jb.blog.payloads;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
	
    private Long categoryId;
    
    @NotNull
    @Size(min = 5, message = "Title shoud contains atleast 5 characters.")
    private String categoryName;
    
    @NotNull
    @Size(min = 10, message = "Description shoud contains atleast 10 characters.")
    private String categoryDescription;

}
