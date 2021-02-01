package com.example.qcm.models;

import java.util.List;

public class UserResponse {

    Question question;
    List<String> reponses;

    public UserResponse(Question question, List<String> reponses) {
        this.reponses = reponses;
        this.question = question;
    }

}