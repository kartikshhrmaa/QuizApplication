package com.example.demo.submitController;

import java.util.List;

public class QuizSubmit {
	    private Long quizId;
	    private List<AnswerDTO> answers;

	    // Getters and Setters

	    public Long getQuizId() {
	        return quizId;
	    }

	    public void setQuizId(Long quizId) {
	        this.quizId = quizId;
	    }

	    public List<AnswerDTO> getAnswers() {
	        return answers;
	    }

	    public void setAnswers(List<AnswerDTO> answers) {
	        this.answers = answers;
	    }
}