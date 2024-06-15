package com.example.taskmanagerapp.activity.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.taskmanagerapp.R;
import com.example.taskmanagerapp.adapter.CustomProjectAdapter;
import com.example.taskmanagerapp.adapter.CustomUserAdapter;
import com.example.taskmanagerapp.adapter.TaskAdapter;
import com.example.taskmanagerapp.api.ProjectApi;
import com.example.taskmanagerapp.api.TaskApi;
import com.example.taskmanagerapp.api.UserApi;
import com.example.taskmanagerapp.dto.Project;
import com.example.taskmanagerapp.dto.Task;
import com.example.taskmanagerapp.dto.TaskDetail;
import com.example.taskmanagerapp.dto.User;
import com.example.taskmanagerapp.model.ResponseObject;
import com.example.taskmanagerapp.retrofit.ApiService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTaskActivity extends AppCompatActivity {

    Button btnCancel, btnSave, btnChooseEm, btnChoosePro;

    EditText edtTaskName, edtDescription, edtStartDate, edtEndDay, edtProject, edtEmployee, edtStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        setControl();
//        Toolbar toolbar = findViewById(R.id.toolbar1);
//        setSupportActionBar(toolbar);

        ImageView backIcon = findViewById(R.id.back_icon);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        } );

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendData();
                Toast.makeText(AddTaskActivity.this, "Successfull!!!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        edtStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStartDatePickerDialog();
            }
        });

        edtEndDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEndDatePickerDialog();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });
        btnChooseEm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEmployeeSelection();
            }
        });

        btnChoosePro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProjectSelection();
            }
        });
    }

    private void setControl() {
        btnChoosePro = findViewById(R.id.btnChoosePro);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        btnChooseEm = findViewById(R.id.btnChooseEm);
        edtTaskName = findViewById(R.id.edtTaskName);
        edtDescription = findViewById(R.id.edtDescp);
        edtStartDate = findViewById(R.id.edtStartDate);
        edtEndDay = findViewById(R.id.edtEndDate);
        edtProject = findViewById(R.id.edtProjectID);
        edtEmployee = findViewById(R.id.edtEmployee);
    }

    private void sendData() {
        String taskName = edtTaskName.getText().toString();
        String description = edtDescription.getText().toString();
        Date startDate = Date.valueOf(edtStartDate.getText().toString());
        Date endDate = Date.valueOf(edtEndDay.getText().toString());
        Integer projectId = Integer.valueOf(edtProject.getText().toString());
        Integer employee = Integer.valueOf(edtEmployee.getText().toString());
        Log.d("myLog", startDate.toString());
        Task task = new Task(taskName, description, "In process", endDate.toString(), startDate.toString(), startDate.toString(),employee, projectId);
        ApiService apiService = new ApiService();
        TaskApi taskApi = apiService.getRetrofit().create(TaskApi.class);
        taskApi.createNewTask(task).enqueue(new Callback<ResponseObject>() {
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

            }
        });

    }

    private void showStartDatePickerDialog() {
        // Get current date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // Create DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        // Create a Calendar object with the selected date
                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(Calendar.YEAR, year);
                        selectedDate.set(Calendar.MONTH, month);
                        selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        // Format the date as a string in "YYYY-MM-DD" format
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        String formattedDate = dateFormat.format(selectedDate.getTime());

                        // Set the formatted date into the EditText
                        edtStartDate.setText(formattedDate);
                    }
                }, year, month, dayOfMonth);

        // Show DatePickerDialog
        datePickerDialog.show();
    }

    private void showEndDatePickerDialog() {
        // Get current date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // Create DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        // Create a Calendar object with the selected date
                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(Calendar.YEAR, year);
                        selectedDate.set(Calendar.MONTH, month);
                        selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        // Format the date as a string in "YYYY-MM-DD" format
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        String formattedDate = dateFormat.format(selectedDate.getTime());

                        // Set the formatted date into the EditText
                        edtEndDay.setText(formattedDate);
                    }
                }, year, month, dayOfMonth);

        // Show DatePickerDialog
        datePickerDialog.show();
    }

    private void showEmployeeSelection() {
        ApiService apiService = new ApiService();
        UserApi userApi = apiService.getRetrofit().create(UserApi.class);
        userApi.getAllEmployee().enqueue(new Callback<ResponseObject>() {
            @Override
            public void onResponse(Call<ResponseObject> call, Response<ResponseObject> response) {
                ResponseObject responseObject = response.body();

                if (responseObject != null && responseObject.getData() != null) {
                    ArrayList<User> userList = new Gson().fromJson(new Gson().toJson(responseObject.getData()), new TypeToken<ArrayList<User>>(){}.getType());

                    if (userList != null && !userList.isEmpty()) {
                        // Tạo một Map để ánh xạ giữa tên và ID của người dùng
                        ArrayList<String> userNames = new ArrayList<>();
                        final ArrayList<Integer> userIds = new ArrayList<>(); // Danh sách ID của người dùng

                        for (User user : userList) {
                            userNames.add(user.getFullName());
                            userIds.add(user.getUserId()); // Lưu ID của người dùng
                        }
                        // Tạo một Adapter cho danh sách người dùng
                        CustomUserAdapter adapter = new CustomUserAdapter(AddTaskActivity.this, android.R.layout.simple_list_item_2, userList);


                        // Tạo AlertDialog
                        AlertDialog.Builder builder = new AlertDialog.Builder(AddTaskActivity.this);
                        builder.setTitle("Choose Employee");
                        builder.setSingleChoiceItems(adapter, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Lấy tên người dùng được chọn
                                String selectedUserName = userNames.get(which);
                                // Lấy ID của người dùng từ Map
                                int selectedUserId = userIds.get(which);
                                Log.d("myLog", "UserId list is empty" + selectedUserId);
                                // Đặt tên người dùng đã chọn vào EditText
                                edtEmployee.setText(selectedUserId + "");
                                dialog.dismiss();
                            }
                        });
                        builder.create().show();
                    } else {
                        Log.d("myLog", "User list is empty");
                    }
                } else {
                    Log.d("myLog", "Response is null or empty");
                }
            }

            @Override
            public void onFailure(Call<ResponseObject> call, Throwable throwable) {
                Log.e("myLog", "Error getting user list: " + throwable.getMessage());
            }
        });

    }
    private void showProjectSelection() {
        ApiService apiService = new ApiService();
        ProjectApi projectApi = apiService.getRetrofit().create(ProjectApi.class);
        projectApi.getAllProject().enqueue(new Callback<ResponseObject>() {
            @Override
            public void onResponse(Call<ResponseObject> call, Response<ResponseObject> response) {
                ResponseObject responseObject = response.body();

                if (responseObject != null && responseObject.getData() != null) {
                    ArrayList<Project> projects = new Gson().fromJson(new Gson().toJson(responseObject.getData()), new TypeToken<ArrayList<Project>>() {
                    }.getType());

                    if (projects != null && !projects.isEmpty()) {
                        ArrayList<String> projectNames = new ArrayList<>();
                        final ArrayList<Integer> projectIds = new ArrayList<>();

                        for (Project project : projects) {
                            projectNames.add(project.getProjectName());
                            projectIds.add(project.getProjectId());
                        }
                        // Tạo một Adapter cho danh sách người dùng
                        CustomProjectAdapter adapter = new CustomProjectAdapter(AddTaskActivity.this, android.R.layout.simple_list_item_2, projects);


                        // Tạo AlertDialog
                        AlertDialog.Builder builder = new AlertDialog.Builder(AddTaskActivity.this);
                        builder.setTitle("Choose Project");
                        builder.setSingleChoiceItems(adapter, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String selectedUserName = projectNames.get(which);
                                int selectedProjectId = projectIds.get(which);
                                Log.d("myLog", "UserId list is empty" + selectedProjectId);
                                edtProject.setText(selectedProjectId + "");
                                dialog.dismiss();
                            }
                        });
                        builder.create().show();
                    } else {
                        Log.d("myLog", "Project list is empty");
                    }
                } else {
                    Log.d("myLog", "Response is null or empty");
                }
            }

            @Override
            public void onFailure(Call<ResponseObject> call, Throwable throwable) {
                Log.e("myLog", "Error getting project list: " + throwable.getMessage());
            }
        });
    }
}

