package com.example.qcm.ui.qcm;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.RadioButton;

import com.example.qcm.R;
import com.example.qcm.models.ListQuestions;
import com.example.qcm.models.Question;
import com.example.qcm.models.UserResponse;
import com.example.qcm.ui.end.EndFragment;
import com.example.qcm.ui.widgets.MultipleQuestionWidget;
import com.example.qcm.ui.widgets.TrueFalseQuestionWidget;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QcmFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QcmFragment extends Fragment {

    private List<UserResponse> userResponses;

    public void setListQuestions(ListQuestions listQuestions) {
        this.listQuestions = listQuestions;
    }

    private ListQuestions listQuestions;
    Question question;

    public QcmFragment() {
        // Required empty public constructor
    }


    public static QcmFragment newInstance(ListQuestions listQuestions) {
        QcmFragment fragment = new QcmFragment();
        fragment.setListQuestions(listQuestions);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_qcm, container, false);

        userResponses = new ArrayList<>();
        MultipleQuestionWidget mqw = root.findViewById(R.id.question);
        TrueFalseQuestionWidget tfqw = root.findViewById(R.id.trueFalse);

        Button next = root.findViewById(R.id.buttonNext);

        question = listQuestions.getResults().get(0);
        if(question.getType().equals("multiple")) {
            mqw.setVisibility(View.VISIBLE);
            tfqw.setVisibility(View.INVISIBLE);
            mqw.setQuestion(question);
        }
        else {
            tfqw.setVisibility(View.VISIBLE);
            mqw.setVisibility(View.INVISIBLE);
            tfqw.setQuestion(question);
        }

        next.setOnClickListener(new View.OnClickListener() {
            int i = 1;
            @Override
            public void onClick(View view) {

                if(mqw.getVisibility() == View.VISIBLE) {
                    userResponses.add(mqw.getUserResponses());
                    List<CheckBox> responseCheckBox = mqw.getReponsesCheckBox();
                    for(CheckBox c : responseCheckBox) {
                        if(c.getText().toString().equals(question.getCorrect_answer()))
                            c.setTextColor(Color.GREEN);
                        else c.setTextColor(Color.RED);
                    }
                }
                else if(tfqw.getVisibility() == View.VISIBLE) {
                    userResponses.add(tfqw.getUserResponses());
                    List<RadioButton> responseCheckBox = tfqw.getReponsesRadioButton();
                    for(RadioButton c : responseCheckBox) {
                        if(c.getText().toString().equals(question.getCorrect_answer()))
                            c.setTextColor(Color.GREEN);
                        else c.setTextColor(Color.RED);
                    }
                }

                Handler handler = new Handler();
                handler.postDelayed(() -> {
                    if(mqw.getVisibility() == View.VISIBLE)
                        mqw.reset();
                    if(tfqw.getVisibility() == View.VISIBLE)
                        tfqw.reset();
                }, 1800);
                handler.postDelayed(() -> {

                    if(i >= listQuestions.getResults().size() - 1) {
                        next.setText("Terminer");
                        next.setOnClickListener(view1 -> {
                            FragmentTransaction t = getParentFragmentManager().beginTransaction();
                            t.replace(R.id.nav_host_fragment, EndFragment.newInstance(userResponses));
                            t.commit();
                        });
                    }

                    Question question1 = listQuestions.getResults().get(i);
                    if(question1.getType().equals("multiple")) {
                        mqw.setVisibility(View.VISIBLE);
                        tfqw.setVisibility(View.INVISIBLE);
                        mqw.setQuestion(question1);
                    }
                    else {
                        tfqw.setVisibility(View.VISIBLE);
                        mqw.setVisibility(View.INVISIBLE);
                        tfqw.setQuestion(question1);
                    }
                    question = question1;
                            i++;
                    }, 2000);



            }
        });

        return root;
    }

}