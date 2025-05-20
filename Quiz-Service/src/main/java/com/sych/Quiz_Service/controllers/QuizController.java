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

    //Create a new quiz by passing "title","Num of questions" and "Category like Java, python....etc." in JSON format.
    // USE URL:  http://localhost:8090/sych/quiz/create
    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestBody QuizDto quizDto){
          return quizService.createQuiz(quizDto.getTitle(),quizDto.getNumQuestions(),quizDto.getCategory());
    }

    //Just give the quiz id as input if created earlier, So it returns you the list of questions with only options in JSON format.
    //USE URL: http://localhost:8090/sych/quiz/getQuizById/your_quiz_Id
    @GetMapping("getQuizById/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable int id){
        return quizService.getQuizQuestions(id);
    }

    //Submit the quiz by passing question "id" and "response" in JSON format.
    //USE URL: http://localhost:8090/sych/quiz/submit/your_quiz_Id
    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submit(@PathVariable Integer id,@RequestBody List<Response> responses){
        log.info("Response taken by Quiz server at submit()!");
        return quizService.calculateResult(responses);
    }

    //Input all the details of Question class in JSON format to add question.
    //USE URL: http://localhost:8090/sych/quiz/addQuestion
    @PostMapping("addQuestion")
    public ResponseEntity<String> addQuestion(@RequestBody Question question){
        return quizService.addQuestion(question);
    }

    //Just pass the id in the URL to delete a particular question.
    //USE URL: http://localhost:8090/sych/quiz/deleteQuestion/Your_Question_Id
    @DeleteMapping("deleteQuestion/{id}")
    public ResponseEntity<String> deleteQuestionById(@PathVariable int id){
        return quizService.deleteQuestionById(id);
    }
}
