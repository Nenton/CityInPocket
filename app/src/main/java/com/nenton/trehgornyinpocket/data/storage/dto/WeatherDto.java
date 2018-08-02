package com.nenton.trehgornyinpocket.data.storage.dto;

import com.nenton.trehgornyinpocket.data.storage.room.WeatherEntity;
import com.nenton.trehgornyinpocket.data.storage.room.WeatherType;

public class WeatherDto {
    private String temperatureMax;
    private String temperatureMin;
    private long date;
    private WeatherType weatherType;
    private String windSpeed;

    public WeatherDto(String temperatureMax, String temperatureMin, long date, WeatherType weatherType, String windSpeed) {
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

    public String getTemperatureMax() {
        return temperatureMax;
    }

    public String getTemperatureMin() {
        return temperatureMin;
    }

    public long getDate() {
        return date;
    }

    public WeatherType getWeatherType() {
        return weatherType;
    }

    public String getWindSpeed() {
        return windSpeed;
    }
}
