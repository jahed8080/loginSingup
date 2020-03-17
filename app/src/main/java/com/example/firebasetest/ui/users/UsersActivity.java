package com.example.firebasetest.ui.users;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.firebasetest.R;
import com.example.firebasetest.models.User;
import com.example.firebasetest.ui.addUser.AddUserActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class UsersActivity extends AppCompatActivity implements UsersContract.View, View.OnClickListener {

    private UsersPresenter mPresenter;

    private UserAdapter adapter;

    private FloatingActionButton btnAddUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        mPresenter = new UsersPresenter(this);
        adapter = new UserAdapter(this,this);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);

        btnAddUser = findViewById(R.id.fab_add);
        btnAddUser.setOnClickListener(this);

        mPresenter.getUsersFromFirebase();
    }

    @Override
    public void renderUsers(List<User> userList) {
        adapter.addUsers(userList);
    }

    @Override
    public void deleteUser(User user) {
        mPresenter.deleteUser(user);
    }

    @Override
    public void deleteFromAdapter(User user) {
        adapter.removeUser(user);
        Toast.makeText(this, "User Delete", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startAddUserActivity() {
        startActivity(new Intent(this, AddUserActivity.class));
    }


    @Override
    public void onClick(View v) {
        mPresenter.startAddUserActivity();
    }
}
