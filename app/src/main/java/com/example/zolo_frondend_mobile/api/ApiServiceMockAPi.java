package com.example.zolo_frondend_mobile.api;

import com.example.zolo_frondend_mobile.entity.StatusCode200SignIn;
import com.example.zolo_frondend_mobile.model.UserMockAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiServiceMockAPi {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    ApiServiceMockAPi apiService = new Retrofit.Builder()
            .baseUrl("https://60aafdfc66f1d0001777356d.mockapi.io/api/users/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiServiceMockAPi.class);



    @GET("user3")
    Call<List<UserMockAPI>> getList();

    @POST("user3")
    Call<UserMockAPI> addUser(@Body UserMockAPI user);

}
