package com.nenton.trehgornyinpocket.utils;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nenton.trehgornyinpocket.data.storage.room.ContactEntity;
import com.nenton.trehgornyinpocket.data.storage.room.WeatherType;

import java.lang.reflect.Type;
import java.sql.Date;
import java.util.List;

public class ConverterRoom {
    @TypeConverter
    public static List<String> fromString(String value) {
        Type listType = new TypeToken<List<String>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromList(List<String> list) {
        return new Gson().toJson(list);
    }

    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static List<ContactEntity> fromContacts(String value) {
        Type contactType = new TypeToken<List<ContactEntity>>() {
        }.getType();
        return new Gson().fromJson(value, contactType);
    }

    @TypeConverter
    public static String toContacts(List<ContactEntity> entities) {
        return new Gson().toJson(entities);
    }

    @TypeConverter
    public static WeatherType fromWeatherType(String value) {
        Type type = new TypeToken<WeatherType>() {
        }.getType();
        return new Gson().fromJson(value, type);
    }

    @TypeConverter
    public static String toWeatherType(WeatherType type) {
        return new Gson().toJson(type);
    }
}
