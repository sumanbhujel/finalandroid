package com.stw300cem.finalandroid.response;


import com.stw300cem.finalandroid.models.User;

public class AuthResponse {
    private String token;

    private String success, error;
    private User user;

    public String getToken() {
        return token;
    }

    public String getSuccess() {
        return success;
    }

    public String getError() {
        return error;
    }

    public User getUser() {
        return user;
    }
}
