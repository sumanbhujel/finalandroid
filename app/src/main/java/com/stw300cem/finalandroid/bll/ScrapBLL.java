package com.stw300cem.finalandroid.bll;

import com.stw300cem.finalandroid.helper.Url;
import com.stw300cem.finalandroid.models.Scrap;
import com.stw300cem.finalandroid.response.ScrapResponse;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class ScrapBLL {
    private ScrapAPI scrapAPI;

    public ScrapBLL() {
        scrapAPI = Url.getInstance().create(ScrapAPI.class);
    }

    public Scrap addScrap(Scrap scrap) {
        Scrap newScrap = null;
        Call<Scrap> scrapCall = scrapAPI.addNewScrap(scrap);
        try {
            Response<Scrap> scrapResponse = scrapCall.execute();
            if (!scrapResponse.isSuccessful()) {
                return newScrap;
            }
            if (scrapResponse.body() != null) {
                newScrap = scrapResponse.body();
            }

        } catch (IOException e) {
            e.printStackTrace();

        }
        return newScrap;
    }

    public List<Scrap> viewScrap() {
        List<Scrap> scrapList = null;
        Call<ScrapResponse> scrapListCall = scrapAPI.viewScrap();

        try {
            Response<ScrapResponse> response = scrapListCall.execute();
            if (!response.isSuccessful()) {
                return scrapList;
            }
            if (response.body() != null) {
                scrapList = response.body().getScrap();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return scrapList;
    }

    public boolean updateScrap(String id, Scrap scrap) {
        boolean isScrapUpdated = false;
        Call<String> scrapUpdate = scrapAPI.updateScrap(id, scrap);
        try {
            Response<String> response = scrapUpdate.execute();
            if (!response.isSuccessful()) {
                return isScrapUpdated;
            }
            isScrapUpdated = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isScrapUpdated;
    }

}