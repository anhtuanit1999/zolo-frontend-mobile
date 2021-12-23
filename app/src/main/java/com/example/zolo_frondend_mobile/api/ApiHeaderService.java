package com.example.zolo_frondend_mobile.api;

import com.example.zolo_frondend_mobile.chat.StatusChat;
import com.example.zolo_frondend_mobile.chat.StatusFileS3;
import com.example.zolo_frondend_mobile.chat.StatusGetOneGroup;
import com.example.zolo_frondend_mobile.chat.StatusGroupSingle;
import com.example.zolo_frondend_mobile.chat.StatusMessageGet;
import com.example.zolo_frondend_mobile.chat.StatusMessageGetOne;
import com.example.zolo_frondend_mobile.danhsach.Friend;
import com.example.zolo_frondend_mobile.danhsach.Status200AddMember;
import com.example.zolo_frondend_mobile.entity.GetFriend;
import com.example.zolo_frondend_mobile.entity.GetGroup;
import com.example.zolo_frondend_mobile.entity.StatusCode204VerifyOTP;
import com.example.zolo_frondend_mobile.utils.JWTUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

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
//            .baseUrl("https://zolo-backend.herokuapp.com/")
            .baseUrl("http://192.168.100.4:3000/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
            .create(ApiHeaderService.class);

    @POST("auth/changepassword")
    Call<StatusCode204VerifyOTP> getNewPassword(@Body Map<String, String> changes);

    @FormUrlEncoded
    @POST("friend/getall")
    Call<GetFriend> getFriends(@Field("limit") int limit, @Field("lastId") int lastId);

//    @GET("user/detail")
//    Call<GetFriend> getAllUsers();

    @GET("user/all")
    Call<List<Friend>> getAllUsers();

    @FormUrlEncoded
    @POST("friend/make")
    Call<StatusCode204VerifyOTP> inviteFriend(@Field("idFriend") long idFriend);

    @FormUrlEncoded
    @POST("friend/getinvite")
    Call<GetFriend> getInviteFriends(@Field("limit") int limit, @Field("lastId") int lastId);

    @FormUrlEncoded
    @POST("friend/deny")
    Call<StatusCode204VerifyOTP> denyFriend(@Field("idFriend") long idFriend);

    @FormUrlEncoded
    @POST("friend/add")
    Call<StatusCode204VerifyOTP> AcceptOrDeleteFriend(@Field("idFriend") long idFriend);

    @FormUrlEncoded
    @POST("group/get")
    Call<GetGroup> getGroup(@Field("limit") int limit, @Field("lastId") int lastId);

    @FormUrlEncoded
    @POST("group/create")
    Call<StatusCode204VerifyOTP> CreatGroup(@Field("idFriends") List<Long> idFriends, @Field("name") String nameGroup);

    @FormUrlEncoded
    @POST("group/getallmember")
    Call<GetFriend> getGroupMember(@Field("groupId") long groupId);

    @FormUrlEncoded
    @POST("message/getall")
    Call<StatusMessageGet> getAllMessage(@Field("chatgroupId") int chatgroupId, @Field("limit") int limit, @Field("lastId") int lastId);

    @FormUrlEncoded
    @POST("message/create")
    Call<StatusChat> createChat(@Field("chatgroupId") int chatgroupId, @Field("content") String content);

    @FormUrlEncoded
    @POST("group/getorcreatesingle")
    Call<StatusGroupSingle> getGroupSingle(@Field("idFriend") long idFriend);

    @FormUrlEncoded
    @POST("group/getgroupid")
    Call<StatusGetOneGroup> getGroupById(@Field("groupId") int groupId);

    @FormUrlEncoded
    @POST("message/getlastid")
    Call<StatusMessageGet> getLastMessId(@Field("chatgroupId") int groupId);

    @FormUrlEncoded
    @POST("message/getminid")
    Call<StatusMessageGet> getMinMessId(@Field("chatgroupId") int groupId);

    @FormUrlEncoded
    @POST("group/delete")
    Call<Status200AddMember> deleteMemberGroup(@Field("groupId") int groupId,
                                            @Field("idFriends") Integer idFriends);

    @FormUrlEncoded
    @POST("group/deletegroup")
    Call<StatusCode204VerifyOTP> deleteGroup(@Field("groupId") int groupId);


    @Multipart
    @POST("file")
    Call<StatusFileS3> sendFileS3(@Part MultipartBody.Part file);

}
