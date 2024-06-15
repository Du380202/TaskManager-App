package com.example.taskmanagerapp.api;

import com.example.taskmanagerapp.dto.User;
import com.example.taskmanagerapp.model.ResponseObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserApi {

    @GET("user/All")
    Call<ResponseObject> getAllEmployee();

    @GET("user/{username}")
    Call<User> getUser(@Path("username") String username);

    @POST("user/create")
    Call<ResponseObject> createUser(@Body User user);


}
