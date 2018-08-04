package com.nenton.trehgornyinpocket.utils;

public class AppConfig {

    public static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    public static final int ID_CITY = 830844;
    public static final long MAX_CONNECTION_TIMEOUT = 5 * 1000L;
    public static final long MAX_READ_TIMEOUT = 5 * 1000L;
    public static final long MAX_WRITE_TIMEOUT = 5 * 1000L;
    public static final int MIN_CONSUMER_COUNT = 1;
    public static final int MAX_CONSUMER_COUNT = 3;
    public static final int LOAD_FACTOR = 3;
    public static final int KEEP_ALIVE = 120;

    private AppConfig() {
        throw new IllegalStateException("Utility class");
    }
}
