package com.nenton.trehgornyinpocket.mvp.models;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;

import com.nenton.trehgornyinpocket.data.storage.room.WeatherEntity;
import com.nenton.trehgornyinpocket.ui.activities.RootActivity;
import com.nenton.trehgornyinpocket.ui.screens.weather.WeatherViewModel;

import java.util.List;

public class WeatherModel extends AbstractModel {
    public LiveData<List<WeatherEntity>> getWeatherOnFourteenDays(RootActivity lifecycle) {
        return ViewModelProviders.of(lifecycle).get(WeatherViewModel.class).getNewsAll();
    }
}
