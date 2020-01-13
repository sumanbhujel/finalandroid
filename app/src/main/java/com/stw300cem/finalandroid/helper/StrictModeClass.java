package com.stw300cem.finalandroid.helper;

public class StrictModeClass {
    public static void StrictMode() {
        android.os.StrictMode.ThreadPolicy policy =
                new android.os.StrictMode.ThreadPolicy
                        .Builder()
                        .permitAll().build();

        android.os.StrictMode.setThreadPolicy(policy);
    }

}
