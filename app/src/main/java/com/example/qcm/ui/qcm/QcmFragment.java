package com.example.qcm.ui.qcm;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import com.example.qcm.R;
import com.example.qcm.models.ListQuestions;
import com.example.qcm.models.Question;
import com.example.qcm.ui.widgets.MultipleQuestionWidget;
import com.example.qcm.ui.widgets.TrueFalseQuestionWidget;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QcmFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QcmFragment extends Fragment {

    public void setListQuestions(ListQuestions listQuestions) {
        this.listQuestions = listQuestions;
    }

    private ListQuestions listQuestions;

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

        MultipleQuestionWidget mqw = root.findViewById(R.id.question);
        TrueFalseQuestionWidget tfqw = root.findViewById(R.id.trueFalse);

        Button next = root.findViewById(R.id.buttonNext);

        Question question = listQuestions.getResults().get(0);
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
                if(i >= listQuestions.getResults().size() - 1) {
                    next.setText("Terminer");
                    next.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            next.setText("HOHOHOHO");
                        }
                    });
                }

                mqw.uncheckAll();
                Question question = listQuestions.getResults().get(i);
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
                i++;
            }
        });

        return root;
    }
}