package com.nenton.trehgornyinpocket.data.storage.dto;

import com.nenton.trehgornyinpocket.data.storage.room.WeatherType;

import java.util.Date;

public class WeatherDto {
    private String temperatureMax;
    private String temperatureMin;
    private Date date;
    private WeatherType weatherType;
    private String windSpeed;

    public WeatherDto(String temperatureMax, String temperatureMin, Date date, WeatherType weatherType, String windSpeed) {
        this.temperatureMax = temperatureMax;
        this.temperatureMin = temperatureMin;
        this.date = date;
        this.weatherType = weatherType;
        this.windSpeed = windSpeed;
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
