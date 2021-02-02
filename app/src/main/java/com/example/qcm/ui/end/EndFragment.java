package com.example.qcm.ui.end;

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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_end, container, false);

        TextView tt = root.findViewById(R.id.textView2);
        int goodAnswers = 0;
        int numberQuestions = userResponse.size();


        for(UserResponse u : userResponse) {
            for(String q : u.getQuestion().getIncorrect_answers()) {
                System.out.println("INCORRECT ANSWERS " + q);
            }
            System.out.println("CORRECT ANSWERS " + u.getQuestion().getCorrect_answer());
            for(String r : u.getReponses()) {
                System.out.println("LES REPONSES DU POTO " + r);
            }

            System.out.println(u.getReponses().stream().noneMatch(u1 -> u.getQuestion().getIncorrect_answers().contains(u1)));
            if (u.getReponses().stream().noneMatch(u1 -> u.getQuestion().getIncorrect_answers().contains(u1))) {
                    goodAnswers++;
            }

        }
        tt.setText("Le nombre de bonnes réponses est : " + goodAnswers + " sur " + numberQuestions + " questions.");
        return root;
    }

}
