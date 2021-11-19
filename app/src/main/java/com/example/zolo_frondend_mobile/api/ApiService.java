package com.example.zolo_frondend_mobile.api;

import com.example.zolo_frondend_mobile.entity.SignIn;
import com.example.zolo_frondend_mobile.entity.StatusCode200SignIn;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {
    Gson gson = new GsonBuilder()
            .setLenient()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    ApiService apiService = new Retrofit.Builder()
            .baseUrl("https://zolo-backend.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);



    @POST("auth/signin")
    Call<StatusCode200SignIn> getUserSignIn(@Body SignIn signIn);

    @FormUrlEncoded
    @POST("auth/signin")
    Call<StatusCode200SignIn> getUserSignIna(@Field("email") String first, @Field("password") String first1);
}
