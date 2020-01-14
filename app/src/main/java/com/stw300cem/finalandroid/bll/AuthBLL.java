package com.stw300cem.finalandroid.bll;

import com.stw300cem.finalandroid.api.AuthAPI;
import com.stw300cem.finalandroid.helper.Url;
import com.stw300cem.finalandroid.models.User;
import com.stw300cem.finalandroid.response.AuthResponse;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class AuthBLL {

    private AuthAPI authAPI = Url.getInstance().create(AuthAPI.class);

    public AuthResponse loginUser(User user) {
        AuthResponse authResponse = null;

        Call<AuthResponse> loginCall = authAPI.userLogin(user);

        try {
            Response<AuthResponse> loginResponse = loginCall.execute();
            if (!loginResponse.isSuccessful()) {
                return authResponse;
            } else {
                authResponse = loginResponse.body();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return authResponse;

    }

    public User registerUser(User user) {
        User newUser = null;
        Call<User> userCall = authAPI.registerUser(user);
        try {
            Response<User> userRegisterResponse = userCall.execute();
            if (!userRegisterResponse.isSuccessful()) {
                return newUser;
            }
            if (userRegisterResponse.body() != null) {
                newUser = userRegisterResponse.body();
            }

        } catch (IOException e) {
            e.printStackTrace();

        }
        return newUser;
    }

}