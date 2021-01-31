package com.example.qcm.repositories;

import com.example.qcm.models.CategoryQuestionCount;
import com.example.qcm.models.ListCategories;
import com.example.qcm.models.ListQuestions;
import com.example.qcm.models.Question;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenTriviaDB {

    @GET("api_category.php")
    Call<ListCategories> getCategories();

    @GET("api_count.php")
    Call<CategoryQuestionCount> getNumberOfQuestionsByCategory(@Query("category") int category);

    @GET("api.php")
    Call<ListQuestions> getListQuestionsByAmountAndCategoryAndDifficulty(
            @Query("amount") int amount,
            @Query("category") String category,
            @Query("difficulty") String difficulty);

}