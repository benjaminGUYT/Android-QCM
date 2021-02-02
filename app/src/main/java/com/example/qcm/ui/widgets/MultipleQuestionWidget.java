package com.example.qcm.ui.widgets;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.qcm.R;
import com.example.qcm.models.Question;
import com.example.qcm.models.UserResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MultipleQuestionWidget extends LinearLayout {

    private Question question;

    private TextView questionText;
    private CheckBox reponse1;
    private CheckBox reponse2;
    private CheckBox reponse3;
    private CheckBox reponse4;

    public MultipleQuestionWidget(Context context, AttributeSet attrs) {
        super(context, attrs);

        final Context tmpContext = context;

        LayoutInflater root = LayoutInflater.from(context);
        root.inflate(R.layout.multiple_question_widget, this);

        questionText = (TextView) findViewById(R.id.question_text);
        reponse1 = (CheckBox) findViewById(R.id.response_cb_1);
        reponse2 = (CheckBox) findViewById(R.id.response_cb_2);
        reponse3 = (CheckBox) findViewById(R.id.response_cb_3);
        reponse4 = (CheckBox) findViewById(R.id.response_cb_4);

    }

    public void setQuestion(Question question) {
        this.question = question;
        questionText.setText(question.getQuestion());

        List<String> reponses = new ArrayList<>(4);
        for(String s : question.getIncorrect_answers())
            reponses.add(s);
        reponses.add(question.getCorrect_answer());
        Collections.shuffle(reponses);

        reponse1.setText(reponses.get(0));
        reponse2.setText(reponses.get(1));
        reponse3.setText(reponses.get(2));
        reponse4.setText(reponses.get(3));
    }

    public void reset() {
        for(CheckBox c : getReponsesCheckBox()) {
            c.setChecked(false);
            c.setTextColor(Color.BLACK);
        }
    }

    public UserResponse getUserResponses() {
        List<String> ret = new ArrayList<>();
        for(CheckBox c : getReponsesCheckBox())
            if(c.isChecked()) ret.add(c.getText().toString());
        UserResponse userResponse = new UserResponse(question, ret);
        return userResponse;
    }

    /* Pas le plus opti, peut-etre  créer un attributt List<CheckBox>
    réinitialisé à chaque changement de question */
    public List<CheckBox> getReponsesCheckBox() {
        List<CheckBox> ret = new ArrayList<>();
        ret.addAll(Arrays.asList(new CheckBox[]{reponse1, reponse2, reponse3, reponse4}));
        return ret;
    }

}
