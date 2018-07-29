package com.nenton.trehgornyinpocket.data.storage.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.sql.Date;

@Entity(tableName = "weather")
public class WeatherEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String temperatureMax;
    private String temperatureMin;
    private Date date;
    private WeatherType weatherType;
    private String windSpeed;

    public WeatherEntity(int id, String temperatureMax, String temperatureMin, Date date, WeatherType weatherType, String windSpeed) {
        this.id = id;
        this.temperatureMax = temperatureMax;
        this.temperatureMin = temperatureMin;
        this.date = date;
        this.weatherType = weatherType;
        this.windSpeed = windSpeed;
    }

    @Ignore
    public WeatherEntity(String temperatureMax, String temperatureMin, Date date, WeatherType weatherType, String windSpeed) {
        this.temperatureMax = temperatureMax;
        this.temperatureMin = temperatureMin;
        this.date = date;
        this.weatherType = weatherType;
        this.windSpeed = windSpeed;
    }

    public int getId() {
        return id;
    }

    public String getTemperatureMax() {
        return temperatureMax;
    }

    public String getTemperatureMin() {
        return temperatureMin;
    }

    public Date getDate() {
        return date;
    }

    public WeatherType getWeatherType() {
        return weatherType;
    }

    public String getWindSpeed() {
        return windSpeed;
    }
}
