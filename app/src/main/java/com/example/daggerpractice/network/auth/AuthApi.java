package com.example.daggerpractice.network.auth;

import com.example.daggerpractice.models.User;
import com.google.gson.annotations.Expose;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AuthApi {
    @GET("users/{id}")
    Flowable<User> getUserById(
            @Path("id") int id
    );
}
