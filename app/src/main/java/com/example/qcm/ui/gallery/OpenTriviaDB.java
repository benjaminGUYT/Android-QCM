package com.example.qcm.ui.gallery;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Part;

public interface OpenTriviaDB {

    @GET("api_category.php")
    Call<Result> getCategories();

}
