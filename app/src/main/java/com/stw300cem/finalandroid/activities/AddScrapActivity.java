package com.stw300cem.finalandroid.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.stw300cem.finalandroid.R;
import com.stw300cem.finalandroid.api.ScrapAPI;
import com.stw300cem.finalandroid.bll.ScrapBLL;
import com.stw300cem.finalandroid.helper.StrictModeClass;
import com.stw300cem.finalandroid.helper.Url;
import com.stw300cem.finalandroid.helper.UserSession;
import com.stw300cem.finalandroid.models.Scrap;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddScrapActivity extends AppCompatActivity {

    private ImageView btnImage;
    EditText etProductType, etDescription, etLocation;
    private String productType, description, location, image;
    private Uri uri;
    private MultipartBody.Part mbImage;
    private ScrapBLL scrapBLL;
    private UserSession userSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_scrap);

        userSession = new UserSession(this);
        scrapBLL = new ScrapBLL();

        etProductType = findViewById(R.id.textProductType);
        etDescription = findViewById(R.id.textDescription);
        etLocation = findViewById(R.id.textLocation);
        Button btnAdd = findViewById(R.id.buttonAdd);
        btnImage = findViewById(R.id.selectImage);

        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.
                                Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                productType = etProductType.getText().toString();
//                description = etDescription.getText().toString();
//                location = etLocation.getText().toString();
//                Scrap newScrap = new Scrap(image, productType, description, location);
//                addScrap(newScrap);
                add();
                Intent i = new Intent(AddScrapActivity.this, DashboardActivity.class);
                startActivity(i);
                finish();
            }
        });


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 2) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getImgReady();
            } else {
                Toast.makeText(AddScrapActivity.this, "No Permission", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            uri = data.getData();
            btnImage.setImageURI(uri);
            askPermission();
        }
    }


    private void askPermission() {
        if (ContextCompat.checkSelfPermission(AddScrapActivity.this, Manifest.permission.
                WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AddScrapActivity.this, new String[]{Manifest.permission
                            .WRITE_EXTERNAL_STORAGE},
                    2);
        } else {
            getImgReady();
            uploadImage(mbImage);
        }
    }

    private void getImgReady() {
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = AddScrapActivity.this.getContentResolver().query(uri, filePathColumn,
                null, null, null);
        assert cursor != null;
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String imgPath = cursor.getString(columnIndex);
        File file = new File(imgPath);
        RequestBody requestBody =
                RequestBody.create(MediaType.parse("image/*"), file);
        mbImage = MultipartBody.Part.createFormData("image",
                file.getName(), requestBody);
    }

    private void uploadImage(MultipartBody.Part img) {

        ScrapAPI scrapAPI = Url.getInstance().create(ScrapAPI.class);
        Call<String> imgUpload = scrapAPI.uploadImage(img);

        imgUpload.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(AddScrapActivity.this, response.body() + "Uploaded",
                        Toast.LENGTH_SHORT).show();
                image = response.body();
//                Toast.makeText(getActivity(), "image name " + image, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("Error:", t.getMessage());
            }
        });
    }

    private void add() {
        String userId = userSession.getUser().get_id();
        productType = etProductType.getText().toString();
        description = etDescription.getText().toString();
        location = etLocation.getText().toString();

        Scrap scrap = new Scrap(image, productType, description, location, userId);

        StrictModeClass.StrictMode();
        Scrap scrapRes = scrapBLL.addScrap(scrap);
        if (scrapRes == null) {
            Toast.makeText(this, "Error adding scrap", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Scrap added sucessfully", Toast.LENGTH_SHORT).show();
        }
    }
}
