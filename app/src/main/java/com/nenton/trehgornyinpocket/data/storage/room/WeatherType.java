package com.nenton.trehgornyinpocket.data.storage.room;

import java.io.Serializable;

public enum WeatherType implements Serializable {
    CLEAR_SKY, FEW_CLOUDS, RAIN, SCATTERED_CLOUDS, BROKEN_CLOUDS,
    SHOWER_RAIN, SNOW, MIST, THUNDERSTORM;

    public String getType() {
        return this.name();
    }
}
