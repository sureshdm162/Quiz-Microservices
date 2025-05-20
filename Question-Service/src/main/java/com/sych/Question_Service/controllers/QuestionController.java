package com.sych.Question_Service.controllers;

import com.sych.Question_Service.model.Question;
import com.sych.Question_Service.model.QuestionWrapper;
import com.sych.Question_Service.model.Response;
import com.sych.Question_Service.service.QuestionServiece;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("questions")
public class QuestionController {

    @Autowired
    QuestionServiece questionServiece;

    @Autowired
    Environment environment;

    @GetMapping(value = "allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions(){
        return questionServiece.getAllQuestions();
    }

    @GetMapping("category/{topic}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String topic){
        return questionServiece.getQuestionsByCategory(topic);
    }

    @PostMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question){
        return questionServiece.addQuestion(question);
    }

    @DeleteMapping("deleteQuestion")
    public ResponseEntity<String> deleteQuestion(@RequestBody int id){
        return questionServiece.deleteQuestion(id);
    }

    //Generate the question Ids for Quiz and return them to UI by accepting categoryName and numOfQuestions.
    @GetMapping("generateQuestionIds")//Why GetMapping,since we will return data to the UI.
    public ResponseEntity<List<Integer>> getQuestionIdsForQuiz(@RequestParam String categoryName, @RequestParam int numOfQuestions){
        return questionServiece.getQuestionIdsForQuiz(categoryName,numOfQuestions);
    }

    //If Quiz-Service requests for questions for a particular quiz ID we can use this method to return the questions with options only to Quiz-Service.
    //Since, we are sending data to another service which is Quiz-Service(Which is also a server), so we are using @PostMapping here.
    @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestions(@RequestBody List<Integer> questionIDs){
        System.out.println(environment.getProperty("local.server.port"));
        return questionServiece.getQuestionsFromId(questionIDs);
    }
    //getScore: Client will submit the Quiz and Quiz-Service will talk to Question-Service for the score
    @PostMapping("getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses){
        log.info("Response taken by Question server at getScore()!");
        return questionServiece.getScore(responses);
    }
}
