package com.example.danamonexamapp.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.danamonexamapp.JsonPlaceHolderApi;
import com.example.danamonexamapp.adapters.PhotosDetailAdapter;
import com.example.danamonexamapp.constants.AppConstant;
import com.example.danamonexamapp.databinding.ActivityMainBinding;
import com.example.danamonexamapp.models.Photos;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private LinearLayoutManager layoutManager;
    private PhotosDetailAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.rvPhotos.setLayoutManager(layoutManager);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<Photos>> call = jsonPlaceHolderApi.getListofPhotos();
        call.enqueue(new Callback<List<Photos>>() {
            @Override
            public void onResponse(Call<List<Photos>> call, Response<List<Photos>> response) {
                if (response.isSuccessful()) {
                    List<Photos> photosList = response.body();
                    adapter = new PhotosDetailAdapter(getApplicationContext(), photosList);
                    binding.rvPhotos.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Photos>> call, Throwable t) {
                Log.e("Error: ", t.getMessage());
            }
        });
    }
}