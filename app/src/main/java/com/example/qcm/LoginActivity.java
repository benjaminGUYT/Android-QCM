package com.example.qcm;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.qcm.models.Category;
import com.example.qcm.models.ListCategories;
import com.example.qcm.models.UserIoT;
import com.example.qcm.repositories.Api;
import com.example.qcm.repositories.OpenTriviaDB;
import com.google.gson.Gson;
import com.koushikdutta.ion.Ion;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity  extends AppCompatActivity {

    private Retrofit retrofit;
    Api api;
    boolean isConnected;
    String logResp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        EditText unTv = findViewById(R.id.unTv);
        EditText pwTv = findViewById(R.id.pwTv);
        Button login = findViewById(R.id.butLog);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(unTv.getText().toString().equals("") || unTv.getText().toString() == null || pwTv.getText().toString().equals("") || pwTv.getText().toString() == null) {
                    Toast toast = Toast.makeText(getApplicationContext(),"Cette combinaison est impossible, veuillez renseigner les deux champs", Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }
                else {

                    String urlString =
                            "http://192.168.10.111/iot/api/login.php?key=iot1235&username="+unTv.getText().toString()+"&password="+pwTv.getText().toString();
                    Ion.with(getApplicationContext())
                            .load(urlString)
                            .asString().withResponse()
                            .setCallback((e, response) -> {
                                if (response.getHeaders().code() == 200) {
                                    System.out.println(response.getResult());
                                    Gson gson = new Gson();
                                    UserIoT userIoT = gson.fromJson(response.getResult(), UserIoT.class);
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    intent.putExtra("userJson", userIoT);
                                    startActivity(intent);
                                }
                                else {
                                    Toast toast = Toast.makeText(getApplicationContext(),"ID/MDP Incorrect !", Toast.LENGTH_LONG);
                                    toast.show();
                                    return;
                                }
                            });

                }
            }
        });

    }

    public void checkLogin(String username, String password) {

       retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.10.111/iot/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(Api.class);
        Call<UserIoT> call = api.getUser("iot1235", username, password);
        System.out.println(call.request().url().toString());

        call.enqueue(new Callback<UserIoT>() {
                         @Override
                         public void onResponse(Call<UserIoT> call, Response<UserIoT> response) {
                             isConnected = true;
                         }

                         @Override
                         public void onFailure(Call<UserIoT> call, Throwable t) {
                         }
                     });

        if(isConnected) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("userJson", logResp);
            startActivity(intent);
        }
        else {
            Toast toast = Toast.makeText(this, "Username ou Password incorrect", Toast.LENGTH_LONG);
            toast.show();
            return;
        }
       /** String urlString =
               "http://192.168.10.111/iot/api/login.php?key=iot1235&username="+username+"&password="+password;
        Ion.with(this)
                .load(urlString)
                .asString().withResponse()
                .setCallback((e, response) -> {
                    if (response.getResult() != null) {
                        Intent intent = new Intent(this, MainActivity.class);
                        intent.putExtra("userJson", response.getResult());
                        startActivity(intent);
                    }
                    else {
                        Toast toast = Toast.makeText(this, "Username ou Password incorrect", Toast.LENGTH_LONG);
                        toast.show();
                        return;
                    }
                });*/
    }
}
