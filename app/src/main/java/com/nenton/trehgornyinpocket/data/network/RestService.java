package com.nenton.trehgornyinpocket.data.network;

import com.nenton.trehgornyinpocket.data.network.res.ResponseWeather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestService {

    @GET("forecast/daily")
    Call<ResponseWeather> getWeather(@Query("id") int id, @Query("APPID") String apiKey, @Query("cnt") int cnt);
}
