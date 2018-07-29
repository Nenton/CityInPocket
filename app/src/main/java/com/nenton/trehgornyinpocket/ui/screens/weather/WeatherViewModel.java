package com.nenton.trehgornyinpocket.ui.screens.weather;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.nenton.trehgornyinpocket.data.managers.AppDatabase;
import com.nenton.trehgornyinpocket.data.storage.room.WeatherEntity;

import java.util.List;

public class WeatherViewModel extends AndroidViewModel {
    private LiveData<List<WeatherEntity>> weatherAll;

    public WeatherViewModel(@NonNull Application application) {
        super(application);
        weatherAll = AppDatabase.getInstance(this.getApplication()).weatherDao().loadWeather();
    }

    public LiveData<List<WeatherEntity>> getNewsAll() {
        return weatherAll;
    }
}
