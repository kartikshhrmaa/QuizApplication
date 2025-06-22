package com.example.demo.questionController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    //<?> - return any type of body
    //adding a new question
    public ResponseEntity<?> createQuestion(Question question) {
        try {
            Question created = questionRepository.save(question);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
            //it has two parameters(data you wanna show, HTTP status code)
         // 201 Created - new resource was successfully created            
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create question: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //retrieve all questions
    public ResponseEntity<?> getAllQuestions() {
        try {
            List<Question> questions = questionRepository.findAll();
            if (questions.isEmpty()) {
                return new ResponseEntity<>("No questions found", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(questions, HttpStatus.OK);// 200 OK
        } catch (Exception e) {
            return new ResponseEntity<>("Error fetching questions: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Retrieve a specific question by its ID
    public ResponseEntity<?> getQuestionById(Long id) {
        try {
            Question question = questionRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Question not found with ID: " + id));
            return new ResponseEntity<>(question, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);// 404
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to fetch question", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Filtered by category
    public ResponseEntity<?> getByCategory(String category) {
        try {
            List<Question> list = questionRepository.findByCategory(category);
            if (list.isEmpty()) {
                return new ResponseEntity<>("No questions found in category: " + category, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error filtering by category", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Filtered by difficulty level
    public ResponseEntity<?> getByDifficulty(String level) {
        try {
            List<Question> list = questionRepository.findByDifficultyLevel(level);
            if (list.isEmpty()) {
                return new ResponseEntity<>("No questions found with difficulty: " + level, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error filtering by difficulty", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Delete a question by its ID
    public ResponseEntity<?> deleteQuestion(Long id) {
        try {
            if (!questionRepository.existsById(id)) {
                return new ResponseEntity<>("Question not found with ID: " + id, HttpStatus.NOT_FOUND);
            }
            questionRepository.deleteById(id);
            return new ResponseEntity<>("Question deleted with ID: " + id, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete question", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update an existing question by its ID
    public ResponseEntity<?> updateQuestion(Long id, Question updatedQuestion) {
        try {
            Question question = questionRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Question not found with ID: " + id));

            question.setQuestionTitle(updatedQuestion.getQuestionTitle());
            question.setOption1(updatedQuestion.getOption1());
            question.setOption2(updatedQuestion.getOption2());
            question.setOption3(updatedQuestion.getOption3());
            question.setOption4(updatedQuestion.getOption4());
            question.setCorrectAnswer(updatedQuestion.getCorrectAnswer());
            question.setCategory(updatedQuestion.getCategory());
            question.setDifficultyLevel(updatedQuestion.getDifficultyLevel());

            return new ResponseEntity<>(questionRepository.save(question), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update question", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //adding limitation to the number of quiz questions it 
 // Generate quiz with number limit
    public ResponseEntity<?> generateQuiz(String category, String difficulty, int numQuestions) {
        try {
        	
        	//filter by both difficulty level or category
            List<Question> filtered = questionRepository.findByCategoryAndDifficultyLevel(category, difficulty);
            if (filtered.isEmpty()) {
                return new ResponseEntity<>("No questions available for quiz", HttpStatus.NOT_FOUND);
            }
            //If filtered.size() is less than numQuestions, 
        	//then stream().limit(numQuestions) simply returns whatever is available, without an error.
            Collections.shuffle(filtered);
            return new ResponseEntity<>(filtered.stream().limit(numQuestions).toList(), HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>("Error generating quiz", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
