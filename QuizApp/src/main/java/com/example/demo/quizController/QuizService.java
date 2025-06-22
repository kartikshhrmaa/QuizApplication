package com.example.demo.quizController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.questionController.Question;
import com.example.demo.questionController.QuestionRepository;
import com.example.demo.submitController.AnswerDTO;
import com.example.demo.submitController.QuizSubmit;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;

    // Create a quiz from given category and question count
    public ResponseEntity<?> createQuiz(String category, int noOfQuestions, String title) {
        try {
            List<Question> questions = questionRepository.findByCategory(category);
            if (questions.size() < noOfQuestions) {
                return new ResponseEntity<>("Not enough questions in category: " + category, HttpStatus.BAD_REQUEST);
            }

            Collections.shuffle(questions);
            Quiz quiz = new Quiz();
            quiz.setTitle(title);
            quiz.setQuestions(questions.subList(0, noOfQuestions));

            Quiz saved = quizRepository.save(quiz);

            // Wrap questions (no correctAnswer)
            List<QuestionWrapper> wrappedQuestions = saved.getQuestions()
                    .stream()
                    .map(q -> new QuestionWrapper(
                            q.getId(),
                            q.getQuestionTitle(),
                            q.getOption1(),
                            q.getOption2(),
                            q.getOption3(),
                            q.getOption4()
                    ))
                    .toList();

            // Return clean quiz response
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("id", saved.getId());
            response.put("title", saved.getTitle());
            response.put("questions", wrappedQuestions);

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Quiz creation failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get quiz by ID
    public ResponseEntity<?> getQuiz(Long id) {
        try {
            Quiz quiz = quizRepository.findById(id).orElse(null);
            if (quiz == null) {
                return new ResponseEntity<>("Quiz not found", HttpStatus.NOT_FOUND);
            }

            List<QuestionWrapper> wrappedQuestions = quiz.getQuestions()
                    .stream()
                    .map(q -> new QuestionWrapper(
                            q.getId(),
                            q.getQuestionTitle(),
                            q.getOption1(),
                            q.getOption2(),
                            q.getOption3(),
                            q.getOption4()
                    ))
                    .toList();

            // Create inline map response
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("id", quiz.getId());
            response.put("title", quiz.getTitle());
            response.put("questions", wrappedQuestions);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error retrieving quiz: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //submit quiz to get result
    public ResponseEntity<?> submitQuiz(QuizSubmit request) {
        try {
            Quiz quiz = quizRepository.findById(request.getQuizId()).orElse(null);
            if (quiz == null) {
                return new ResponseEntity<>("Quiz not found", HttpStatus.NOT_FOUND);
            }

            int total = quiz.getQuestions().size();
            int correct = 0;

            Map<Long, String> correctAnswersMap = quiz.getQuestions().stream()
                    .collect(Collectors.toMap(Question::getId, Question::getCorrectAnswer));

            List<Map<String, Object>> incorrectAnswers = new java.util.ArrayList<>();

            for (AnswerDTO answer : request.getAnswers()) {
                String correctAnswer = correctAnswersMap.get(answer.getQuestionId());
                if (correctAnswer != null && correctAnswer.equalsIgnoreCase(answer.getSelectedAnswer())) {
                    correct++;
                } else {
                    Map<String, Object> incorrect = new LinkedHashMap<>();
                    incorrect.put("questionId", answer.getQuestionId());
                    incorrect.put("yourAnswer", answer.getSelectedAnswer());
                    incorrect.put("correctAnswer", correctAnswer);
                    incorrectAnswers.add(incorrect);
                }
            }

            Map<String, Object> result = new LinkedHashMap<>();
            result.put("quizId", request.getQuizId());
            result.put("totalQuestions", total);
            result.put("correctAnswers", correct);
            result.put("score", correct + " / " + total);
            result.put("incorrectAnswers", incorrectAnswers);

            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error evaluating quiz: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // Get all quizzes
    public ResponseEntity<?> getAllQuizzes() {
        List<Map<String, Object>> response = quizRepository.findAll().stream().map(quiz -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", quiz.getId());
            map.put("title", quiz.getTitle());
            return map;
        }).toList();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}