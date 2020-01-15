package com.stw300cem.finalandroid.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.stw300cem.finalandroid.R;
import com.stw300cem.finalandroid.bll.AuthBLL;
import com.stw300cem.finalandroid.helper.StrictModeClass;
import com.stw300cem.finalandroid.helper.UserSession;
import com.stw300cem.finalandroid.models.User;
import com.stw300cem.finalandroid.response.AuthResponse;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    EditText etEmail, etPassword;
    TextView tvSignup;
    String email, password;
    AuthBLL authBLL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.inputEmail);
        etPassword = findViewById(R.id.inputPassword);
        btnLogin = findViewById(R.id.buttonLogin);
        tvSignup = findViewById(R.id.link_signup);
        final UserSession userSession = new UserSession(this);

        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(i);
                finish();

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = etEmail.getText().toString();
                password = etPassword.getText().toString();
                authBLL = new AuthBLL();


                if (validate()) {
                    final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, R.style.AppTheme_Dark_Dialog);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Authenticating...");
                    progressDialog.show();

                    User user = new User(email, password);

                    StrictModeClass.StrictMode();
                    AuthResponse authResponse = authBLL.loginUser(user);

                    if (authResponse == null) {
                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        progressDialog.dismiss();
                                        Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                                    }
                                }, 1000);
                    }
                    else if (authResponse.getSuccess().equals("true")) {

                        User loggedinUser = authResponse.getUser();
                        userSession.startSession(loggedinUser, authResponse.getToken());
                        Toast.makeText(LoginActivity.this, "login successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                        startActivity(intent);
                        finish();


                    }
                }
            }
        });
    }

    public boolean validate() {

        if (TextUtils.isEmpty(etEmail.getText().toString())) {
            etEmail.setError("Enter your email");
            etEmail.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(etPassword.getText().toString())) {
            etPassword.setError("Enter your password");
            etPassword.requestFocus();
            return false;
        }

        return true;
    }
}
