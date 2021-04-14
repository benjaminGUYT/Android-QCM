package com.example.qcm.ui.friend;

import androidx.lifecycle.ViewModel;

import com.example.qcm.models.Category;
import com.example.qcm.models.FriendsIoT;
import com.example.qcm.models.ListCategories;
import com.example.qcm.repositories.Api;
import com.example.qcm.repositories.OpenTriviaDB;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FriendViewModel extends ViewModel {

    Retrofit retrofit;
    Api api;
    public FriendViewModel() {

        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.10.111/iot/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(Api.class);

        Call<List<FriendsIoT>> call = api.getUserList("iot1235", "26d11c8e68899f2fef671e3331da3a8f");

        call.enqueue(new Callback<List<FriendsIoT>>() {
            @Override
            public void onResponse(Call<List<FriendsIoT>> call, Response<List<FriendsIoT>> response) {

            }
            @Override
            public void onFailure(Call<List<FriendsIoT>> call, Throwable t) {
            }
        });

    }
}
