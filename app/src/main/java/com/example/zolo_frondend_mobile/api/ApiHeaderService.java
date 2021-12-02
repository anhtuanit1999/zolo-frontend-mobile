package com.example.zolo_frondend_mobile.api;

import com.example.zolo_frondend_mobile.entity.StatusCode204VerifyOTP;
import com.example.zolo_frondend_mobile.utils.JWTUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiHeaderService {

    OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request newRequest  = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer " + JWTUtils.TOKEN)
                    .build();
            return chain.proceed(newRequest);
        }
    }).build();

    Gson gson = new GsonBuilder()
            .setLenient()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    ApiHeaderService apiService = new Retrofit.Builder()
            .baseUrl("https://zolo-backend.herokuapp.com/")
//            .baseUrl("http://localhost:3000/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
            .create(ApiHeaderService.class);

    @POST("auth/changepassword")
    Call<StatusCode204VerifyOTP> getNewPassword(@Body Map<String, String> changes);


}
