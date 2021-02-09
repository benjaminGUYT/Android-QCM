package com.example.qcm.ui.qcm;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.qcm.R;
import com.example.qcm.models.ListQuestions;
import com.example.qcm.models.Question;
import com.example.qcm.models.UserResponse;
import com.example.qcm.ui.end.EndFragment;
import com.example.qcm.ui.widgets.MultipleQuestionWidget;
import com.example.qcm.ui.widgets.TrueFalseQuestionWidget;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QcmFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QcmFragment extends Fragment {

    private List<UserResponse> userResponses;
    private ListQuestions listQuestions;
    private Question question;

    private Button next;
    private MultipleQuestionWidget mqw;
    private TrueFalseQuestionWidget tfqw;
    private TextView chrono;

    public QcmFragment() {
        // Required empty public constructor
    }

    public static QcmFragment newInstance(ListQuestions listQuestions) {
        QcmFragment fragment = new QcmFragment();
        fragment.setListQuestions(listQuestions);
        return fragment;
    }

    public void setListQuestions(ListQuestions listQuestions) {
        this.listQuestions = listQuestions;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_qcm, container, false);

        userResponses = new ArrayList<>();
        mqw = root.findViewById(R.id.question);
        tfqw = root.findViewById(R.id.trueFalse);
        next = root.findViewById(R.id.buttonNext);
        chrono = root.findViewById(R.id.chrono);

        int secondsToRun = 999;

        ValueAnimator timer = ValueAnimator.ofInt(secondsToRun);
        timer.setDuration(secondsToRun * 1000).setInterpolator(new LinearInterpolator());
        timer.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                int elapsedSeconds = (int) animation.getAnimatedValue();
                int minutes = elapsedSeconds / 60;
                int seconds = elapsedSeconds % 60;

                chrono.setText(String.format("%d:%02d", minutes, seconds));
            }
        });
        timer.start();

        setNextQuestion();
        return root;
    }

    private void setNextQuestion() {
        question = listQuestions.getResults().remove(0);
        if(listQuestions.getResults().isEmpty()) setNextButtonFinalState();
        else setNextButton();
        setWidgetsVisibility();
    }

    private void setNextButton() {
        next.setOnClickListener(view -> {
            showSolutionSaveUserResp();
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                if (mqw.getVisibility() == View.VISIBLE)
                    mqw.reset();
                if (tfqw.getVisibility() == View.VISIBLE)
                    tfqw.reset();
                handler.postDelayed(() -> setNextQuestion(), 200);
            }, 1800);
        });
    }

    private void setNextButtonFinalState() {
        next.setText("Terminer");
        next.setOnClickListener(view1 -> {
            showSolutionSaveUserResp();
            FragmentTransaction t = getParentFragmentManager().beginTransaction();
            t.replace(R.id.nav_host_fragment, EndFragment.newInstance(userResponses));
            t.commit();
        });
    }

    private void showSolutionSaveUserResp() {
        if (mqw.getVisibility() == View.VISIBLE) {
            userResponses.add(mqw.getUserResponses());
            List<CheckBox> responseCheckBox = mqw.getReponsesCheckBox();
            for (CheckBox c : responseCheckBox) {
                if (c.getText().toString().equals(question.getCorrect_answer()))
                    c.setTextColor(Color.GREEN);
                else c.setTextColor(Color.RED);
            }
        } else if (tfqw.getVisibility() == View.VISIBLE) {
            userResponses.add(tfqw.getUserResponses());
            List<RadioButton> responseCheckBox = tfqw.getReponsesRadioButton();
            for (RadioButton c : responseCheckBox) {
                if (c.getText().toString().equals(question.getCorrect_answer()))
                    c.setTextColor(Color.GREEN);
                else c.setTextColor(Color.RED);
            }
        }
    }

    private void setWidgetsVisibility() {
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
    }

}