package com.example.qcm.ui.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.TextView;

import com.example.qcm.R;
import com.example.qcm.models.Question;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MultipleQuestionWidget extends GridLayout {

    private Question question;

    private TextView questionText;
    private CheckBox reponse1;
    private CheckBox reponse2;
    private CheckBox reponse3;
    private CheckBox reponse4;

    public MultipleQuestionWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.question = question;

        final Context tmpContext = context;

        this.question = question;

        // Inflate the custom widget layout xml file.
        LayoutInflater root = LayoutInflater.from(context);
        root.inflate(R.layout.multiple_question_widget, this);

        questionText = (TextView) findViewById(R.id.question_text);
        reponse1 = (CheckBox) findViewById(R.id.response_cb_1);
        reponse2 = (CheckBox) findViewById(R.id.response_cb_2);
        reponse3 = (CheckBox) findViewById(R.id.response_cb_3);
        reponse4 = (CheckBox) findViewById(R.id.response_cb_4);


    }

    public void setQuestion(Question question) {
        questionText.setText(question.getQuestion());

        System.out.println("----------------------------------------------");
        System.out.println(question.toString());

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


}
