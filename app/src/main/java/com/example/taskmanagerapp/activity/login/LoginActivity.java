package com.example.taskmanagerapp.activity.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.taskmanagerapp.MainActivity;
import com.example.taskmanagerapp.R;
import com.example.taskmanagerapp.adapter.UserAdapter;
import com.example.taskmanagerapp.api.UserApi;
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

public class LoginActivity extends AppCompatActivity {
    EditText edtUsername, edtPassword;
    ArrayList<User> userList = new ArrayList<>();
    User userInfo;
    Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setControl();
        getAllUser();
    //    Log.d("myLog", userList.size()+"");
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("myLog", "cliclk");
                clickLogin();

            }


        });
    }
    private void clickLogin() {
        Log.d("myLog", userList.size()+"");
        String username = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();
        boolean isChecked = false;
        for (User user : userList) {
            if (username.equals(user.getUsername()) && PasswordUtils.checkPassword(password, user.getPassword())) {
                isChecked = true;
                UserInfo.user = user;
                break;
            }
        }

        if (isChecked) {
           // Toast.makeText(LoginActivity.this, "l " + userInfo.toString(), Toast.LENGTH_SHORT).show();
            Intent detail = new Intent(LoginActivity.this, MainActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("Object:" , userInfo);
//            detail.putExtras(bundle);
            startActivity(detail);
        }
        else {

            Toast.makeText(LoginActivity.this, "Login fail" + userList.size(), Toast.LENGTH_SHORT).show();
        }


    }
    private void setControl() {
        edtUsername = findViewById(R.id.username);
        edtPassword = findViewById(R.id.password);
        btnLogin = findViewById(R.id.loginButton);
    }

    private void getAllUser() {
        ApiService apiService = new ApiService();
        UserApi userApi = apiService.getRetrofit().create(UserApi.class);
        userApi.getAllEmployee().enqueue(new Callback<ResponseObject>() {
            @Override
            public void onResponse(Call<ResponseObject> call, Response<ResponseObject> response) {
                if (response.body() != null) {
                    ResponseObject responseObject = response.body();
                    Log.d("myLog", "Response message: " + response.message());
                    Log.d("myLog", "Response message: " + userList.size());
                    Gson gson = new Gson();
                    Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
                    userList = gson.fromJson(gson.toJson(responseObject.getData()), userListType);
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