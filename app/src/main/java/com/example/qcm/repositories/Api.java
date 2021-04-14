package com.example.qcm.repositories;

import com.example.qcm.models.CategoryQuestionCount;
import com.example.qcm.models.FriendsIoT;
import com.example.qcm.models.ListCategories;
import com.example.qcm.models.ListQuestions;
import com.example.qcm.models.UserIoT;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface Api {

    @GET("login.php")
    Call<UserIoT> getUser(@Query("key") String key,@Query("username") String username, @Query("password") String password);

    @GET("getFriends.php")
    Call<List<FriendsIoT>> getUserList(@Query("key") String key, @Query("token") String token);

}
