package com.example.taskmanagerapp.api;

import com.example.taskmanagerapp.dto.Task;
import com.example.taskmanagerapp.model.ResponseObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TaskApi {

    @GET("task/details")
    Call<ResponseObject> getAllTaskDetail();

    @GET("task/taskByUser/{userId}")
    Call<ResponseObject> getTaskByUser(@Path("userId") Integer userId);

    @GET("task/project/{projectId}")
    Call<ResponseObject> getTaskByProject(@Path("projectId") Integer projectId);

    @GET("task/activity/{taskId}")
    Call<ResponseObject> getActivityLog(@Path("taskId") Integer taskId);

    @POST("task/create")
    Call<ResponseObject> createNewTask(@Body Task task);

    @POST("task/employee/update/{taskId}")
    Call<ResponseObject> updateTaskUser(@Path("taskId") Integer taskId, @Body Task task);

    @POST
    Call<Task> updateTask(@Body Task task);

}
