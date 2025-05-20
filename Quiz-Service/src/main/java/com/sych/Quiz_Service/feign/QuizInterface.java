package com.sych.Quiz_Service.feign;

import com.sych.Quiz_Service.model.Question;
import com.sych.Quiz_Service.model.QuestionWrapper;
import com.sych.Quiz_Service.model.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "QUESTION-SERVICE")
public interface QuizInterface {
    //Generate the question Ids for Quiz and return them to UI by accepting categoryName and numOfQuestions.
    @GetMapping("questions/generateQuestionIds")//Why GetMapping,since we will return data to the UI.
    public ResponseEntity<List<Integer>> getQuestionIdsForQuiz(@RequestParam String categoryName, @RequestParam int numOfQuestions);


    //If Quiz-Service requests for questions for a particular quiz ID we can use this method to return the questions with options only to Quiz-Service.
    //Since, we are sending data to another service which is Quiz-Service(Which is also a server), so we are using @PostMapping here.
    @PostMapping("questions/getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestions(@RequestBody List<Integer> questionIDs);

    //getScore: Client will submit the Quiz and Quiz-Service will talk to Question-Service for the score
    @PostMapping("questions/getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses);

    //Calls the addQuestion() method present in question-microservice
    @PostMapping("questions/add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question);

    //Calls the deleteQuestion() method present in question-microservice
    @DeleteMapping("questions/deleteQuestion")
    ResponseEntity<String> deleteQuestionById(@RequestBody int id);
}
