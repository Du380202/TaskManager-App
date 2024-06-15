package com.example.taskmanagerapp.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.taskmanagerapp.R;
import com.example.taskmanagerapp.activity.admin.AddTaskActivity;
import com.example.taskmanagerapp.activity.login.UserInfo;
import com.example.taskmanagerapp.activity.user.TaskUserActivity;
import com.example.taskmanagerapp.adapter.TaskAdapter;
import com.example.taskmanagerapp.adapter.TaskUserAdapter;
import com.example.taskmanagerapp.api.TaskApi;
import com.example.taskmanagerapp.dto.Task;
import com.example.taskmanagerapp.dto.TaskDetail;
import com.example.taskmanagerapp.dto.User;
import com.example.taskmanagerapp.model.ResponseObject;
import com.example.taskmanagerapp.retrofit.ApiService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskUserFragment extends Fragment {

    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private TaskUserAdapter taskAdapter;
    User user = UserInfo.user;
    ArrayList<Task> taskList;

    public TaskUserFragment(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }

    public TaskUserFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_task_user, container, false);
        recyclerView = view.findViewById(R.id.recyclerTaskUser);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //Log.d("myLog", user.getUserId() + "");
        ApiService apiService = new ApiService();
        TaskApi taskApi = apiService.getRetrofit().create(TaskApi.class);
        taskApi.getTaskByUser(user.getUserId()).enqueue(new Callback<ResponseObject>() {
            @Override
            public void onResponse(Call<ResponseObject> call, Response<ResponseObject> response) {
                if (response.body() != null) {
                    ResponseObject responseObject = response.body();
                    Log.d("myLog", "Response message: " + response.message());

                    Gson gson = new Gson();
                    Type taskListType = new TypeToken<ArrayList<Task>>(){}.getType();
                    ArrayList<Task> taskList = gson.fromJson(gson.toJson(responseObject.getData()), taskListType);
                    taskAdapter = new TaskUserAdapter(getActivity(), taskList);
                    recyclerView.setAdapter(taskAdapter);
                } else {
                    Log.d("myLog", "Response body is null");
                }
            }

            @Override
            public void onFailure(Call<ResponseObject> call, Throwable throwable) {
                Log.e("Object: ", throwable.getMessage());
            }
        });

        return view;
    }
}