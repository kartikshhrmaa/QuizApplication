package com.example.demo.questionController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService; //injecting service cls
    

    //adding a new question
    @PostMapping
    public ResponseEntity<?> createQuestion(@RequestBody Question question) {
        return questionService.createQuestion(question);
    }

    //Retrieve all ques
    @GetMapping
    public ResponseEntity<?> getAllQuestions() {
        return questionService.getAllQuestions();
    }
    
    
    //get a question by its ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getQuestionById(@PathVariable Long id) {
        return questionService.getQuestionById(id);
    }

    ////filter by difficulty level
    @GetMapping("/difficulty/{level}")
    public ResponseEntity<?> getByDifficulty(@PathVariable String level) {
        return questionService.getByDifficulty(level);
    }

    //filter by category
    @GetMapping("/category/{category}")
    public ResponseEntity<?> getByCategory(@PathVariable String category) {
        return questionService.getByCategory(category);
    }

    //delete by id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long id) {
        return questionService.deleteQuestion(id);
    }

    //update existing question via id
    @PutMapping("/{id}")
    public ResponseEntity<?> updateQuestion(@PathVariable Long id, @RequestBody Question updatedQuestion) {
        return questionService.updateQuestion(id, updatedQuestion);
    }
    
    //adding limitation to the number of quiz questions it 
    @GetMapping("/generate")
    public ResponseEntity<?> generateQuiz(
            @RequestParam String category,
            @RequestParam String difficulty,
            @RequestParam(defaultValue = "10") int numQuestions) {
        return questionService.generateQuiz(category, difficulty, numQuestions);
    }
}
