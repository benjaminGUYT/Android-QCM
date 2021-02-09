package com.example.qcm.models;

import android.text.Html;

import java.util.ArrayList;
import java.util.List;

public class Question {

    private String question;

    private String correct_answer;

    private String type;

    private List<String> incorrect_answers;

    public String getQuestion() {
        return Html.fromHtml(question).toString();
    }

    public String getCorrect_answer() {
        return Html.fromHtml(correct_answer).toString();
    }

    public String getType() {
        return type;
    }

    public List<String> getIncorrect_answers() {
        List<String> ret = new ArrayList<>();
        for(String s : incorrect_answers)
            ret.add(Html.fromHtml(s).toString());
        return ret;
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
