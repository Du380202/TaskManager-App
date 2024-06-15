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
import com.example.taskmanagerapp.activity.admin.TaskDetailActivity;
import com.example.taskmanagerapp.activity.admin.UserDetailActivity;
import com.example.taskmanagerapp.dto.Project;
import com.example.taskmanagerapp.dto.TaskDetail;
import com.example.taskmanagerapp.dto.User;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{
    private ArrayList<User> userList;

    private Context context;

    public UserAdapter(ArrayList<User> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvFullName, tvEmail;
        CardView cardItem;

        public ViewHolder(View itemView) {
            super(itemView);
            cardItem = itemView.findViewById(R.id.cardViewItemUser);
            tvFullName = itemView.findViewById(R.id.tvFullName);
            tvEmail = itemView.findViewById(R.id.tvEmail);
        }
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_user_item, parent, false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {
        User user = userList.get(position);
        if (user == null) {
            Log.e("ProjectAdapter", "Project at position " + position + " is null");
            return;
        }

        // Set data to views
        if (user.getFullName() != null) {
            holder.tvFullName.setText(user.getFullName());
        } else {
            Log.e("ProjectAdapter", "ProjectName is null at position " + position);
            holder.tvFullName.setText(""); // hoặc giá trị mặc định
        }

        if (user.getEmail() != null) {
            holder.tvEmail.setText(user.getEmail());
        } else {
            Log.e("TaskAdapter", "AssigneeId is null at position " + position);
            holder.tvEmail.setText("");
        }

        holder.cardItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclickDetail(user);
            }


        });
    }

    private void onclickDetail(User user) {
        Intent detail = new Intent(context, UserDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Object:" , user);
        detail.putExtras(bundle);
        context.startActivity(detail);
    }
    @Override
    public int getItemCount() {
        return userList.size();
    }
}
