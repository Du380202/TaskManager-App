package com.example.taskmanagerapp.activity.user;

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
import com.example.taskmanagerapp.activity.admin.TaskDetailActivity;
import com.example.taskmanagerapp.activity.login.DateUtils;
import com.example.taskmanagerapp.activity.login.UserInfo;
import com.example.taskmanagerapp.api.TaskApi;
import com.example.taskmanagerapp.api.UserApi;
import com.example.taskmanagerapp.dto.Task;
import com.example.taskmanagerapp.dto.TaskDetail;
import com.example.taskmanagerapp.dto.User;
import com.example.taskmanagerapp.model.ResponseObject;
import com.example.taskmanagerapp.retrofit.ApiService;

import java.sql.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskUserActivity extends AppCompatActivity {
    private FrameLayout overlay;
    String[] statusArray = {"In Process", "Completed"};
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
        setContentView(R.layout.activity_task_user);
        setControl();
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return;
        Task taskDetail;
        taskDetail = (Task) bundle.getSerializable("Object:", Task.class);
        if (taskDetail != null) {
            setData(taskDetail);
        }
        adapterStatus = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, statusArray);

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
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overlay.setVisibility(View.GONE);
            }
        });
        ImageView backIcon = findViewById(R.id.back_icon);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        } );
        btnSaveFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData(taskDetail.getTaskId());
                tvStatus.setText(selectedStatus);
                Toast.makeText(TaskUserActivity.this, "Successfull!!",Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(TaskUserActivity.this, "Status: Done", Toast.LENGTH_SHORT).show();
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

    private void setData(Task task) {
        tvTaskName.setText("Task Name: " + task.getTaskName());
        tvDescp.setText("Description: " + task.getDescription());
        tvStartDate.setText("Start Day: " + task.getCreatedAt());
        tvEndDate.setText("End day: " + task.getDueDate());
        tvProjectID.setText("Project: " + task.getProjectId());
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



}