package com.stw300cem.finalandroid.api;

import com.stw300cem.finalandroid.models.Scrap;
import com.stw300cem.finalandroid.response.ScrapResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ScrapAPI {

    @Multipart
    @POST("upload")
    Call<String> uploadImage(@Part MultipartBody.Part img);

    @POST("api/addnewscrap")
    Call<Scrap> addNewScrap(@Body Scrap scrap);

//    @GET("api/scraps")
//    Call<List<Scrap>> viewScrap();

    @GET("api/newscraps")
    Call<ScrapResponse> viewScrap();

    @PUT("api/updatescrap/{id}")
    Call<String> updateScrap(@Path("id") String id, @Body Scrap scrap);

    @DELETE("api/deletescrap/{id}")
    Call<Void> deleteScrap(@Path("id") String id);
}
