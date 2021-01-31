package com.example.qcm.ui.qcm;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.qcm.R;
import com.example.qcm.models.ListQuestions;
import com.example.qcm.models.Question;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_qcm, container, false);

        TextView textView = root.findViewById(R.id.TEXT_STATUS_ID);
        StringBuilder sb = new StringBuilder("");
        for(Question q : listQuestions.getResults())
            sb.append(q.toString());
        textView.setText(sb.toString() + '\n');

        return root;
    }
}