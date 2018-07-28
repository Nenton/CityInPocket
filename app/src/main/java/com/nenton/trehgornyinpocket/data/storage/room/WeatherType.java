package com.nenton.trehgornyinpocket.data.storage.room;

import java.io.Serializable;

public enum WeatherType implements Serializable {
    SUMMER, CLOUDS, PARTLY_CLOUDY,
    HEAVY_RAIN, RAIN, RAIN_CLOUD,
    SLEET, SNOW, STORM, WINDY;

    public String getType() {
        return this.name();
    }
}
