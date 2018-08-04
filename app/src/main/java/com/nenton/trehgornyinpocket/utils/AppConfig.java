package com.nenton.trehgornyinpocket.utils;

public interface AppConfig {
    String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    int ID_CITY = 830844;
    //    http://api.openweathermap.org/data/2.5/forecast?id=830844&APPID=2b5bdaf6820931db0f1f2f40fa9458e6
    long MAX_CONNECTION_TIMEOUT = 5 * 1000L;
    long MAX_READ_TIMEOUT = 5 * 1000L;
    long MAX_WRITE_TIMEOUT = 5 * 1000L;
    int MIN_CONSUMER_COUNT = 1;
    int MAX_CONSUMER_COUNT = 3;
    int LOAD_FACTOR = 3;
    int KEEP_ALIVE = 120;
    int INITIAL_BACK_OFF_IN_MS = 1000;
    int UPDATE_DATA_INTERVAL = 30;
    int RETRY_REQUEST_COUNT = 5;
    int RETRY_REQUEST_BASE_DELAY = 500;
}
