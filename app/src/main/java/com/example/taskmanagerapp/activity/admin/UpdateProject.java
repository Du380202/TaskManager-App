package com.example.taskmanagerapp.activity.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taskmanagerapp.R;
import com.example.taskmanagerapp.adapter.TaskAdapter;
import com.example.taskmanagerapp.api.ProjectApi;
import com.example.taskmanagerapp.api.TaskApi;
import com.example.taskmanagerapp.dto.Project;
import com.example.taskmanagerapp.dto.TaskDetail;
import com.example.taskmanagerapp.model.ResponseObject;
import com.example.taskmanagerapp.retrofit.ApiService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProject extends AppCompatActivity {
    Button btnShowTask,btnUpdate, btnSaveFrame;
    ImageView btnClose ;
    TextView tvProjectName, tvDescp, tvStartDate, tvEndDate, tvProjectID, tvStatus;
    int selectedPercentage;
    final Integer[] percentages = new Integer[] {
            0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100
    };

    final String[] percentageStrings = new String[] {
            "0%", "10%", "20%", "30%", "40%", "50%", "60%", "70%", "80%", "90%", "100%"
    };
    String[] statusArray = {"In Process", "Completed", "Cancelled"};
    ProgressBar progressBarProject;
    RecyclerView taskByProject;
    FrameLayout frame;
    private TaskAdapter taskAdapter;
    boolean check = false;
    AutoCompleteTextView autoProgress, autoStatus;
    ArrayAdapter<String> adapterProgress, adapterStatus;

    ArrayList<TaskDetail> taskList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_project);
        setControl();
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return;
        Project projectDetail;
        projectDetail = (Project) bundle.getSerializable("Object:", Project.class);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        taskByProject.setLayoutManager(layoutManager);
        tvProjectName.setText(projectDetail.getProjectName());
        tvDescp.setText(projectDetail.getDescription());
        tvStartDate.setText(projectDetail.getCreatedAt());
        tvEndDate.setText(projectDetail.getEndDate());
        tvStatus.setText(projectDetail.getStatus());
        progressBarProject.setProgress(projectDetail.getTienDo());
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frame.setVisibility(View.VISIBLE);
                autoProgress.setOnItemClickListener((parent, view, position, id) -> {
                    selectedPercentage = percentages[position];
                    Toast.makeText(UpdateProject.this, "Selected: " + selectedPercentage, Toast.LENGTH_SHORT).show();

                });
            }
        });
        btnSaveFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData(projectDetail.getProjectId());
                frame.setVisibility(View.GONE);
                progressBarProject.setProgress(selectedPercentage);
                Toast.makeText(UpdateProject.this, "Successfull " + selectedPercentage, Toast.LENGTH_SHORT).show();
            }
        });

        btnShowTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataTask(projectDetail.getProjectId());
                if(!check) {
                    taskByProject.setVisibility(View.VISIBLE);
                    btnShowTask.setText("Hidden Task");
                    check = true;
                }
                else {
                    taskByProject.setVisibility(View.GONE);
                    btnShowTask.setText("View Task");
                    check = false;
                }
            }
        });
        ImageView backIcon = findViewById(R.id.back_icon);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        } );
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frame.setVisibility(View.GONE);
            }
        });
    }
    private void setControl() {
        btnClose = findViewById(R.id.btnClose);
        btnSaveFrame = findViewById(R.id.btnSaveFrame);
        btnShowTask = findViewById(R.id.btnShowTask);
        taskByProject = findViewById(R.id.taskByProject);
        tvProjectName = findViewById(R.id.tvTaskName);
        tvDescp = findViewById(R.id.tvDescp);
        tvStartDate = findViewById(R.id.tvStartDate);
        tvEndDate = findViewById(R.id.tvEndDate);
        tvProjectID = findViewById(R.id.tvProjectID);
        tvStatus = findViewById(R.id.tvStatus);
        frame = findViewById(R.id.overlay);
        autoProgress = findViewById(R.id.edtProgressin);
        autoStatus = findViewById(R.id.autoStatus);
        progressBarProject = findViewById(R.id.progressBarProject);
        btnUpdate = findViewById(R.id.btnUpdate);
        adapterProgress = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, percentageStrings);
        autoProgress.setAdapter(adapterProgress);
        adapterStatus = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, statusArray);
        autoStatus.setAdapter(adapterStatus);
    }

    private void update() {

    }

    private void sendData(Integer projectId) {
        String des = tvDescp.getText().toString();
        String status = autoStatus.getText().toString();
        Project project = new Project(projectId,des, status, selectedPercentage );
        ApiService apiService = new ApiService();
        ProjectApi projectApi = apiService.getRetrofit().create(ProjectApi.class);
        projectApi.updateProgress(projectId, project).enqueue(new Callback<ResponseObject>() {
            @Override
            public void onResponse(Call<ResponseObject> call, Response<ResponseObject> response) {
                ResponseObject responseObject = response.body();
                Boolean ischecked = (Boolean) responseObject.getData();
                if (ischecked) {
                    Log.d("myLog", responseObject.getMessage());
                }
                else {
                    Log.d("myLog", "Them thay bai");
                }
            }

            @Override
            public void onFailure(Call<ResponseObject> call, Throwable throwable) {
                Log.d("myLog", throwable.getMessage());
            }
        });
    }

    private void getDataTask(Integer projectId) {

        ApiService apiService = new ApiService();
        TaskApi taskApi = apiService.getRetrofit().create(TaskApi.class);
        taskApi.getTaskByProject(projectId).enqueue(new Callback<ResponseObject>() {
            @Override
            public void onResponse(Call<ResponseObject> call, Response<ResponseObject> response) {
                if (response.body() != null) {
                    ResponseObject responseObject = response.body();
                    Log.d("myLog", "Response message: " + response.message());

                    Gson gson = new Gson();
                    Type taskListType = new TypeToken<ArrayList<TaskDetail>>(){}.getType();
                    ArrayList<TaskDetail> taskList = gson.fromJson(gson.toJson(responseObject.getData()), taskListType);
                    Log.d("myLog", "Response message: " + taskList.size()+"");
                    taskAdapter = new TaskAdapter(UpdateProject.this, taskList);
                    taskByProject.setAdapter(taskAdapter);
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