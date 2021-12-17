package com.example.zolo_frondend_mobile.api;

import com.example.zolo_frondend_mobile.danhsach.Friend;
import com.example.zolo_frondend_mobile.entity.GetFriend;
import com.example.zolo_frondend_mobile.entity.ResendOTP;
import com.example.zolo_frondend_mobile.entity.SignIn;
import com.example.zolo_frondend_mobile.entity.SignUp;
import com.example.zolo_frondend_mobile.entity.StatusCode200SignIn;
import com.example.zolo_frondend_mobile.entity.StatusCode200SignUp;
import com.example.zolo_frondend_mobile.entity.StatusCode204VerifyOTP;
import com.example.zolo_frondend_mobile.entity.VerifyOTP;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.Map;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService {
    Gson gson = new GsonBuilder()
            .setLenient()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    ApiService apiService = new Retrofit.Builder()
            .baseUrl("https://zolo-backend.herokuapp.com/")
//            .baseUrl("http://192.168.100.4:3000/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);



    @POST("auth/signin")
    Call<StatusCode200SignIn> getUserSignIn(@Body SignIn signIn);

    @POST("auth/signin")
    Call<StatusCode204VerifyOTP> getUserSignInFail(@Body SignIn signIn);

    @POST("auth/signup")
    Call<StatusCode200SignUp> getUserSignUp(@Body SignUp signUp);

    @POST("auth/signup")
    Call<StatusCode204VerifyOTP> getUserSignUpFail(@Body SignUp signUp);

    @POST("auth/verify")
    Call<StatusCode204VerifyOTP> getVerifyOTP(@Body VerifyOTP verify);

    @POST("auth/resendotp")
    Call<StatusCode204VerifyOTP> getResendVerifyOTP(@Body ResendOTP resendOTP);

    @GET("auth/signout")
    Call<StatusCode204VerifyOTP> getSignout(@Header("Authorization") String authHeader);

    @FormUrlEncoded
    @POST("auth/changepassword")
    Call<StatusCode204VerifyOTP> getForgotPassWord(@Field("email") String email);

    @FormUrlEncoded
    @POST("auth/forgotpassword")
    Call<StatusCode204VerifyOTP> getForgotPassWordinMain(@Field("email") String email);

    @POST("auth/newpassword")
    Call<StatusCode204VerifyOTP> getNNewPassword(@Body Map<String, String> changes);

    @GET("users/all")
    Call<GetFriend> getAllUsers();
}
