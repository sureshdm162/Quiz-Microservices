package com.sych.Quiz_Service.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;
    private String category;
    private String difficultyLevel;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    @Column(name = "question_title")
    private String questionTitle;
    @Column(name = "right_answer")
    private String rightAnswer;
}
