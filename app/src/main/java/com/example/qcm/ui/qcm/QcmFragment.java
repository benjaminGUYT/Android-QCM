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
import android.widget.Toast;

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

        question = listQuestions.getResults().remove(0);
        if(listQuestions.getResults().size() == 0) {

            next.setText("Terminer");
            next.setOnClickListener(view1 -> {
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
                FragmentTransaction t = getParentFragmentManager().beginTransaction();
                t.replace(R.id.nav_host_fragment, EndFragment.newInstance(userResponses));
                t.commit();


            }
            );
        }

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
        if(listQuestions.getResults().size() != 0) {
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    System.out.println(mqw.isAllCheckBoxUnchecked());
                    System.out.println(tfqw.isAllRadioButtonUnchecked());

                    if(mqw.isAllCheckBoxUnchecked() && tfqw.isAllRadioButtonUnchecked()) {
                        Toast toast = Toast.makeText(getContext(), "Veuillez chocher au moins une réponse", Toast.LENGTH_LONG);
                        toast.show();
                        return;
                    }

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

                    Handler handler = new Handler();
                    handler.postDelayed(() -> {
                        if (mqw.getVisibility() == View.VISIBLE)
                            mqw.reset();
                        if (tfqw.getVisibility() == View.VISIBLE)
                            tfqw.reset();
                    }, 1800);
                    handler.postDelayed(() -> {
                        System.out.println("----------------" + listQuestions.getResults().size());
                        if (listQuestions.getResults().size() == 0) {

                            next.setText("Terminer");
                            next.setOnClickListener(view1 -> {
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
                                FragmentTransaction t = getParentFragmentManager().beginTransaction();
                                t.replace(R.id.nav_host_fragment, EndFragment.newInstance(userResponses));
                                t.commit();


                            });


                        }
                        if (listQuestions.getResults().size() != 0) {
                            Question question1 = listQuestions.getResults().remove(0);
                            if (listQuestions.getResults().size() == 0) {

                                next.setText("Terminer");
                                next.setOnClickListener(view1 -> {
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
                                    FragmentTransaction t = getParentFragmentManager().beginTransaction();
                                    t.replace(R.id.nav_host_fragment, EndFragment.newInstance(userResponses));
                                    t.commit();


                                });


                            }
                            if (question1.getType().equals("multiple")) {
                                mqw.setVisibility(View.VISIBLE);
                                tfqw.setVisibility(View.INVISIBLE);
                                mqw.setQuestion(question1);
                            } else {
                                tfqw.setVisibility(View.VISIBLE);
                                mqw.setVisibility(View.INVISIBLE);
                                tfqw.setQuestion(question1);
                            }
                            question = question1;
                        }
                    }, 2000);


                }
            });
        }
        return root;
    }

}