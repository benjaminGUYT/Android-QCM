package com.example.qcm.models;

import java.util.List;

public class Question {

    private String question;

    private String correct_answer;

    private List<String> incorrect_answers;

    @Override
    public String toString() {
        return "Question{" +
                "question='" + question + '\'' +
                ", correct_answer='" + correct_answer + '\'' +
                ", incorrect_answers=" + incorrect_answers +
                '}';
    }
}
