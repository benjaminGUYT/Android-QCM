package com.example.qcm.ui.end;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModel;

import com.example.qcm.models.UserResponse;

import java.text.DecimalFormat;
import java.util.List;

public class EndViewModel extends ViewModel {


    private int goodAnswers = 0;
    private int noAnswered = 0;
    private int numberQuestions;
    private double percentage = 0;
    private String percentageGoodFormat="";

    public EndViewModel() {

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String checkGoodAnswers(List<UserResponse> userResponse) {
        numberQuestions = userResponse.size();
        for(UserResponse u : userResponse) {
            System.out.println(u.getReponses().stream().noneMatch(u1 -> u.getQuestion().getIncorrect_answers().contains(u1)));
            if(u.getIsAnswered()) {
                if (u.getReponses().stream().noneMatch(u1 -> u.getQuestion().getIncorrect_answers().contains(u1))) {
                    goodAnswers++;
                }
            }
            else {
                noAnswered++;
            }
        }
        return calculPercentage(goodAnswers, numberQuestions);
    }

    public String calculPercentage(int goodAnswers, int numberQuestions) {
        if(numberQuestions > 0) {
            percentage = (double) goodAnswers / numberQuestions;
            percentageGoodFormat = new DecimalFormat("#.##").format(percentage * 100);
            return  percentageGoodFormat;
        }
        return "0,00";
    }
}
