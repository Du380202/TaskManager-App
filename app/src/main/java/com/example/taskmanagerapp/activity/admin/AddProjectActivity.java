package com.example.taskmanagerapp.activity.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.taskmanagerapp.R;
import com.example.taskmanagerapp.api.ProjectApi;
import com.example.taskmanagerapp.dto.Project;
import com.example.taskmanagerapp.model.ResponseObject;
import com.example.taskmanagerapp.retrofit.ApiService;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProjectActivity extends AppCompatActivity {
    private String selectedStatus;
    String[] statusArray = {"Open", "In Process", "Completed"};
    Button btnCancel, btnSave;
    EditText edtName, edtDescription, edtStartDate, edtEndDate;
    AutoCompleteTextView autoCompleteTextViewStatus;
    ArrayAdapter<String> adapterStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);
        setControl();
        adapterStatus = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, statusArray);
        autoCompleteTextViewStatus.setAdapter(adapterStatus);
        autoCompleteTextViewStatus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedStatus = parent.getItemAtPosition(position).toString();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendData();
                Toast.makeText(AddProjectActivity.this, "Successfull!!!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        ImageView backIcon = findViewById(R.id.back_iconProject);
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

        edtStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStartDatePickerDialog();
            }
        });

        edtEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEndDatePickerDialog();
            }
        });

    }

    private void setControl() {
        btnCancel = findViewById(R.id.btnCancelProject);
        btnSave = findViewById(R.id.btnSaveProject);
        edtName = findViewById(R.id.edtProjectName);
        edtDescription = findViewById(R.id.edtDescription);
        edtStartDate = findViewById(R.id.edtStartDateP);
        edtEndDate = findViewById(R.id.edtEndDateP);
        autoCompleteTextViewStatus = findViewById(R.id.autoCompleteTextViewStatus);
    }

    private void sendData() {
        String name = edtName.getText().toString();
        String moTa = edtDescription.getText().toString();
        String start = edtStartDate.getText().toString();
        String end = edtEndDate.getText().toString();
        Project project = new Project(name, moTa, start ,start, end, start, selectedStatus, 1);
        ApiService apiService = new ApiService();
        ProjectApi projectApi = apiService.getRetrofit().create(ProjectApi.class);
        projectApi.createNewProject(project).enqueue(new Callback<ResponseObject>() {
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
                        edtEndDate.setText(formattedDate);
                    }
                }, year, month, dayOfMonth);

        // Show DatePickerDialog
        datePickerDialog.show();
    }
}