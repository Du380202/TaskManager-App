package com.example.taskmanagerapp.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskmanagerapp.R;
import com.example.taskmanagerapp.activity.admin.AddTaskActivity;
import com.example.taskmanagerapp.activity.login.UserInfo;
import com.example.taskmanagerapp.adapter.TaskAdapter;
import com.example.taskmanagerapp.dto.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    CardView cardProject, cardTask, cardEmployee, cardChart;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        cardProject = view.findViewById(R.id.projectCard);
        cardTask = view.findViewById(R.id.taskCard);
        cardEmployee = view.findViewById(R.id.employeeCard);
        cardChart = view.findViewById(R.id.chartCard);
        cardProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProjectFragment()).commit();
            }
        });
        if (UserInfo.user.getRole().equals("user")) {
            cardProject.setVisibility(View.GONE);
            cardEmployee.setVisibility(View.GONE);
            cardChart.setVisibility(View.GONE);
            cardTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TaskUserFragment()).commit();
                }
            });
        }
        else {
            cardTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TaskFragment()).commit();
                }
            });
        }


        cardEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new EmployeeFragment()).commit();
            }
        });

        return view;
    }

}