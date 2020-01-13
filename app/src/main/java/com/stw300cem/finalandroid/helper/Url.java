package com.stw300cem.finalandroid.helper;

public class Url {
    public static final String BASE_URL = "http://10.0.2.2:9000/";
    public static final String IMAGE_URL = "http://10.0.2.2:9000/images/";

    public static Retrofit getInstance() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit;
    }
}
