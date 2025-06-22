package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QuizAppApplication {

	public static void main(String[] args) {
		/*
		.Designed and implemented a full-stack quiz management system using Spring Boot and MySQL, 
		supporting dynamic quiz generation from stored question banks
		.Developed CRUD functionality for managing quiz questions with RESTful endpoints and 
		Spring MVC architecture.
		.Implemented features like randomized question selection, category-based filtering, and 
		real-time quiz evaluation.
		.Leveraged Spring Data JPA for database operations and ensured a clean separation of layers (Controller, Service, Repository)
		 */
		SpringApplication.run(QuizAppApplication.class, args);
	}

}
