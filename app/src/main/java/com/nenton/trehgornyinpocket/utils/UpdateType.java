package com.nenton.trehgornyinpocket.utils;

public enum UpdateType {
    ALL_UPDATE,
    NEWS_UPDATE,
    ANNOUNCEMENTS_UPDATE,
    ORGANIZATIONS_UPDATE,
    WEATHER_UPDATE;

    public String getName() {
        return this.name();
    }
}
