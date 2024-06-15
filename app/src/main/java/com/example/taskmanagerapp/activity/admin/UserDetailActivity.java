package com.example.taskmanagerapp.activity.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.taskmanagerapp.R;
import com.example.taskmanagerapp.adapter.TaskAdapter;
import com.example.taskmanagerapp.api.TaskApi;
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

public class UserDetailActivity extends AppCompatActivity {
    Button btnSave, btnCancel, btnTaskUser;
    RecyclerView taskByUser;
    EditText edtFullName, edtUsername, edtPassword, edtEmail;
    private TaskAdapter taskAdapter;
    ArrayList<TaskDetail> taskList;
    boolean check = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        setControl();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        taskByUser.setLayoutManager(layoutManager);
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return;
        User userDetail;
        userDetail = (User) bundle.getSerializable("Object:", User.class);
        edtFullName.setText(userDetail.getFullName());
        edtEmail.setText(userDetail.getEmail());
        ImageView backIcon = findViewById(R.id.back_iconUser);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        } );
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });

        btnTaskUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataTask(userDetail.getUserId());
                if(!check) {
                    taskByUser.setVisibility(View.VISIBLE);
                    btnTaskUser.setText("Hidden Task");
                    check = true;
                }
                else {
                    taskByUser.setVisibility(View.GONE);
                    btnTaskUser.setText("View Task");
                    check = false;
                }
            }
        });

    }

    private void setControl() {
        btnSave = findViewById(R.id.btnSaveUser);
        btnCancel = findViewById(R.id.btnTaskUser);
        edtFullName = findViewById(R.id.edtFullName);
        edtEmail = findViewById(R.id.edtEmail);
        taskByUser = findViewById(R.id.taskByUser);
        btnTaskUser = findViewById(R.id.btnTaskUser);
    }

    private void getDataTask(Integer userId) {

        ApiService apiService = new ApiService();
        TaskApi taskApi = apiService.getRetrofit().create(TaskApi.class);
        taskApi.getTaskByUser(userId).enqueue(new Callback<ResponseObject>() {
            @Override
            public void onResponse(Call<ResponseObject> call, Response<ResponseObject> response) {
                if (response.body() != null) {
                    ResponseObject responseObject = response.body();
                    Log.d("myLog", "Response message: " + response.message());

                    Gson gson = new Gson();
                    Type taskListType = new TypeToken<ArrayList<TaskDetail>>(){}.getType();
                    ArrayList<TaskDetail> taskList = gson.fromJson(gson.toJson(responseObject.getData()), taskListType);
                    Log.d("myLog", "Response message: " + taskList.size()+"");
                    if (taskList.size() == 0) {
                        Toast.makeText(UserDetailActivity.this, "Nhân viên chưa có công việc!!", Toast.LENGTH_SHORT).show();
                    }
                    taskAdapter = new TaskAdapter(UserDetailActivity.this, taskList);
                    taskByUser.setAdapter(taskAdapter);
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