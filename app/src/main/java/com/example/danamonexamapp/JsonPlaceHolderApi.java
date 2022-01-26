package com.example.danamonexamapp;

import com.example.danamonexamapp.models.Photos;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {

    @GET("photos")
    Call<List<Photos>> getListofPhotos();
}
