package com.nenton.trehgornyinpocket.mvp.models;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;

import com.nenton.trehgornyinpocket.data.storage.room.WeatherEntity;
import com.nenton.trehgornyinpocket.data.storage.room.WeatherType;
import com.nenton.trehgornyinpocket.ui.activities.RootActivity;
import com.nenton.trehgornyinpocket.ui.screens.weather.WeatherViewModel;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class WeatherModel extends AbstractModel {
    public LiveData<List<WeatherEntity>> getWeatherOnFourteenDays(RootActivity lifecycle) {
//        mockData();
        return ViewModelProviders.of(lifecycle).get(WeatherViewModel.class).getNewsAll();
    }

    private void mockData() {
        List<WeatherEntity> weathers = new ArrayList<>();
        weathers.add(new WeatherEntity("17", "5", new Date(System.currentTimeMillis()), WeatherType.SUMMER, "2-6"));
        weathers.add(new WeatherEntity("21", "12", new Date(System.currentTimeMillis() + 86400000), WeatherType.SUMMER, "1-2"));
        weathers.add(new WeatherEntity("22", "7", new Date(System.currentTimeMillis() + 2 * 86400000), WeatherType.CLOUDS, "1-2"));
        weathers.add(new WeatherEntity("20", "5", new Date(System.currentTimeMillis() + 3 * 86400000), WeatherType.HEAVY_RAIN, "2-4"));
        weathers.add(new WeatherEntity("7", "0", new Date(System.currentTimeMillis() + 4 * 86400000), WeatherType.PARTLY_CLOUDY, "2-6"));
        weathers.add(new WeatherEntity("16", "6", new Date(System.currentTimeMillis() + 5 * 86400000), WeatherType.RAIN, "5-15"));
        weathers.add(new WeatherEntity("15", "5", new Date(System.currentTimeMillis() + 6 * 86400000), WeatherType.SLEET, "1-3"));
        weathers.add(new WeatherEntity("22", "10", new Date(System.currentTimeMillis() + 7 * 86400000), WeatherType.SNOW, "1-3"));
        weathers.add(new WeatherEntity("26", "7", new Date(System.currentTimeMillis() + 8 * 86400000), WeatherType.STORM, "2-6"));
        weathers.add(new WeatherEntity("28", "5", new Date(System.currentTimeMillis() + 9 * 86400000), WeatherType.WINDY, "20-25"));
        weathers.add(new WeatherEntity("30", "15", new Date(System.currentTimeMillis() + 10 * 86400000), WeatherType.RAIN_CLOUD, "5-8"));
        weathers.add(new WeatherEntity("32", "15", new Date(System.currentTimeMillis() + 11 * 86400000), WeatherType.SUMMER, "2-6"));
        new Thread(() -> {
            appDatabase.weatherDao().deleteAll();
            for (WeatherEntity entity : weathers) {
                appDatabase.weatherDao().insertWeather(entity);
            }
        }).start();
    }
}
