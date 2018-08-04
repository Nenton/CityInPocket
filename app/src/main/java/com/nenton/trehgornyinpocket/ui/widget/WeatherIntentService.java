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
import com.nenton.trehgornyinpocket.data.storage.room.WeatherDao;
import com.nenton.trehgornyinpocket.data.storage.room.WeatherEntity;
import com.nenton.trehgornyinpocket.utils.AppConfig;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

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
        Call<ResponseWeather> weatherCall = instance.getRestService().getWeather(AppConfig.ID_CITY, BuildConfig.ApiKey, 14);
        weatherCall.enqueue(new Callback<ResponseWeather>() {
            @Override
            public void onResponse(Call<ResponseWeather> call, Response<ResponseWeather> response) {
                if (response.code() == 200) {
                    Thread thread = new Thread(() -> {
                        ResponseWeather weather = response.body();
                        if (weather != null && weather.getList() != null) {
                            WeatherDao weatherDao = AppDatabase.getInstance(WeatherIntentService.this).weatherDao();
                            weatherDao.deleteAll();
                            for (ResponseWeather.List list : weather.getList()) {
                                weatherDao.insertWeather(new WeatherEntity(list));
                            }
                        }
                    });
                    thread.start();
                }
                checkDateWeather();
            }

            @Override
            public void onFailure(Call<ResponseWeather> call, Throwable t) {
                checkDateWeather();
                Toast.makeText(WeatherIntentService.this, "Error update weather: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        checkDateWeather();
    }

    private void checkDateWeather() {
        Thread thread = new Thread(() -> {
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
            String curDate = format.format(new Date(System.currentTimeMillis()));
            WeatherEntity currentDayWeather = AppDatabase.getInstance(this.getApplication()).weatherDao().getCurrentDayWeather(curDate);
            if (currentDayWeather != null) {
                WeatherDto weatherDto = new WeatherDto(currentDayWeather);
                int widgetId = getSharedPreferences(PREFS_NAME, 0).getInt(PREF_WIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
                AppWidget.saveTitlePref(this, widgetId, weatherDto);
                AppWidget.updateAppWidget(this, AppWidgetManager.getInstance(this), widgetId, false);
            }
        });
        thread.start();
    }
}
