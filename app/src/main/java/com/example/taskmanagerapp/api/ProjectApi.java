package com.example.taskmanagerapp.api;

import com.example.taskmanagerapp.dto.Project;
import com.example.taskmanagerapp.dto.Task;
import com.example.taskmanagerapp.model.ResponseObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ProjectApi {

    @GET("project")
    Call<ResponseObject> getAllProject();

    @POST("project/create")
    Call<ResponseObject> createNewProject(@Body Project project);

    @POST("project/update/{id}")
    Call<ResponseObject> updateProject(@Path("id") Integer taskId, @Body Project project);
    @POST("project/updateProgress/{id}")
    Call<ResponseObject> updateProgress(@Path("id") Integer projectId, @Body Project project);

}
