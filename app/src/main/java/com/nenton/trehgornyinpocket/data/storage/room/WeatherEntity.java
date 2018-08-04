package com.nenton.trehgornyinpocket.data.storage.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.nenton.trehgornyinpocket.data.network.res.ResponseWeather;
import com.nenton.trehgornyinpocket.utils.ViewHelper;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

@Entity(tableName = "weather")
public class WeatherEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private float temperatureMax;
    private float temperatureMin;
    private Date date;
    private String dateText;
    private WeatherType weatherType;
    private float windSpeed;

    public WeatherEntity(int id, float temperatureMax, float temperatureMin, Date date, String dateText, WeatherType weatherType, float windSpeed) {
        this.id = id;
        this.temperatureMax = temperatureMax;
        this.temperatureMin = temperatureMin;
        this.date = date;
        this.dateText = dateText;
        this.weatherType = weatherType;
        this.windSpeed = windSpeed;
    }

    @Ignore
    public WeatherEntity(float temperatureMax, float temperatureMin, Date date, WeatherType weatherType, float windSpeed) {
        this.temperatureMax = temperatureMax;
        this.temperatureMin = temperatureMin;
        this.date = date;
        this.dateText = (new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH)).format(date);
        this.weatherType = weatherType;
        this.windSpeed = windSpeed;
    }

    @Ignore
    public WeatherEntity(ResponseWeather.List list) {
        this.temperatureMax = list.getTemp().getMax() - 273.15f;
        this.temperatureMin = list.getTemp().getMin() - 273.15f;
        this.date = new Date(list.getDt() * 1000L);
        this.dateText = (new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH)).format(date);
        if (!list.getWeather().isEmpty()) {
            this.weatherType = ViewHelper.getWeatherTypeFromNet(list.getWeather().get(0).getMain());
        } else {
            this.weatherType = WeatherType.FEW_CLOUDS;
        }
        this.windSpeed = list.getSpeed();
    }

    public int getId() {
        return id;
    }

    public float getTemperatureMax() {
        return temperatureMax;
    }

    public float getTemperatureMin() {
        return temperatureMin;
    }

    public Date getDate() {
        return date;
    }

    public WeatherType getWeatherType() {
        return weatherType;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public String getDateText() {
        return dateText;
    }
}
