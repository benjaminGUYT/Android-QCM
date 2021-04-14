package com.example.qcm.ui.end;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
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
import androidx.lifecycle.ViewModelProvider;

import com.daasuu.cat.CountAnimationTextView;
import com.example.qcm.R;
import com.example.qcm.models.ListQuestions;
import com.example.qcm.models.Question;
import com.example.qcm.models.UserResponse;
import com.example.qcm.ui.qcm.QcmViewModel;

import java.text.DecimalFormat;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class EndFragment extends Fragment {

    private List<UserResponse> userResponse;
    private int minutes;
    private int secondes;
    private EndViewModel endViewModel;

    public EndFragment() {
    }

    public static EndFragment newInstance(List<UserResponse> userResponse, int minutes, int secondes) {
        EndFragment endFragment = new EndFragment();
        endFragment.setUserResponse(userResponse);
        endFragment.setChrono(minutes, secondes);
        return endFragment;
    }

    public void setUserResponse(List<UserResponse> userResponse) {
        this.userResponse = userResponse;
    }
    public void setChrono(int minutes, int secondes) {
        this.minutes = minutes;
        this.secondes = secondes;
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

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);

        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_end, container, false);
        endViewModel = new ViewModelProvider(this).get(EndViewModel.class);



        String percentage = this.endViewModel.checkGoodAnswers(userResponse);
        ProgressBar progressBar = root.findViewById(R.id.progressBar);
        int goal = Integer.parseInt(percentage.split(",")[0]);
        //progrssBar.setProgress(goal, true);

        ObjectAnimator progressAnimator;
        progressAnimator = ObjectAnimator.ofInt(progressBar, "progress", goal);
        progressAnimator.setDuration(1000);
        progressAnimator.start();

        CountAnimationTextView percent = root.findViewById(R.id.percents);
        percent
                .setAnimationDuration(1000)
                .countAnimation(0, goal);


        return root;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
