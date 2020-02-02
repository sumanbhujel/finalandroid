package com.stw300cem.finalandroid.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.stw300cem.finalandroid.R;
import com.stw300cem.finalandroid.bll.ScrapBLL;
import com.stw300cem.finalandroid.helper.Url;
import com.stw300cem.finalandroid.models.Scrap;

public class EditDetailActivity extends AppCompatActivity {

    EditText etProductType, etDescription, etLocation;
    Button btnUpdate;
    private ScrapBLL scrapBLL;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_detail);

        scrapBLL = new ScrapBLL();

        etProductType = findViewById(R.id.editProductType);
        etDescription = findViewById(R.id.editDescription);
        etLocation = findViewById(R.id.editLocation);
        btnUpdate = findViewById(R.id.buttonUpdate);
        imageView = findViewById(R.id.changeImage);

        Intent intent = getIntent();
        final String id = intent.getStringExtra("id");
        String pt = intent.getStringExtra("producttype");
        String d = intent.getStringExtra("description");
        String l = intent.getStringExtra("location");
        final String i = intent.getStringExtra("image");

        Picasso.get().load(Url.IMAGE_URL + i).into(imageView);
        etProductType.setText(pt);
        etDescription.setText(d);
        etLocation.setText(l);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pt1 = etProductType.getText().toString();
                String d1 = etDescription.getText().toString();
                String l1 = etLocation.getText().toString();

                Scrap scrap = new Scrap(i, pt1, d1, l1);
                updateScrap(id, scrap);
                Intent i = new Intent(EditDetailActivity.this, DashboardActivity.class);
                startActivity(i);

            }
        });


    }

    private void updateScrap(String id, Scrap scrap) {

        boolean scrapUpdated = scrapBLL.updateScrap(id, scrap);
        if (scrapUpdated) {
            Toast.makeText(this, "Scrap details successfully updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error updating scrap details", Toast.LENGTH_SHORT).show();
        }

    }
}
