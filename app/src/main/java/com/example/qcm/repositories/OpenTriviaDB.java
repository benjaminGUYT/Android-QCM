package com.example.qcm.repositories;

import com.example.qcm.models.CategoryQuestionCount;
import com.example.qcm.models.ListCategories;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenTriviaDB {

    @GET("api_category.php")
    Call<ListCategories> getCategories();

    @GET("api_count.php")
    Call<CategoryQuestionCount> getNumberOfQuestionsByCategory(@Query("category") int category);


}
