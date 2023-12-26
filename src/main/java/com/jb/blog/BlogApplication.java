package com.jb.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;



@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Blog Application",
				description = "This Blog Application build on Java v17 & Spring MVC",
				version = "v1.0",
				
				contact = @Contact(
						name = "Jayesh Wani",
						email = "jayeshbwani57@gmail.com",
						url = "https://www.linkedin.com/in/jayeshwani/"
				),
				
				license = @License(
						name = "Apache 2.0",
						url = "https://www.apache.org/licenses/LICENSE-2.0"
				)
			),
				
			externalDocs = @ExternalDocumentation(
					description = "Blog Application",
					url = "https://github.com/Jayesh-1604/"
			)
				
)

public class BlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}

}
