package com.example.qcm.ui.gallery;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.qcm.models.Category;
import com.example.qcm.models.CategoryQuestionCount;
import com.example.qcm.models.ListCategories;
import com.example.qcm.models.ListQuestions;
import com.example.qcm.models.Question;
import com.example.qcm.repositories.OpenTriviaDB;

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
    private MutableLiveData<ListQuestions> listQuestions;

    private Retrofit retrofit;
    OpenTriviaDB openTriviaDB;



    public GalleryViewModel() {
        numberOfQuestions = new MutableLiveData<>(50);

        mText = new MutableLiveData<>();
        mText.setValue("Veuillez entrer ci-dessous les options du QCM");

        listQuestions = new MutableLiveData<>();
        listQuestions.setValue(new ListQuestions());

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

        openTriviaDB = retrofit.create(OpenTriviaDB.class);

        Call<ListCategories> call = openTriviaDB.getCategories();

        call.enqueue(new Callback<ListCategories>() {
            @Override
            public void onResponse(Call<ListCategories> call, Response<ListCategories> response) {
                for(Category cat : response.body().getCategories())
                    list.add(cat);
                listCategories.setValue(list);
            }
            @Override
            public void onFailure(Call<ListCategories> call, Throwable t) {
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

    public LiveData<ListQuestions> getListQuestions() { return listQuestions; }

    public void getNumberOfQuestionsByCategoryAndDifficulty(Category category, String difficulty) {

        System.out.println(category.getName() + " " + difficulty);

        if(category.getId() == 0) {
            numberOfQuestions.setValue(50);
            return;
        }

        Call<CategoryQuestionCount> call = openTriviaDB.getNumberOfQuestionsByCategory(category.getId());
        call.enqueue(new Callback<CategoryQuestionCount>() {
            @Override
            public void onResponse(Call<CategoryQuestionCount> call, Response<CategoryQuestionCount> response) {
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
            public void onFailure(Call<CategoryQuestionCount> call, Throwable t) {
            }
        });
    }

    public void getNumberOfQuestionsByCategoryAndDifficultyAndType(int amount, Category selectedCategory, String selectedDifficulty, String selectedType) {
        if(selectedCategory.getId() == 0) {
            numberOfQuestions.setValue(50);
            return;
        }

        System.out.println("Amount : " + amount + '\n' + "category : " + String.valueOf(selectedCategory.getId()) + '\n'
        + "difficulty : " + selectedDifficulty + '\n' + "type : " + selectedType);

        Call<ListQuestions> call = null;

        if(selectedCategory.getId() == 0 && selectedDifficulty == "any")
            call = openTriviaDB.getListQuestionsByAmountAndCategoryAndDifficultyAndType(amount, "", "", selectedType);
        else if(selectedCategory.getId() == 0)
            call = openTriviaDB.getListQuestionsByAmountAndCategoryAndDifficultyAndType(amount, "", selectedDifficulty, selectedType);
        else if(selectedDifficulty.equals("any"))
            call = openTriviaDB.getListQuestionsByAmountAndCategoryAndDifficultyAndType(amount, String.valueOf(selectedCategory.getId()), "", selectedType);
        else
            call = openTriviaDB.getListQuestionsByAmountAndCategoryAndDifficultyAndType(amount, String.valueOf(selectedCategory.getId()), selectedDifficulty, selectedType);

        System.out.println(call.request().url());

        call.enqueue(new Callback<ListQuestions>() {
            @Override
            public void onResponse(Call<ListQuestions> call, Response<ListQuestions> response) {
                numberOfQuestions.setValue(response.body().getResults().size());
            }
            @Override
            public void onFailure(Call<ListQuestions> call, Throwable t) {
            }
        });
    }

    public void getQuestionsByCategoryAndDifficultyAndType(int amount, Category selectedCategory, String selectedDifficulty, String selectedType) {
        if(selectedCategory.getId() == 0) {
            numberOfQuestions.setValue(50);
            return;
        }

        Call<ListQuestions> call = openTriviaDB.getListQuestionsByAmountAndCategoryAndDifficultyAndType(amount, String.valueOf(selectedCategory.getId()), selectedDifficulty, selectedType);
        call.enqueue(new Callback<ListQuestions>() {
            @Override
            public void onResponse(Call<ListQuestions> call, Response<ListQuestions> response) {
                listQuestions.setValue(response.body());
            }
            @Override
            public void onFailure(Call<ListQuestions> call, Throwable t) {
            }
        });
    }


}