package com.example.qcm.models;

import java.util.List;

public class UserResponse {

    Question question;
    List<String> reponses;
    boolean isAnswered;

    public UserResponse(Question question, List<String> reponses, boolean isAnswered) {
        this.reponses = reponses;
        this.question = question;
        this.isAnswered = isAnswered;
    }

    public Question getQuestion() {
        return this.question;
    }

    public List<String> getReponses() {
        return this.reponses;
    }

    public boolean getIsAnswered() {
        return this.isAnswered;
    }

}
