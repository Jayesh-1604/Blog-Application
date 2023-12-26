package com.jb.blog.SecurityConfig;

import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {

	//Spring security can not accept password in plain text for user's hence we have to encode password details
	
	@Bean
	public static PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	
	
	
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception
	{
		/*
		 This Is used for all User
		 
		 httpSecurity
		.csrf()
		.disable()
		.authorizeHttpRequests((authorize) ->authorize
				.anyRequest()
				.authenticated())
		.httpBasic(Customizer.withDefaults());
		
		return httpSecurity.build();
		*/
		
		
		/*  ADMIN have all access and Normal Has Only GET Method Access  */
		
		httpSecurity
		.csrf()
		.disable()
		.authorizeHttpRequests((authorize)-> authorize
				.requestMatchers(HttpMethod.GET,"/api/**")
				.permitAll()
				.requestMatchers(HttpMethod.GET,"/swagger-ui/**")
				.permitAll()
				.requestMatchers(HttpMethod.GET,"/v3/api-docs")
				.permitAll()
				.anyRequest()
				.authenticated())
		.httpBasic(Customizer.withDefaults());
		
		return httpSecurity.build();
	}
	
	
	//Creating Users
	
	@Bean
	public UserDetailsService userDetailsService()
	{
		UserDetails user1 = User.builder()
				.username("Jayesh")
				.password(passwordEncoder().encode("Jayesh#123"))
				.roles("ADMIN")
				.build();
		
		UserDetails user2 = User.builder()
				.username("TEMP")
				.password(passwordEncoder().encode("123"))
				.roles("USER")
				.build();
		
		return new InMemoryUserDetailsManager(user1,user2);
	}
	
	
	
}
