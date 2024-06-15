package com.example.taskmanagerapp.activity.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.taskmanagerapp.R;
import com.example.taskmanagerapp.dto.Project;
import com.example.taskmanagerapp.dto.TaskDetail;

public class ProjectDetailActivity extends AppCompatActivity {
    Button btnCancel, btnSave;
    EditText edtName, edtDescription, edtStartDate, edtEndDate;
    AutoCompleteTextView autoCompleteTextViewStatus;
    ArrayAdapter<String> adapterStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_detail);
        setControl();
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return;
        Project projectDetail;
        projectDetail = (Project) bundle.getSerializable("Object:", Project.class);
        edtName.setText(projectDetail.getProjectName());
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
}