package com.example.qcm.ui.end;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.daasuu.cat.CountAnimationTextView;
import com.example.qcm.R;
import com.example.qcm.models.ListQuestions;
import com.example.qcm.models.Question;
import com.example.qcm.models.UserResponse;

import java.text.DecimalFormat;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class EndFragment extends Fragment {

    private List<UserResponse> userResponse;

    public EndFragment() {
    }

    public static EndFragment newInstance(List<UserResponse> userResponse) {
        EndFragment endFragment = new EndFragment();
        endFragment.setUserResponse(userResponse);
        return endFragment;
    }

    public void setUserResponse(List<UserResponse> userResponse) {
        this.userResponse = userResponse;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_end, container, false);




        int goodAnswers = 0;
        int numberQuestions = userResponse.size();
        double percentage = 0;


        for(UserResponse u : userResponse) {
            for(String s : u.getReponses()) {
                System.out.println("LES REPONSE DU POTO : " + s );
            }
            System.out.println(u.getReponses().stream().noneMatch(u1 -> u.getQuestion().getIncorrect_answers().contains(u1)));
            if (u.getReponses().stream().noneMatch(u1 -> u.getQuestion().getIncorrect_answers().contains(u1))) {
                    goodAnswers++;
            }
        }

        percentage = (double) goodAnswers/numberQuestions;
        String percentage100 = new DecimalFormat("#.##").format(percentage*100);

        percentage = goodAnswers/numberQuestions;

        ProgressBar progrssBar = root.findViewById(R.id.progressBar);
        int goal = Integer.parseInt(percentage100.split(",")[0]);
        //progrssBar.setProgress(goal, true);

        ObjectAnimator progressAnimator;
        progressAnimator = ObjectAnimator.ofInt(progrssBar, "progress", goal);
        progressAnimator.setDuration(1000);
        progressAnimator.start();

        CountAnimationTextView percent = root.findViewById(R.id.percents);
        percent
                .setAnimationDuration(1000)
                .countAnimation(0, goal);

        return root;
    }

}
