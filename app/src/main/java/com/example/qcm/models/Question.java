package com.example.qcm.models;

import java.util.List;

public class Question {

    private String question;

    private String correct_answer;

    private String type;

    private List<String> incorrect_answers;

    public String getQuestion() {
        return question;
    }

    public String getCorrect_answer() {
        return correct_answer;
    }

    public String getType() {
        return type;
    }

    public List<String> getIncorrect_answers() {
        return incorrect_answers;
    }

    @Override
    public String toString() {
        return "Question{" +
                "question='" + question + '\'' +
                ", correct_answer='" + correct_answer + '\'' +
                ", incorrect_answers=" + incorrect_answers +
                '}';
    }
}
