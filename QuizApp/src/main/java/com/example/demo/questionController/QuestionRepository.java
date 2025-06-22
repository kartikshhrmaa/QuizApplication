package com.example.demo.questionController;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
	
	//filtering by category or difficulty individually using
    List<Question> findByCategory(String category);
    List<Question> findByDifficultyLevel(String difficultyLevel);
    
    //filters or finds by both category and difficulty level
    List<Question> findByCategoryAndDifficultyLevel(String category, String difficultyLevel);
    
}