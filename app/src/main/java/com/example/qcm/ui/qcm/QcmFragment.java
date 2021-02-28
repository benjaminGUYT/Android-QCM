package com.example.qcm.ui.qcm;

import android.animation.ValueAnimator;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.qcm.R;
import com.example.qcm.models.ListQuestions;
import com.example.qcm.models.Question;
import com.example.qcm.models.UserResponse;
import com.example.qcm.ui.end.EndFragment;
import com.example.qcm.ui.options.OptionsViewModel;
import com.example.qcm.ui.widgets.MultipleQuestionWidget;
import com.example.qcm.ui.widgets.TrueFalseQuestionWidget;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QcmFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QcmFragment extends Fragment {

    private List<UserResponse> userResponses;
    private ListQuestions listQuestions;
    private Question question;
    private List<Float> listValues;
    private   String countFormat;

    private Button next;
    private MultipleQuestionWidget mqw;
    private TrueFalseQuestionWidget tfqw;
    private TextView chrono;

    private CountDownTimer countDownTimer;
    private int secondsToRun;
    private final int INTERVAL_COUNT_DOWN = 1000;

    private QcmViewModel qcmViewModel;


    public QcmFragment() {
        // Required empty public constructor
    }

    public static QcmFragment newInstance(ListQuestions listQuestions, Float timer) {
        QcmFragment fragment = new QcmFragment();
        fragment.setListQuestions(listQuestions);
        fragment.secondsToRun = timer.intValue() * 1000;
        System.out.println(timer.intValue());
        // fragment.setListValues(listValues);
        return fragment;
    }

    public void setListQuestions(ListQuestions listQuestions) {
        this.listQuestions = listQuestions;
    }
    public void setListValues(List<Float> listValues) {
        this.listValues = listValues;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);


        View root = inflater.inflate(R.layout.fragment_qcm, container, false);


        mqw = root.findViewById(R.id.question);
        tfqw = root.findViewById(R.id.trueFalse);
        next = root.findViewById(R.id.buttonNext);
        chrono = root.findViewById(R.id.chrono);

        if(savedInstanceState != null) {
            countFormat = savedInstanceState.getString("countFormat");
            chrono.setText(countFormat);
            Gson gson = new Gson();
            userResponses = (List<UserResponse>) gson.fromJson(savedInstanceState.getString("listUserResponse"), List.class);
            listQuestions = gson.fromJson(savedInstanceState.getString("listQuestions"), ListQuestions.class);
            question = gson.fromJson(savedInstanceState.getString("question"), Question.class);
            secondsToRun =savedInstanceState.getInt("second");
            qcmViewModel = (QcmViewModel) savedInstanceState.getParcelable("kok");
        }
        else {
            qcmViewModel = new ViewModelProvider(this).get(QcmViewModel.class);
        }

        countDownTimer = new CountDownTimer(secondsToRun, INTERVAL_COUNT_DOWN) {
            @Override
            public void onTick(long l) {
                secondsToRun = (int) l;
                int sec = (int) (l / 1000)%60;
                int min = (int) TimeUnit.SECONDS.toMinutes((int) l / 1000);
                countFormat = String.format("%d:%02d", min, sec);
                chrono.setText(countFormat);
            }
            @Override
            public void onFinish() {
                Toast toast = Toast.makeText(getContext(), "Le temps imparti est écoulé, affichage du score ..", Toast.LENGTH_LONG);
                toast.show();
                FragmentTransaction t = getParentFragmentManager().beginTransaction();
                t.replace(R.id.nav_host_fragment, EndFragment.newInstance(userResponses, secondsToRun%60 - Integer.parseInt(chrono.getText().toString().split(":")[0]), secondsToRun/1000 - Integer.parseInt(chrono.getText().toString().split(":")[1])));
                t.commit();
            }
        }.start();

        this.qcmViewModel.setNextQuestion(question, listQuestions, next, mqw, tfqw, userResponses, countDownTimer, secondsToRun, chrono, getParentFragmentManager().beginTransaction());
        return root;
    }



        @Override
        public void onSaveInstanceState(Bundle bundle) {
            super.onSaveInstanceState(bundle);
            bundle.putString("countFormat", countFormat);
            Gson gson = new Gson();
            String jsonUserResp = gson.toJson(userResponses);
            bundle.putSerializable("listUserResponse", jsonUserResp);
            String jsonListQuestion = gson.toJson(listQuestions);
            bundle.putSerializable("listQuestions", jsonListQuestion);
            String jsonQuestion = gson.toJson(question);
            bundle.putSerializable("question", jsonQuestion);
            bundle.putInt("second", secondsToRun);
        }

}