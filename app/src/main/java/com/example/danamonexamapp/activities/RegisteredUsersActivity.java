package com.example.danamonexamapp.activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.danamonexamapp.DatabaseHelper;
import com.example.danamonexamapp.adapters.RegistedUsersAdapter;
import com.example.danamonexamapp.databinding.ActivityRegisteredUsersBinding;
import com.example.danamonexamapp.models.Users;

import java.util.ArrayList;
import java.util.List;

public class RegisteredUsersActivity extends AppCompatActivity {

    private ActivityRegisteredUsersBinding binding;
    private DatabaseHelper databaseHelper;
    private Cursor cursor;
    private List<Users> usersList = new ArrayList<>();
    private RegistedUsersAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    public static RegisteredUsersActivity registeredUsersActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisteredUsersBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        registeredUsersActivity = this;

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.rvRegisteredUsers.setLayoutManager(linearLayoutManager);

        databaseHelper = new DatabaseHelper(this);

        readData();
    }

    private void readData() {
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        String[] field = {"username", "email", "password", "role"};
        cursor = database.query("users", field, null, null, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                String username = cursor.getString(0);
                String email = cursor.getString(1);
                String role = cursor.getString(3);
                Users users = new Users();
                users.setUsername(username);
                users.setEmail(email);
                users.setRole(role);
                usersList.add(users);
            }
            display();
        } else {
            System.out.println("Query returns empty result. >>>");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshList();
    }

    private void display() {
        adapter = new RegistedUsersAdapter(this, usersList);
        binding.rvRegisteredUsers.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void refreshList() {
        usersList = new ArrayList<>();
        if (usersList.size() > 0) {
            adapter.notifyDataSetChanged();
        }
        readData();
    }
}