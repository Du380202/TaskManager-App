package com.example.taskmanagerapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagerapp.R;
import com.example.taskmanagerapp.activity.admin.ProjectDetailActivity;
import com.example.taskmanagerapp.activity.admin.TaskDetailActivity;
import com.example.taskmanagerapp.activity.admin.UpdateProject;
import com.example.taskmanagerapp.dto.Project;
import com.example.taskmanagerapp.dto.TaskDetail;

import java.util.ArrayList;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ViewHolder> {

    private ArrayList<Project> projectList;
    private Context context;


    // Constructor
    public ProjectAdapter(Context context, ArrayList<Project> projectList) {
            this.context = context;
            this.projectList = projectList;
    }

    // ViewHolder class
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView projectName, status;
        ProgressBar progress;
        CardView cardItem;

        public ViewHolder(View itemView) {
            super(itemView);
            cardItem = itemView.findViewById(R.id.cardViewProject);
            projectName = itemView.findViewById(R.id.projectName);
            progress = itemView.findViewById(R.id.progressBarProject);
            status = itemView.findViewById(R.id.statusProject);
        }
    }

    @NonNull
    @Override
    public ProjectAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_project, parent, false);
        return new ProjectAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Project project = projectList.get(position);
        if (project == null) {
            Log.e("ProjectAdapter", "Project at position " + position + " is null");
            return;
        }

        // Set data to views
        if (project.getProjectName() != null) {
            holder.projectName.setText(project.getProjectName());
        } else {
            Log.e("ProjectAdapter", "ProjectName is null at position " + position);
            holder.projectName.setText(""); // hoặc giá trị mặc định
        }

        if (project.getTienDo() != null) {
            holder.progress.setProgress(project.getTienDo());
        } else {
            Log.e("TaskAdapter", "AssigneeId is null at position " + position);
            holder.progress.setProgress(0);
        }

        if (project.getStatus() != null) {
            holder.status.setText(project.getStatus());
        } else {
            Log.e("TaskAdapter", "Status is null at position " + position);
            holder.status.setText(""); // hoặc giá trị mặc định
        }
        holder.cardItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclickDetail(project);
            }


        });
    }
    private void onclickDetail(Project project) {
        Intent detail = new Intent(context, UpdateProject.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Object:" , project);
        detail.putExtras(bundle);
        context.startActivity(detail);
    }


    @Override
    public int getItemCount() {
        return projectList.size();
    }
}
