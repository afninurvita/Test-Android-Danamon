package com.example.danamonexamapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "login.db";
    public static final int DATABASE_VERSION = 1;

    private Cursor cursor;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String table = "CREATE TABLE users(username TEXT PRIMARY KEY, email TEXT, password TEXT, role TEXT)";
        sqLiteDatabase.execSQL(table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists users");
    }

    public Boolean insertData(String username, String email, String password, String role) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("username", username);
        values.put("email", email);
        values.put("password", password);
        values.put("role", role);

        long result = db.insert("users", null, values);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getAllWhereUsername(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        cursor = db.rawQuery("select * from users where username=?", new String[]{username});
        return cursor;
    }

    public Boolean checkUsername(String username) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery("select * from users where username=?", new String[]{username});
        getAllWhereUsername(username);
        if (getAllWhereUsername(username).getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean checkUsernamePassword(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from users where username=? and password=?", new String[]{username, password});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public String getRole(String username) {
        String role = "";
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery("select * from users where username=?", new String[]{username});
//        cursor.moveToFirst();
//        if (cursor.getCount() > 0) {
//            cursor.moveToPosition(0);
//            String namaUser = cursor.getString(0);
//
//            if (namaUser != null) {
//                role = cursor.getString(3);
//            } else {
//                System.out.println("Empty result!");
//            }
//        }
        if (checkUsername(username)) {
            cursor.moveToPosition(0);
            String namaUser = cursor.getString(0);

            if (namaUser != null) {
                role = cursor.getString(3);
                System.out.println("ROLE RESULT: " + role);
            } else {
                System.out.println("Empty result!");
            }
        }
        return role;
    }
}