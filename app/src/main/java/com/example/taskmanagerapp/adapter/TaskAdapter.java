package com.example.taskmanagerapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.taskmanagerapp.R;
import com.example.taskmanagerapp.activity.admin.TaskDetailActivity;
import com.example.taskmanagerapp.dto.Task;
import com.example.taskmanagerapp.dto.TaskDetail;
import com.example.taskmanagerapp.fragment.TaskFragment;

import java.util.ArrayList;
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private ArrayList<TaskDetail> taskList;
    private Context context;


    // Constructor
    public TaskAdapter(Context context, ArrayList<TaskDetail> taskList) {
        this.context = context;
        this.taskList = taskList;
    }

    // ViewHolder class
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView taskName, assignName, status;
        CardView cardItem;

        public ViewHolder(View itemView) {
            super(itemView);
            cardItem = itemView.findViewById(R.id.cardViewItem);
            taskName = itemView.findViewById(R.id.taskName);
            assignName = itemView.findViewById(R.id.asignName);
            status = itemView.findViewById(R.id.status);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TaskDetail task = taskList.get(position);
        if (task == null) {
            Log.e("TaskAdapter", "Task at position " + position + " is null");
            return;
        }

        // Set data to views
        if (task.getTaskName() != null) {
            holder.taskName.setText(task.getTaskName());
        } else {
            Log.e("TaskAdapter", "TaskName is null at position " + position);
            holder.taskName.setText(""); // hoặc giá trị mặc định
        }

        if (task.getFull_name() != null) {
            holder.assignName.setText(task.getFull_name());
        } else {
            Log.e("TaskAdapter", "AssigneeId is null at position " + position);
            holder.assignName.setText(""); // hoặc giá trị mặc định
        }

        if (task.getStatus() != null) {
            holder.status.setText(task.getStatus());
        } else {
            Log.e("TaskAdapter", "Status is null at position " + position);
            holder.status.setText(""); // hoặc giá trị mặc định
        }
        holder.cardItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclickDetail(task);
            }


        });
    }
    private void onclickDetail(TaskDetail task) {
        Intent detail = new Intent(context, TaskDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Object:" , task);
        detail.putExtras(bundle);
        context.startActivity(detail);
    }


    @Override
    public int getItemCount() {
        return taskList.size();
    }
}
