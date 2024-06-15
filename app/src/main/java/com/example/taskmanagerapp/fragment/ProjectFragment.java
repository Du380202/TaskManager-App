package com.example.taskmanagerapp.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskmanagerapp.R;
import com.example.taskmanagerapp.activity.admin.AddProjectActivity;
import com.example.taskmanagerapp.activity.admin.AddTaskActivity;
import com.example.taskmanagerapp.adapter.ProjectAdapter;
import com.example.taskmanagerapp.adapter.TaskAdapter;
import com.example.taskmanagerapp.api.ProjectApi;
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

public class ProjectFragment extends Fragment {

    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private ProjectAdapter projectAdapter;

    ArrayList<Project> projects;

    public ProjectFragment() {
        // Required empty public constructor
    }

    public ProjectFragment(ArrayList<Project> projects) {
        this.projects = projects;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project, container, false);
        fab = view.findViewById(R.id.addProject);
        recyclerView = view.findViewById(R.id.recyclerViewProject);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddProjectActivity.class);
                startActivity(intent);
            }
        });
        ApiService apiService = new ApiService();
        ProjectApi projectApi = apiService.getRetrofit().create(ProjectApi.class);
        projectApi.getAllProject().enqueue(new Callback<ResponseObject>() {
            @Override
            public void onResponse(Call<ResponseObject> call, Response<ResponseObject> response) {
                if (response.body() != null) {
                    ResponseObject responseObject = response.body();
                    Log.d("myLog", "Response message: " + response.message());

                    Gson gson = new Gson();
                    Type projectListType = new TypeToken<ArrayList<Project>>(){}.getType();
                    ArrayList<Project> projects = gson.fromJson(gson.toJson(responseObject.getData()), projectListType);
                    projectAdapter = new ProjectAdapter(getActivity(), projects);
                    recyclerView.setAdapter(projectAdapter);
                } else {
                    Log.d("myLog", "Response body is null");
                }
            }

            @Override
            public void onFailure(Call<ResponseObject> call, Throwable throwable) {
                Log.e("Object: ", throwable.getMessage());
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && fab.isShown()) {
                    fab.hide();
                } else if (dy < 0 && !fab.isShown()) {
                    fab.show();
                }
            }
        });
        return view;
    }
}