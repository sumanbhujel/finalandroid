package com.stw300cem.finalandroid.models;

public class User {

    private String _id;
    private String fullName;
    private String address;
    private String email;
    private String mobileNumber;
    private String password;
    private String gender;
    private String userType;


    public User(String fullName, String address, String email, String mobileNumber, String password, String gender, String userType) {
        this.fullName = fullName;
        this.address = address;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.password = password;
        this.gender = gender;
        this.userType = userType;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String get_id() {
        return _id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getGender() {
        return gender;
    }

    public String getUserType() {
        return userType;
    }
}
