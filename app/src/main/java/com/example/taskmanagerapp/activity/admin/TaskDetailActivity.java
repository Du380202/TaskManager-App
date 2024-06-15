package com.example.taskmanagerapp.activity.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taskmanagerapp.R;
import com.example.taskmanagerapp.activity.login.DateUtils;
import com.example.taskmanagerapp.activity.login.UserInfo;
import com.example.taskmanagerapp.activity.user.TaskUserActivity;
import com.example.taskmanagerapp.adapter.TaskAdapter;
import com.example.taskmanagerapp.api.TaskApi;
import com.example.taskmanagerapp.dto.Task;
import com.example.taskmanagerapp.dto.TaskDetail;
import com.example.taskmanagerapp.dto.User;
import com.example.taskmanagerapp.model.ResponseObject;
import com.example.taskmanagerapp.retrofit.ApiService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskDetailActivity extends AppCompatActivity {
    private FrameLayout overlay;
    String[] statusArray = {"In Process", "Completed", "Cancelled"};
    Button btnSave, btnUpdate, btnSaveFrame;
    ImageView btnClose;
    EditText frameContent;
    TextView tvTaskName, tvDescp, tvStartDate, tvEndDate, tvProjectID, tvStatus;
    AutoCompleteTextView autoStatus;
    ArrayAdapter<String> adapterStatus;
    private String selectedStatus;
    User user = UserInfo.user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        setControl();
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return;
        TaskDetail taskDetail;
        taskDetail = (TaskDetail) bundle.getSerializable("Object:", TaskDetail.class);
        adapterStatus = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, statusArray);
        setData(taskDetail);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overlay.setVisibility(View.VISIBLE);
                autoStatus.setAdapter(adapterStatus);
                autoStatus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        selectedStatus = parent.getItemAtPosition(position).toString();
                    }
                });
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
                overlay.setVisibility(View.GONE);
            }
        });

        btnSaveFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData(taskDetail.getTask_id());
                tvStatus.setText(selectedStatus);
                Toast.makeText(TaskDetailActivity.this, "Successfull!!",Toast.LENGTH_SHORT).show();
                overlay.setVisibility(View.GONE);
            }

        });
    }

    private void sendData(Integer taskId) {
        String description = frameContent.getText().toString();
        String status = selectedStatus;
        String updateDate = DateUtils.getCurrentDateSQLFormat();
        Task task = new Task(taskId, description, status, updateDate);
        ApiService apiService = new ApiService();
        TaskApi taskApi = apiService.getRetrofit().create(TaskApi.class);
        taskApi.updateTaskUser(taskId, task).enqueue(new Callback<ResponseObject>() {
            @Override
            public void onResponse(Call<ResponseObject> call, Response<ResponseObject> response) {
                ResponseObject responseObject = response.body();
                Boolean ischecked = (Boolean) responseObject.getData();
                if (ischecked) {
                    Toast.makeText(TaskDetailActivity.this, "Status: Done", Toast.LENGTH_SHORT).show();
                    Log.d("myLog", responseObject.getMessage());
                }
                else {
                    Log.d("myLog", "Them thay bai");
                }
            }

            @Override
            public void onFailure(Call<ResponseObject> call, Throwable throwable) {

            }
        });
    }

    private void setData(TaskDetail task) {
        tvTaskName.setText("Task Name: " + task.getTaskName());
        tvStartDate.setText("Start Day: " + task.getCreated_at());
        tvEndDate.setText("End day: " + task.getDueDate());
        tvStatus.setText("Status: " + task.getStatus());
        autoStatus.setText(task.getStatus());
    }

    private void setControl() {
        autoStatus = findViewById(R.id.autoStatus);
        overlay = findViewById(R.id.overlay);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnSave = findViewById(R.id.btnSave);
        btnClose = findViewById(R.id.btnClose);
        btnSaveFrame = findViewById(R.id.btnSaveFrame);
        tvTaskName = findViewById(R.id.tvTaskName);
        tvDescp = findViewById(R.id.tvDescp);
        tvStartDate = findViewById(R.id.tvStartDate);
        tvEndDate = findViewById(R.id.tvEndDate);
        tvProjectID = findViewById(R.id.tvProjectID);
        tvStatus = findViewById(R.id.tvStatus);
        frameContent = findViewById(R.id.frameContent);
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
//                    taskAdapter = new TaskAdapter(UpdateProject.this, taskList);
//                    taskByProject.setAdapter(taskAdapter);
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