package com.example.taskmanagerapp.activity.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.taskmanagerapp.R;
import com.example.taskmanagerapp.adapter.TaskAdapter;
import com.example.taskmanagerapp.api.TaskApi;
import com.example.taskmanagerapp.dto.Project;
import com.example.taskmanagerapp.dto.TaskDetail;
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

public class ViewTask extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private TaskAdapter taskAdapter;

    ArrayList<TaskDetail> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);
        recyclerView = findViewById(R.id.recyclerTaskPro);

        // Thiết lập LayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return;
        Project projectDetail;
        projectDetail = (Project) bundle.getSerializable("Project", Project.class);
        ApiService apiService = new ApiService();
        TaskApi taskApi = apiService.getRetrofit().create(TaskApi.class);
        taskApi.getTaskByProject(projectDetail.getProjectId()).enqueue(new Callback<ResponseObject>() {
            @Override
            public void onResponse(Call<ResponseObject> call, Response<ResponseObject> response) {
                if (response.body() != null) {
                    ResponseObject responseObject = response.body();
                    Log.d("myLog", "Response projectDetail: " + response.message());

                    Gson gson = new Gson();
                    Type taskListType = new TypeToken<ArrayList<TaskDetail>>(){}.getType();
                    ArrayList<TaskDetail> taskList = gson.fromJson(gson.toJson(responseObject.getData()), taskListType);
                    taskAdapter = new TaskAdapter(ViewTask.this, taskList);
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
    }
}
