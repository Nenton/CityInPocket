package com.nenton.trehgornyinpocket.data.storage.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface WeatherDao {

    @Query("Select * from weather order by date")
    LiveData<List<WeatherEntity>> loadWeather();

    @Insert
    void insertWeather(WeatherEntity entity);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateWeather(WeatherEntity entity);

    @Delete
    void deleteWeather(WeatherEntity entity);

    @Query("delete from weather where id = :id")
    void deleteWeatherById(int id);

    @Query("delete from weather")
    void deleteAll();

    @Query("Select * from weather order by date asc limit 1")
    WeatherEntity getCurrentDayWeather();
}
