package com.example.qcm.ui.options;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.qcm.R;
import com.example.qcm.models.Category;
import com.example.qcm.models.ListQuestions;
import com.example.qcm.models.Question;
import com.example.qcm.ui.qcm.QcmFragment;
import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.Slider;


import java.lang.reflect.Array;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class OptionsFragment extends Fragment {

    private OptionsViewModel galleryViewModel;

    private TextView categoryHint;
    private TextView difficultyHint;
    private TextView amountHint;
    private NumberPicker questionCount;
    private List<String> difficulties = new ArrayList<String>(Arrays.asList(new String[]{"Any", "Easy", "Medium", "Hard"}));
    private List<Category> categoryList = new ArrayList<Category>();
    private Button sendingButton;
    private Slider slider;

    private Category selectedCategory;
    private String selectedDifficulty = "any";
    private int selectedNumber = 50;
    private ListQuestions selectedListQuestions;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);

        galleryViewModel = new ViewModelProvider(this).get(OptionsViewModel.class);

        View root = inflater.inflate(R.layout.fragment_options, container, false);

        categoryHint = root.findViewById(R.id.category_hint);
        difficultyHint = root.findViewById(R.id.difficulty_hint);
        amountHint = root.findViewById(R.id.amount_hint);
        slider = root.findViewById(R.id.timer_opt);


        final Spinner categoriesSpinner = root.findViewById(R.id.categories_spinner);
        ArrayAdapter<Category> categoriesSpinnerAdapter = new ArrayAdapter<Category>(this.getContext(),
                android.R.layout.simple_spinner_item,
                categoryList);
        categoriesSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoriesSpinner.setAdapter(categoriesSpinnerAdapter);
        categoriesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Adapter adapter = parent.getAdapter();
                Category category = (Category) adapter.getItem(position);
                selectedCategory = category;
                galleryViewModel.getNumberOfQuestionsByCategoryAndDifficulty(selectedCategory, selectedDifficulty);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        final Spinner difficultiesSpinner = root.findViewById(R.id.difficulty_spinner);
        ArrayAdapter<String> difficultiesSpinnerAdapter = new ArrayAdapter<String>(this.getContext(),
                android.R.layout.simple_spinner_item,
                difficulties);
        difficultiesSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficultiesSpinner.setAdapter(difficultiesSpinnerAdapter);
        difficultiesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Adapter adapter = parent.getAdapter();
                String difficulty = (String) adapter.getItem(position);
                selectedDifficulty = difficulty.toLowerCase();
                galleryViewModel.getNumberOfQuestionsByCategoryAndDifficulty(selectedCategory, selectedDifficulty);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        questionCount = root.findViewById(R.id.question_count);
        questionCount.setMinValue(1);
        questionCount.setMaxValue(50);
        questionCount.setOnValueChangedListener((numberPicker, i, i1) -> {
            selectedNumber = numberPicker.getValue();
            slider.setValueFrom(selectedNumber * 5);
            slider.setValueTo(selectedNumber * 10);
            slider.setValue(selectedNumber * 5);
        });
        questionCount.setValue(50);
        slider.setValueFrom(50 * 5);
        slider.setValueTo(50 * 10);
        slider.setValue(50 * 5);

        slider.setLabelFormatter(value -> {
            NumberFormat format = NumberFormat.getInstance();
            format.setMaximumFractionDigits(0);
            if(value < 60)  return format.format(new Double(value)) + "s";
            return TimeUnit.SECONDS.toMinutes((int) value) + "min" + TimeUnit.SECONDS.toSeconds((int) value % 60) + "s";
        });

        sendingButton = root.findViewById(R.id.sending_button);
        sendingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!questionCount.isEnabled()) {
                    Toast toast = Toast.makeText(getContext(), "Cette combinaison est impossible", Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }
                sendingButton.setActivated(false);
                galleryViewModel.getQuestionsByCategoryAndDifficulty(selectedNumber, selectedCategory, selectedDifficulty);
            }
        });

        

        galleryViewModel.getCategoryHint().observe(getViewLifecycleOwner(), s -> {
            categoryHint.setText(s);
        });

        galleryViewModel.getDifficultyHint().observe(getViewLifecycleOwner(), s -> {
            difficultyHint.setText(s);
        });

        galleryViewModel.getAmountHint().observe(getViewLifecycleOwner(), s -> {
            amountHint.setText(s);
        });

        galleryViewModel.getInitialSelectedCategories().observe(getViewLifecycleOwner(), category -> {
            selectedCategory = category;
        });

        galleryViewModel.getListCategories().observe(getViewLifecycleOwner(), categories -> {
            categoriesSpinnerAdapter.addAll(categories);
            categoriesSpinnerAdapter.notifyDataSetChanged();
        });

        galleryViewModel.getNumberOfQuestions().observe(getViewLifecycleOwner(), number -> {
            if(number == 0) {
                Toast toast = Toast.makeText(getContext(), "Cette combinaison est impossible", Toast.LENGTH_LONG);
                toast.show();
                questionCount.setEnabled(false);
            }
            else {
                questionCount.setEnabled(true);
                questionCount.setValue(number);
                questionCount.setMaxValue(number);
            }
        });

        galleryViewModel.getListQuestions().observe(getViewLifecycleOwner(), listQuestions -> {
            selectedListQuestions = listQuestions;
            if(listQuestions.getResults() == null)
                return;
            System.out.println("SIZE = " + listQuestions.getResults().size());
            for(Question q : listQuestions.getResults())
                System.out.println(q.toString() + '\n');
            FragmentTransaction t = this.getParentFragmentManager().beginTransaction();
            t.replace(R.id.nav_host_fragment, QcmFragment.newInstance(selectedListQuestions, slider.getValue()));
            t.commit();
        });

        return root;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}