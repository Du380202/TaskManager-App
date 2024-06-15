package com.example.taskmanagerapp.activity.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.taskmanagerapp.R;
import com.example.taskmanagerapp.api.UserApi;
import com.example.taskmanagerapp.dto.User;
import com.example.taskmanagerapp.fragment.EmployeeFragment;
import com.example.taskmanagerapp.model.ResponseObject;
import com.example.taskmanagerapp.retrofit.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddUserActivity extends AppCompatActivity {
    Button btnSave, btnCancel;

    EditText edtFullName, edtUsername, edtPassword, edtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        setControl();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();
                Toast.makeText(AddUserActivity.this, "Successfull", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });
        ImageView backIcon = findViewById(R.id.back_iconUser);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        } );
    }
    private void setControl() {
        btnSave = findViewById(R.id.btnSaveUser);
        btnCancel = findViewById(R.id.btnCancelUser);
        edtFullName = findViewById(R.id.edtFullName);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        edtEmail = findViewById(R.id.edtEmail);
    }

    private void sendData() {
        String name = edtFullName.getText().toString();
        String username = edtUsername.getText().toString();
        String pass = edtPassword.getText().toString();
        String email = edtEmail.getText().toString();
        String createdAt = "2024-06-09";
        User user = new User(username, pass,email,name, createdAt);
        ApiService apiService = new ApiService();
        UserApi userApi = apiService.getRetrofit().create(UserApi.class);
        userApi.createUser(user).enqueue(new Callback<ResponseObject>() {
            @Override
            public void onResponse(Call<ResponseObject> call, Response<ResponseObject> response) {
                ResponseObject responseObject = response.body();
                Boolean ischecked = (Boolean) responseObject.getData();
                if (ischecked) {
                    Log.d("myLog", responseObject.getMessage());
                }
                else {
                    Log.d("myLog", "Them that bai");
                }
            }

            @Override
            public void onFailure(Call<ResponseObject> call, Throwable throwable) {
                Log.d("myLog", throwable.getMessage() + "");
            }
        });
    }



}