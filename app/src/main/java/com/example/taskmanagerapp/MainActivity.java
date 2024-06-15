package com.example.taskmanagerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taskmanagerapp.activity.login.LoginActivity;
import com.example.taskmanagerapp.activity.login.UserInfo;
import com.example.taskmanagerapp.dto.User;
import com.example.taskmanagerapp.fragment.AccountFragment;
import com.example.taskmanagerapp.fragment.EmployeeFragment;
import com.example.taskmanagerapp.fragment.HomeFragment;
import com.example.taskmanagerapp.fragment.ProjectFragment;
import com.example.taskmanagerapp.fragment.TaskFragment;
import com.example.taskmanagerapp.fragment.TaskUserFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    User userInfo;

    TextView tvUser, tvEmail;

    MenuItem navTaskEmployee, navTask, navEmployee, navProject, navHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setControl();
        View headerView = navigationView.getHeaderView(0);
        // Lấy TextView từ HeaderView
        tvUser = headerView.findViewById(R.id.tvUser);
        tvEmail = headerView.findViewById(R.id.tvEmail);
//        Bundle bundle = getIntent().getExtras();
//        if (bundle != null) {
            User userInfo = UserInfo.user;
            if (userInfo != null) {
                if (userInfo.getRole().equals("user")) {
                    navEmployee.setVisible(false);
                    navTask.setVisible(false);
                    navTaskEmployee.setVisible(true);
                    navProject.setVisible(false);

                }
                tvUser.setText("Welcome, " + userInfo.getFullName());
                tvEmail.setText(userInfo.getEmail());
                Toast.makeText(MainActivity.this, "Login Successfull" + userInfo.toString(), Toast.LENGTH_SHORT).show();
            }
//        }


        Toolbar toolbar = findViewById(R.id.toolbar); //Ignore red line errors
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav,
                R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }

    private void setControl() {
        navigationView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.drawer_layout);
        Menu menu = navigationView.getMenu();
        navTaskEmployee = menu.findItem(R.id.nav_taskEmployee);
        navTask = menu.findItem(R.id.nav_task);
        navEmployee = menu.findItem(R.id.nav_employee);
        navProject = menu.findItem(R.id.nav_project);
        navHome = menu.findItem(R.id.nav_home);
//        tvEmail = findViewById(R.id.tvEmail);
//        tvUser = findViewById(R.id.tvUser);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.nav_home) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        } else if (itemId == R.id.nav_project) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProjectFragment()).commit();
        } else if (itemId == R.id.nav_task) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TaskFragment()).commit();
        }else if (itemId == R.id.nav_taskEmployee) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TaskUserFragment()).commit();
        }
        else if (itemId == R.id.nav_employee) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new EmployeeFragment()).commit();
        } else if (itemId == R.id.nav_profile) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AccountFragment()).commit();
        }
        else if (itemId == R.id.nav_logout) {
            Intent detail = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(detail);
            Toast.makeText(this, "Logout!", Toast.LENGTH_SHORT).show();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}