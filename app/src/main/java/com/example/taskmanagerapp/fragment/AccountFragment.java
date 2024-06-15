package com.example.taskmanagerapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.taskmanagerapp.R;
import com.example.taskmanagerapp.activity.login.UserInfo;
import com.example.taskmanagerapp.dto.User;

public class AccountFragment extends Fragment {
    TextView tvName, tvUsername, profileName, profileEmail, profileUsername, profilePassword;
    User user = UserInfo.user;
    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        tvName = view.findViewById(R.id.titleName);
        tvUsername = view.findViewById(R.id.titleUsername);
        profileName = view.findViewById(R.id.profileName);
        profileEmail = view.findViewById(R.id.profileEmail);
        profileUsername = view.findViewById(R.id.profileUsername);
        profilePassword = view.findViewById(R.id.profilePassword);

        tvName.setText(user.getFullName());
        tvUsername.setText(user.getUsername());
        profileName.setText(user.getFullName());
        profileUsername.setText(user.getUsername());
        profileEmail.setText(user.getEmail());
        profilePassword.setText("*******");


        return view;
    }

}