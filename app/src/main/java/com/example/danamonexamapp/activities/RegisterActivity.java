package com.example.danamonexamapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.danamonexamapp.DatabaseHelper;
import com.example.danamonexamapp.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        dbHelper = new DatabaseHelper(this);

        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = binding.etUsername.getText().toString();
                String email = binding.etEmail.getText().toString();
                String pass = binding.etPassword.getText().toString();
                String role = binding.etRole.getText().toString();

                if (TextUtils.isEmpty(user) || TextUtils.isEmpty(email) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(role)) {
                    Toast.makeText(RegisterActivity.this, "All fields are required.", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean checkUser = dbHelper.checkUsername(user);
                    if (!checkUser) {
                        Boolean insert = dbHelper.insertData(user, email, pass, role);
                        if (insert) {
                            Toast.makeText(RegisterActivity.this, "Your account is successfully registered.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(RegisterActivity.this, "Registration failed.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(RegisterActivity.this, "User already exists.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        binding.tvToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}