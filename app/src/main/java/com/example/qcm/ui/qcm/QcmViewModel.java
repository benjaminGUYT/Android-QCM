package com.example.qcm.ui.qcm;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;

import com.example.qcm.QuestionTypeEnum;
import com.example.qcm.R;
import com.example.qcm.models.ListQuestions;
import com.example.qcm.models.Question;
import com.example.qcm.models.UserResponse;
import com.example.qcm.ui.end.EndFragment;
import com.example.qcm.ui.widgets.MultipleQuestionWidget;
import com.example.qcm.ui.widgets.TrueFalseQuestionWidget;

import java.util.List;

public class QcmViewModel extends ViewModel {

    public void setNextQuestion(Question question, ListQuestions listQuestions, Button next, MultipleQuestionWidget mqw, TrueFalseQuestionWidget tfqw, List<UserResponse> userResponses, CountDownTimer countDownTimer, int secondsToRun, TextView chrono, FragmentTransaction t) {

        question = listQuestions.getResults().remove(0);
        if(listQuestions.getResults().isEmpty()) setNextButtonFinalState(mqw, tfqw, question, userResponses, next, countDownTimer, secondsToRun, chrono, t);
        else setNextButton(question, listQuestions, next,  mqw,  tfqw, userResponses, countDownTimer, secondsToRun, chrono, t);
        setWidgetsVisibility(question, mqw, tfqw);
    }

    public void setNextButton(Question question, ListQuestions listQuestions, Button next, MultipleQuestionWidget mqw, TrueFalseQuestionWidget tfqw, List<UserResponse> userResponses, CountDownTimer countDownTimer, int secondsToRun, TextView chrono, FragmentTransaction t) {
        next.setOnClickListener(view -> {
            showSolutionSaveUserResp(question, tfqw, mqw, userResponses);
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                if (mqw.getVisibility() == View.VISIBLE)
                    mqw.reset();
                if (tfqw.getVisibility() == View.VISIBLE)
                    tfqw.reset();
                handler.postDelayed(() -> setNextQuestion(question, listQuestions, next, mqw, tfqw, userResponses, countDownTimer, secondsToRun, chrono, t), 200);
            }, 1800);
        });
    }

    public void setNextButtonFinalState(MultipleQuestionWidget mqw, TrueFalseQuestionWidget tfqw, Question question, List<UserResponse> userResponses, Button next, CountDownTimer countDownTimer, int secondsToRun, TextView chrono, FragmentTransaction t) {
        next.setText("Terminer");
        next.setOnClickListener(view1 -> {
            showSolutionSaveUserResp(question, tfqw, mqw, userResponses);
            countDownTimer.cancel();
            t.replace(R.id.nav_host_fragment, EndFragment.newInstance(userResponses, secondsToRun/1000/60 - Integer.parseInt(chrono.getText().toString().split(":")[0]), secondsToRun/1000 - Integer.parseInt(chrono.getText().toString().split(":")[1])));
            t.commit();
        });

    }

    public void showSolutionSaveUserResp(Question question, TrueFalseQuestionWidget tfqw, MultipleQuestionWidget mqw, List<UserResponse> userResponses) {
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

    public void setWidgetsVisibility(Question question, MultipleQuestionWidget mqw, TrueFalseQuestionWidget tfqw) {
        if(question.getType().equals(QuestionTypeEnum.MULTIPLE.name().toLowerCase())) {
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
