package com.example.qcm.ui.widgets;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.qcm.R;
import com.example.qcm.models.Question;
import com.example.qcm.models.UserResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TrueFalseQuestionWidget extends GridLayout {

    private Question question;

    private TextView questionText;
    private RadioButton reponse1;
    private RadioButton reponse2;

    public TrueFalseQuestionWidget(Context context, AttributeSet attrs) {
        super(context, attrs);


        final Context tmpContext = context;

        this.question = question;

        // Inflate the custom widget layout xml file.
        LayoutInflater root = LayoutInflater.from(context);
        root.inflate(R.layout.true_false_question_widget, this);

        questionText = (TextView) findViewById(R.id.question_text);
        reponse1 = (RadioButton) findViewById(R.id.response_rb_1);
        reponse2 = (RadioButton) findViewById(R.id.response_rb_2);


    }

    public void setQuestion(Question question) {
        this.question = question;
        questionText.setText(question.getQuestion());

        reponse1.setText(question.getCorrect_answer());
        reponse2.setText(question.getIncorrect_answers().get(0));
    }

    public void reset() {
        for(RadioButton c : getReponsesRadioButton()) {
            c.setChecked(false);
            c.setTextColor(Color.BLACK);
        }
    }

    public UserResponse getUserResponses() {
        List<String> ret = new ArrayList<>();
        for(RadioButton c : getReponsesRadioButton())
            if(c.isChecked()) ret.add(c.getText().toString());
        UserResponse userResponse = new UserResponse(question, ret);
        return userResponse;
    }

    /* Pas le plus opti, peut-etre  créer un attributt List<CheckBox>
    réinitialisé à chaque changement de question */
    public List<RadioButton> getReponsesRadioButton() {
        List<RadioButton> ret = new ArrayList<>();
        ret.addAll(Arrays.asList(new RadioButton[]{reponse1, reponse2}));
        return ret;
    }
}
