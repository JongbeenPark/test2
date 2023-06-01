package com.example.test2;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface HA_API {
    @POST("services/light/turn_{action}")
    Call<Void> toggleLight(@Header("Authorization") String Token,
                           @Path("action") String action,
                           @Body LightRequest request);
}
