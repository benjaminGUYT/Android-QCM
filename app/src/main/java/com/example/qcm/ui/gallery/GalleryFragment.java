package com.example.qcm.ui.gallery;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.qcm.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;

    private TextView textViewResult;
    private NumberPicker questionCount;
    private List<String> types = new ArrayList<String>(Arrays.asList(new String[]{"Any", "Multiple choice", "True / False"}));
    private List<String> difficulties = new ArrayList<String>(Arrays.asList(new String[]{"Any", "Easy", "Medium", "Hard"}));
    private List<Category> categoryList = new ArrayList<Category>();

    private Category selectedCategory;
    private String selectedDifficulty = "any";
    private String selectedType;
    private int selectedNumber;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        galleryViewModel = new ViewModelProvider(this).get(GalleryViewModel.class);

        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        final TextView textView = root.findViewById(R.id.text_gallery);

        textViewResult = root.findViewById(R.id.text_view_result);

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
                galleryViewModel.getNumberOfQuestionsByCategory(category, selectedDifficulty);
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
                galleryViewModel.getNumberOfQuestionsByCategory(selectedCategory, selectedDifficulty);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        final Spinner typeSpinner = root.findViewById(R.id.type_spinner);
        ArrayAdapter<String> typesSpinnerAdapter = new ArrayAdapter<String>(this.getContext(),
                android.R.layout.simple_spinner_item,
                types);
        typesSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typesSpinnerAdapter);
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Adapter adapter = parent.getAdapter();
                String type = (String) adapter.getItem(position);
                selectedType = type.toLowerCase();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        questionCount = root.findViewById(R.id.question_count);
        questionCount.setMinValue(1);
        questionCount.setMaxValue(50);
        questionCount.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                selectedNumber = numberPicker.getValue();
            }
        });

        

        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        galleryViewModel.getInitialSelectedCategories().observe(getViewLifecycleOwner(), new Observer<Category>() {
            @Override
            public void onChanged(Category category) {
                selectedCategory = category;
            }
        });

        galleryViewModel.getListCategories().observe(getViewLifecycleOwner(), new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                categoriesSpinnerAdapter.addAll(categories);
                categoriesSpinnerAdapter.notifyDataSetChanged();
            }
        });

        galleryViewModel.getNumberOfQuestions().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                questionCount.setMaxValue(integer);
            }
        });


        return root;
    }


}