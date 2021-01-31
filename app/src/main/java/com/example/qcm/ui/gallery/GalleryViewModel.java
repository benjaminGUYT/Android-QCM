package com.example.qcm.ui.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GalleryViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<List<Category>> listCategories;
    private MutableLiveData<Category> initialSelectedCategory;
    private MutableLiveData<Integer> numberOfQuestions;

    private Retrofit retrofit;



    public GalleryViewModel() {
        numberOfQuestions = new MutableLiveData<>(50);

        mText = new MutableLiveData<>();
        mText.setValue("Veuillez entrer ci-dessous les options du QCM");

        listCategories = new MutableLiveData<>();
        List<Category> list = new ArrayList<>();
        Category initialCat = new Category(0, "Any");
        initialSelectedCategory = new MutableLiveData<>();
        initialSelectedCategory.setValue(initialCat);
        list.add(initialCat);


        retrofit = new Retrofit.Builder()
                .baseUrl("https://opentdb.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OpenTriviaDB openTriviaDB = retrofit.create(OpenTriviaDB.class);

        Call<Result> call = openTriviaDB.getCategories();

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                for(Category cat : response.body().getCategories())
                    list.add(cat);
                listCategories.setValue(list);
            }
            @Override
            public void onFailure(Call<Result> call, Throwable t) {
            }
        });


    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<List<Category>> getListCategories() {
        return this.listCategories;
    }

    public LiveData<Category> getInitialSelectedCategories() {
        return initialSelectedCategory;
    }

    public LiveData<Integer> getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void getNumberOfQuestionsByCategory(Category category, String difficulty) {

        System.out.println(category.getName() + " " + difficulty);

        if(category.getId() == 0) {
            numberOfQuestions.setValue(50);
            return;
        }
        OpenTriviaDB openTriviaDB = retrofit.create(OpenTriviaDB.class);
        Call<QuestionCount> call = openTriviaDB.getNumberOfQuestionsByCategory(category.getId());
        call.enqueue(new Callback<QuestionCount>() {
            @Override
            public void onResponse(Call<QuestionCount> call, Response<QuestionCount> response) {
                int ret = 50;

                switch(difficulty) {
                    case ("easy") :
                        ret = response.body().getCategory_question_count().getTotal_easy_question_count();
                        break;
                    case ("medium") :
                        ret = response.body().getCategory_question_count().getTotal_medium_question_count();
                        break;
                    case ("hard") :
                        ret = response.body().getCategory_question_count().getTotal_hard_question_count();
                        break;
                    case("any") :
                        ret = response.body().getCategory_question_count().getTotal_question_count();
                        break;
                }
                if(ret <= 50)
                    numberOfQuestions.setValue(ret);
                else numberOfQuestions.setValue(50);
            }
            @Override
            public void onFailure(Call<QuestionCount> call, Throwable t) {
            }
        });
    }
}