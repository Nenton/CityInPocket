package com.nenton.trehgornyinpocket.data.network.intentservices;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

public class WeatherIntentService extends IntentService {
    public WeatherIntentService() {
        super("WeatherIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }
}
