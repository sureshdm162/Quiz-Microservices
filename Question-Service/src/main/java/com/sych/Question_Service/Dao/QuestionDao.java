package com.sych.Question_Service.Dao;

import com.sych.Question_Service.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface QuestionDao extends JpaRepository<Question,Integer> {
    public List<Question> findByCategoryIgnoreCase(String topic);

    String query="SELECT q.id FROM question q WHERE q.category=:category ORDER BY RAND() LIMIT :numQuestions;";
    @Query(value = query,nativeQuery = true )
    public List<Integer> findRandomNumQuestionsByCategory(String category, int numQuestions);
}
