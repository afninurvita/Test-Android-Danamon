package com.example.danamonexamapp.activities;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.danamonexamapp.DatabaseHelper;
import com.example.danamonexamapp.databinding.ActivityEditDataUsersBinding;

public class EditDataUsersActivity extends AppCompatActivity {

    private ActivityEditDataUsersBinding binding;
    private Context context = this;
    private DatabaseHelper databaseHelper;
    private Cursor cursor;
    private String mUsername = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditDataUsersBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        databaseHelper = new DatabaseHelper(this);

        binding.buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validasiInput();
            }
        });

        mUsername = getIntent().getStringExtra("usr");
        System.out.println("USERNAME: " + mUsername);
        loadDataFromDb(mUsername);
    }

    private void validasiInput() {
        if (binding.usernameUpdate.getText().toString().trim().length() == 0) {
            Toast.makeText(context, "Username belum diisi", Toast.LENGTH_SHORT).show();
        } else if (binding.emailUpdate.getText().toString().trim().length() == 0) {
            Toast.makeText(context, "Email belum diisi", Toast.LENGTH_SHORT).show();
        } else if (binding.roleUpdate.getText().toString().trim().length() == 0) {
            Toast.makeText(context, "Role belum diisi", Toast.LENGTH_SHORT).show();
        } else {
            updateToDatabase();
        }
    }

    private void loadDataFromDb(String userName) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        cursor = db.rawQuery("select * from users where username=?", new String[]{userName});
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            cursor.moveToPosition(0);
            String nama = cursor.getString(0);
            if (nama != null) {
                binding.usernameUpdate.setText(nama);
            }

            String email = cursor.getString(1);
            if (email != null) {
                binding.emailUpdate.setText(email);
            }

            String role = cursor.getString(3);
            if (role != null) {
                binding.roleUpdate.setText(role);
            }
        } else {
            System.out.println("Empty result!");
        }
    }

    private void updateToDatabase() {
//        String queryUpdate = "UPDATE users SET " +
//                "username='" +
//                binding.usernameUpdate.getText().toString().trim() +
//                "'" +
//                ", " +
//                "email='" +
//                binding.emailUpdate.getText().toString().trim() +
//                "', " +
//                "role='" +
//                binding.roleUpdate.getText().toString().trim() +
//                "', " +
//                "WHERE username='" +
//                mUsername +
//                "'";

//        SQLiteDatabase db = databaseHelper.getWritableDatabase();
//        db.execSQL(queryUpdate);
//        Toast.makeText(context, "Update data sukses!", Toast.LENGTH_SHORT).show();
//        finish();
        Toast.makeText(context, "Fitur masih dalam tahap pengembangan!", Toast.LENGTH_SHORT).show();
    }
}