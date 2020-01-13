package com.stw300cem.finalandroid.api;

import com.stw300cem.finalandroid.models.User;
import com.stw300cem.finalandroid.response.AuthResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthAPI {
    @POST("api/login")
    Call<AuthResponse> userLogin(@Body User user);

    @POST("api/signup")
    Call<User> registerUser(@Body User user);
}
