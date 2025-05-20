package com.sych.Question_Service.service;

import com.sych.Question_Service.Dao.QuestionDao;
import com.sych.Question_Service.model.Question;
import com.sych.Question_Service.model.QuestionWrapper;
import com.sych.Question_Service.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class QuestionServiece {
    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<List<Question>> getAllQuestions(){
        return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String topic) {
        return new ResponseEntity<>(questionDao.findByCategoryIgnoreCase(topic),HttpStatus.OK);//Since category is a field in Question class,just creating this as an abstract method in QuestionDao is enough
    }

    public ResponseEntity<String> addQuestion(Question question) {
           questionDao.save(question);
           return new ResponseEntity<>("Success",HttpStatus.CREATED);
    }

    public ResponseEntity<String> deleteQuestion(int id) {
        String result=null;
        //Question q=questionDao.findById(id).get();
        if(questionDao.findById(id)==null){
            result="The question does not exist with id:"+id;
            return new ResponseEntity<>(result,HttpStatus.NOT_FOUND);
        }else {
            questionDao.deleteById(id);
            result="Deleted Successfully";
            return new ResponseEntity<>(result,HttpStatus.NO_CONTENT);
        }
    }

    public ResponseEntity<List<Integer>> getQuestionIdsForQuiz(String categoryName, int numOfQuestions) {
        List<Integer> questionIds=questionDao.findRandomNumQuestionsByCategory(categoryName,numOfQuestions);
        return new ResponseEntity<>(questionIds,HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionIDs) {
        List<QuestionWrapper> wrappers=new ArrayList<>();
//Optional class: A container object which may or may not contain a non-null value.
// If a value is present, isPresent() returns true. If no value is present, the object is considered empty and isPresent() returns false.
        for(Integer id:questionIDs){
            Optional<Question> question=questionDao.findById(id);
            int wrapperId=question.get().getId();
            String title=question.get().getQuestionTitle();
            String option1=question.get().getOption1();
            String option2=question.get().getOption2();
            String option3=question.get().getOption3();
            String option4=question.get().getOption4();
            wrappers.add(new QuestionWrapper(wrapperId,title,option1,option2,option3,option4));
        }

        return new ResponseEntity<>(wrappers,HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {
        log.info("Response taken by QuestionService server at getScore()!");
        int count=0;
        for(Response r:responses){
            System.out.println(r.getId());
            System.out.println(r.getResponse());
        }
        for(Response response:responses){
            int responseId=response.getId();
            Question originalQuestionFromDB=questionDao.findById(responseId).get();
            String rightAnswer=originalQuestionFromDB.getRightAnswer();
            String responseString=response.getResponse();
            if(responseString.equalsIgnoreCase(rightAnswer)) {
                count++;
            }
        }

        return new ResponseEntity<>(count,HttpStatus.OK);
    }
}
