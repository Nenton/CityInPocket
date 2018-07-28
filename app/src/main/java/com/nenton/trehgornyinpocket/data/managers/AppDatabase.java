package com.nenton.trehgornyinpocket.data.managers;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.nenton.trehgornyinpocket.data.storage.room.NewsDao;
import com.nenton.trehgornyinpocket.data.storage.room.NewsEntity;
import com.nenton.trehgornyinpocket.utils.ConverterRoom;

@Database(entities = {NewsEntity.class}, version = 1, exportSchema = false)
@TypeConverters(value = ConverterRoom.class)
public abstract class AppDatabase extends RoomDatabase {
    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
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
}
