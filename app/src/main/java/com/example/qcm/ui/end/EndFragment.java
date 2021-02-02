package com.example.qcm.ui.end;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

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

        TextView tt = root.findViewById(R.id.textView2);
        int goodAnswers = 0;
        int numberQuestions = userResponse.size();
        double percentage = 0;

        for(UserResponse u : userResponse) {
            System.out.println(u.getReponses().stream().noneMatch(u1 -> u.getQuestion().getIncorrect_answers().contains(u1)));
            if (u.getReponses().stream().noneMatch(u1 -> u.getQuestion().getIncorrect_answers().contains(u1))) {
                    goodAnswers++;
            }
        }

        percentage = (double) goodAnswers/numberQuestions;
        String percentage100 = new DecimalFormat("#.##").format(percentage*100);

        percentage = goodAnswers/numberQuestions;
        tt.setText("Le nombre de bonnes réponses est : " + goodAnswers + " sur " + numberQuestions + " questions \n" + "Pourcentage : " + percentage100 + "%");

        return root;
    }

}
