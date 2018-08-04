package com.nenton.trehgornyinpocket.data.storage.dto;

import com.nenton.trehgornyinpocket.data.storage.room.WeatherEntity;
import com.nenton.trehgornyinpocket.data.storage.room.WeatherType;

public class WeatherDto {
    private float temperatureMax;
    private float temperatureMin;
    private long date;
    private WeatherType weatherType;
    private float windSpeed;

    public WeatherDto(float temperatureMax, float temperatureMin, long date, WeatherType weatherType, float windSpeed) {
        this.temperatureMax = temperatureMax;
        this.temperatureMin = temperatureMin;
        this.date = date;
        this.weatherType = weatherType;
        this.windSpeed = windSpeed;
    }

    public WeatherDto(WeatherEntity currentDayWeather) {
        this.temperatureMax = currentDayWeather.getTemperatureMax();
        this.temperatureMin = currentDayWeather.getTemperatureMin();
        this.date = currentDayWeather.getDate().getTime();
        this.weatherType = currentDayWeather.getWeatherType();
        this.windSpeed = currentDayWeather.getWindSpeed();
    }

    public float getTemperatureMax() {
        return temperatureMax;
    }

    public float getTemperatureMin() {
        return temperatureMin;
    }

    public long getDate() {
        return date;
    }

    public WeatherType getWeatherType() {
        return weatherType;
    }

    public float getWindSpeed() {
        return windSpeed;
    }
}
