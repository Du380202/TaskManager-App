package com.example.taskmanagerapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.taskmanagerapp.dto.Project;
import com.example.taskmanagerapp.dto.User;

import java.util.List;

public class CustomProjectAdapter extends ArrayAdapter<Project> {

    private List<Project> projects;

    public CustomProjectAdapter(@NonNull Context context, int resource, @NonNull List<Project> projects) {
        super(context, resource, projects);
        this.projects = projects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(android.R.layout.simple_list_item_2, parent, false);
        }

        Project project = projects.get(position);
        if (project != null) {
            TextView textView1 = view.findViewById(android.R.id.text1);
            TextView textView2 = view.findViewById(android.R.id.text2);
            if (textView1 != null && textView2 != null) {
                textView1.setText("ID: " + project.getProjectId());
                textView2.setText("Name: " + project.getProjectName());
            }
        }

        return view;
    }
}
