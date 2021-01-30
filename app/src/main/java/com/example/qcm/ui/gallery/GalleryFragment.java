package com.example.qcm.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
    private Spinner categoriesSpinner;
    private Spinner difficultiesSpinner;
    private Spinner typeSpinner;
    private List<String> types = new ArrayList<String>(Arrays.asList(new String[]{"Any", "Multiple choice", "True / False"}));
    private List<String> difficulties = new ArrayList<String>(Arrays.asList(new String[]{"Any", "Easy", "Medium", "Hard"}));
    private List<Category> categoryList = new ArrayList<Category>();

    private Category selectedCategory;
    private String selectedDifficulty;
    private String selectedType;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        galleryViewModel = new ViewModelProvider(this).get(GalleryViewModel.class);

        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        final TextView textView = root.findViewById(R.id.text_gallery);

        textViewResult = root.findViewById(R.id.text_view_result);

        categoriesSpinner = root.findViewById(R.id.categories_spinner);
        Category catmp = new Category(0, "Any");
        selectedCategory = catmp;
        categoryList.add(catmp);
        ArrayAdapter<Category> categoriesSpinnerAdapter = new ArrayAdapter<Category>(this.getContext(),
                android.R.layout.simple_spinner_item,
                categoryList);
        categoriesSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.categoriesSpinner.setAdapter(categoriesSpinnerAdapter);
        this.categoriesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Adapter adapter = parent.getAdapter();
                Category category = (Category) adapter.getItem(position);
                selectedCategory = category;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        difficultiesSpinner = root.findViewById(R.id.difficulty_spinner);
        ArrayAdapter<String> difficultiesSpinnerAdapter = new ArrayAdapter<String>(this.getContext(),
                android.R.layout.simple_spinner_item,
                difficulties);
        difficultiesSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.difficultiesSpinner.setAdapter(difficultiesSpinnerAdapter);
        this.difficultiesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Adapter adapter = parent.getAdapter();
                String difficulty = (String) adapter.getItem(position);
                selectedDifficulty = difficulty.toLowerCase();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        typeSpinner = root.findViewById(R.id.type_spinner);
        ArrayAdapter<String> typesSpinnerAdapter = new ArrayAdapter<String>(this.getContext(),
                android.R.layout.simple_spinner_item,
                types);
        typesSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.typeSpinner.setAdapter(typesSpinnerAdapter);
        this.typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://opentdb.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OpenTriviaDB openTriviaDB = retrofit.create(OpenTriviaDB.class);

        Call<Result> call = openTriviaDB.getCategories();

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if(!response.isSuccessful()) {
                    textViewResult.setText(response.code());
                    return;
                }

                Result result = response.body();



                StringBuilder sb = new StringBuilder("");
                for(Category cat : result.getCategories()) {
                    sb.append("Nom : " + cat.getName() + " ID : " + cat.getId() + "\n");
                    categoryList.add(cat);
                }
                categoriesSpinnerAdapter.notifyDataSetChanged();
                textViewResult.append(sb.toString());

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                textViewResult.setText("Failure : " + t.getMessage());
            }
        });

                galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }


}