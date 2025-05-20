package com.sych.Quiz_Service.service;

import com.sych.Quiz_Service.Dao.QuizDao;
import com.sych.Quiz_Service.feign.QuizInterface;
import com.sych.Quiz_Service.model.Question;
import com.sych.Quiz_Service.model.QuestionWrapper;
import com.sych.Quiz_Service.model.Quiz;
import com.sych.Quiz_Service.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class QuizService {

    @Autowired
    QuizDao quizDao;
    @Autowired
    QuizInterface quizInterface;

    public ResponseEntity<String> createQuiz(String title, int numQuestions, String category) {
        //In order to create quiz, we need questions,but we cannot use questionDao bcz it does not exist in this service.
        //So, we'll communicate via "QuizInterface"

        List<Integer> questionsIds= quizInterface.getQuestionIdsForQuiz(category,numQuestions).getBody();
        Quiz newQuiz=new Quiz();
        newQuiz.setTitle(title);
        newQuiz.setQuestionIds(questionsIds);
        quizDao.save(newQuiz);

        return new ResponseEntity<>("Success",HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(int id) {
       Quiz quiz=quizDao.findById(id).get();
       List<Integer> questionIds=quiz.getQuestionIds();
        ResponseEntity<List<QuestionWrapper>> questions= quizInterface.getQuestions(questionIds);
        return questions;
    }

    public ResponseEntity<Integer> calculateResult(List<Response> responses) {
        log.info("Response taken by Quiz(calculateResult) server!");
        ResponseEntity<Integer> score=quizInterface.getScore(responses);
        return score;
    }

    //addQuestion methhod calls the QuizInterface which internally calls Qustion-microservice
    public ResponseEntity<String> addQuestion(Question question){
        return quizInterface.addQuestion(question);
    }

    public ResponseEntity<String> deleteQuestionById(int id) {
        return quizInterface.deleteQuestionById(id);
    }
}
