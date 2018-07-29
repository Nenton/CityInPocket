package com.nenton.trehgornyinpocket.data.storage.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface NewsDao {

    @Query("Select * from news order by date")
    LiveData<List<NewsEntity>> loadAllNews();

    @Insert
    void insertNews(NewsEntity entity);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateNews(NewsEntity entity);

    @Delete
    void deleteNews(NewsEntity entity);

    @Query("delete from news where id = :id")
    void deleteNewsById(int id);

    @Query("Select * from news where title like :query or description like :query")
    LiveData<List<NewsEntity>> loadNewsByQuery(String query);
}
