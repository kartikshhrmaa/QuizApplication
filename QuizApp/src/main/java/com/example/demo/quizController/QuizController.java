package com.example.demo.quizController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.submitController.QuizSubmit;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    // Create quiz using category, noOfQuestions, and title
    @GetMapping("/create")
    public ResponseEntity<?> createQuiz(
            @RequestParam String category,
            @RequestParam int noOfQuestions,
            @RequestParam String title) {
        return quizService.createQuiz(category, noOfQuestions, title);
    }

    // Get quiz by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getQuiz(@PathVariable Long id) {
        return quizService.getQuiz(id);
    }

    //submit quiz
    @PostMapping("/submit")
    public ResponseEntity<?> submitQuiz(@RequestBody QuizSubmit request) {
        return quizService.submitQuiz(request);
    }

    
    // Get all quizzes
    @GetMapping
    public ResponseEntity<?> getAllQuizzes() {
        return quizService.getAllQuizzes();
    }
}
