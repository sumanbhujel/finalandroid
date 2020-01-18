package com.stw300cem.finalandroid.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.stw300cem.finalandroid.R;
import com.stw300cem.finalandroid.adapter.ScrapAdapter;
import com.stw300cem.finalandroid.bll.ScrapBLL;
import com.stw300cem.finalandroid.models.Scrap;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private List<Scrap> scrapList;
    Button btnAdd;
    private ScrapBLL scrapBLL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        RecyclerView recyclerView = findViewById(R.id.listScrap);
        btnAdd = findViewById(R.id.buttonAdd);
        scrapBLL = new ScrapBLL();
        scrapList = new ArrayList<>();
        getAllScraps();

        ScrapAdapter scrapAdapter = new ScrapAdapter(this, scrapList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(scrapAdapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, AddScrapActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getAllScraps() {
//        scrapList = scrapBLL.viewScrap();
        scrapList.addAll(scrapBLL.viewScrap());
    }
}
