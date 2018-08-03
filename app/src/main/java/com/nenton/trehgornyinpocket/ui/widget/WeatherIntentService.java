package com.nenton.trehgornyinpocket.ui.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.nenton.trehgornyinpocket.BuildConfig;
import com.nenton.trehgornyinpocket.data.managers.AppDatabase;
import com.nenton.trehgornyinpocket.data.managers.DataManager;
import com.nenton.trehgornyinpocket.data.network.res.ResponseWeather;
import com.nenton.trehgornyinpocket.data.storage.dto.WeatherDto;
import com.nenton.trehgornyinpocket.data.storage.room.WeatherEntity;
import com.nenton.trehgornyinpocket.utils.AppConfig;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nenton.trehgornyinpocket.ui.widget.AppWidget.PREFS_NAME;
import static com.nenton.trehgornyinpocket.ui.widget.AppWidget.PREF_WIDGET_ID;

public class WeatherIntentService extends IntentService {
    public WeatherIntentService() {
        super("WeatherIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        DataManager instance = DataManager.getInstance();
        Call<ResponseWeather> weatherCall = instance.getRestService().getWeather(AppConfig.ID_CITY, BuildConfig.ApiKey);
        weatherCall.enqueue(new Callback<ResponseWeather>() {
            @Override
            public void onResponse(Call<ResponseWeather> call, Response<ResponseWeather> response) {
                if (response.code() == 200) {
                    ResponseWeather weather = response.body();
                    for (ResponseWeather.List list : weather.getList()) {
                        WeatherEntity entity = new WeatherEntity(list);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseWeather> call, Throwable t) {
                Toast.makeText(WeatherIntentService.this, "Error update weather", Toast.LENGTH_LONG).show();
            }
        });
        WeatherEntity currentDayWeather = AppDatabase.getInstance(this.getApplication()).weatherDao().getCurrentDayWeather();
        WeatherDto weatherDto = new WeatherDto(currentDayWeather);
        int widgetId = getSharedPreferences(PREFS_NAME, 0).getInt(PREF_WIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        AppWidget.saveTitlePref(this, widgetId, weatherDto);
        AppWidget.updateAppWidget(this, AppWidgetManager.getInstance(this), widgetId);
    }
}
