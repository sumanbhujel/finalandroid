package com.stw300cem.finalandroid.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.stw300cem.finalandroid.R;
import com.stw300cem.finalandroid.bll.AuthBLL;
import com.stw300cem.finalandroid.helper.StrictModeClass;
import com.stw300cem.finalandroid.models.User;

public class SignUpActivity extends AppCompatActivity {

    EditText etFullname, etAddress, etMobileNumber, etEmail, etPassword;
    TextView textView;
    Spinner spinner;
    RadioGroup radioGroupGender;
    Button btnSignup;
    String fullname, address, gender, email, password, userType, mobilenumber;
    String[] usertype = {"-- Choose --", "Normal", "Collector"};
    AuthBLL authBLL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        authBLL = new AuthBLL();
        etFullname = findViewById(R.id.input_fullname);
        etAddress = findViewById(R.id.input_address);
        etEmail = findViewById(R.id.input_email);
        etMobileNumber = findViewById(R.id.input_mobilenumber);
        etPassword = findViewById(R.id.input_password);
        btnSignup = findViewById(R.id.buttonSignup);
        radioGroupGender = findViewById(R.id.rgGender);
        spinner = findViewById(R.id.spUsertype);
        textView = findViewById(R.id.link_login);

        ArrayAdapter adapter = new ArrayAdapter(this,
                R.layout.spinner_values, usertype);
        spinner.setAdapter(adapter);
        setSpinnerValue();

        radioGroupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbMale) {
                    gender = "Male";
                }
                if (checkedId == R.id.rbFemale) {
                    gender = "Female";
                }
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    public boolean validate() {
        if (TextUtils.isEmpty(etFullname.getText().toString())) {
            etFullname.setError("Enter your full name");
            etFullname.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(etAddress.getText().toString())) {
            etAddress.setError("Enter your address");
            etAddress.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(etMobileNumber.getText().toString())) {
            etMobileNumber.setError("Enter your address");
            etMobileNumber.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(etEmail.getText().toString())) {
            etEmail.setError("Enter your email or phone number");
            etEmail.requestFocus();
            return false;
        }

//        if (password.length() < 4 || password.length() > 6) {
//            etPassword.setError("between 4 and 10 alphanumeric characters");
//            etPassword.requestFocus();
//            return false;
//        }

        if (TextUtils.isEmpty(etPassword.getText().toString())) {
            etPassword.setError("Enter your password");
            etPassword.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(gender)) {
            Toast.makeText(this, " Select your Gender ", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(userType)) {
            Toast.makeText(this, " Select UserType ", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void setSpinnerValue() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                userType = adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(SignUpActivity.this, "Please select usertype",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void register() {

        fullname = etFullname.getText().toString();
        address = etAddress.getText().toString();
        email = etEmail.getText().toString();
        password = etPassword.getText().toString();
        mobilenumber = etMobileNumber.getText().toString();

        if (validate()) {

            StrictModeClass.StrictMode();
            User user = new User(fullname, address, email, mobilenumber, password, gender, userType);
            User userResponse = authBLL.registerUser(user);

            if (userResponse == null) {
                Toast.makeText(this, "Fail in registration.", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                Toast.makeText(this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                startActivity(intent);

            }

        }
    }
}
