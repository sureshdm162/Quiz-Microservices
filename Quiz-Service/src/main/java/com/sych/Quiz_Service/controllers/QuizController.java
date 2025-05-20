package com.sych.Quiz_Service.controllers;

import com.sych.Quiz_Service.model.Question;
import com.sych.Quiz_Service.model.QuestionWrapper;
import com.sych.Quiz_Service.model.QuizDto;
import com.sych.Quiz_Service.model.Response;
import com.sych.Quiz_Service.service.QuizService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Data
@Slf4j
@RestController
@RequestMapping("quiz")
public class QuizController {
    @Autowired
    QuizService quizService;

    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestBody QuizDto quizDto){
          return quizService.createQuiz(quizDto.getTitle(),quizDto.getNumQuestions(),quizDto.getCategory());
    }

    @GetMapping("getQuizById/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable int id){
        return quizService.getQuizQuestions(id);
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submit(@PathVariable Integer id,@RequestBody List<Response> responses){
        log.info("Response taken by Quiz server at submit()!");
        return quizService.calculateResult(responses);
    }

    @PostMapping("addQuestion")
    public ResponseEntity<String> addQuestion(@RequestBody Question question){
        return quizService.addQuestion(question);
    }

    @DeleteMapping("deleteQuestion/{id}")
    public ResponseEntity<String> deleteQuestionById(@PathVariable int id){
        return quizService.deleteQuestionById(id);
    }
}
