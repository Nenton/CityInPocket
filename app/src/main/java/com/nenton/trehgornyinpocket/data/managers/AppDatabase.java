package com.nenton.trehgornyinpocket.data.managers;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.nenton.trehgornyinpocket.data.storage.room.AnnouncementEntity;
import com.nenton.trehgornyinpocket.data.storage.room.AnnouncementsDao;
import com.nenton.trehgornyinpocket.data.storage.room.NewsDao;
import com.nenton.trehgornyinpocket.data.storage.room.NewsEntity;
import com.nenton.trehgornyinpocket.data.storage.room.OrganizationEntity;
import com.nenton.trehgornyinpocket.data.storage.room.OrganizationsDao;
import com.nenton.trehgornyinpocket.data.storage.room.WeatherDao;
import com.nenton.trehgornyinpocket.data.storage.room.WeatherEntity;
import com.nenton.trehgornyinpocket.utils.ConverterRoom;

@Database(entities = {NewsEntity.class, AnnouncementEntity.class, OrganizationEntity.class, WeatherEntity.class}, version = 1, exportSchema = false)
@TypeConverters(value = ConverterRoom.class)
public abstract class AppDatabase extends RoomDatabase {
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "cityinpocket";
    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, AppDatabase.DATABASE_NAME)
                        .build();
            }
        }
        return sInstance;
    }

    public abstract NewsDao newsDao();

    public abstract AnnouncementsDao announcementsDao();

    public abstract OrganizationsDao organizationsDao();

    public abstract WeatherDao weatherDao();
}
