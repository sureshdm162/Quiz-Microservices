package com.sych.Quiz_Service.model;

import lombok.Data;

@Data
public class  QuizDto {
    private String title;
    private int numQuestions;
    private String category;
}
