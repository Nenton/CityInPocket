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
public interface AnnouncementsDao {

    @Query("Select * from announcements order by date")
    LiveData<List<AnnouncementEntity>> loadAllAnnouncements();

    @Insert
    void insertAnnouncement(AnnouncementEntity entity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAnnouncement(List<AnnouncementEntity> list);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateAnnouncement(AnnouncementEntity entity);

    @Delete
    void deleteAnnouncement(AnnouncementEntity entity);

    @Query("delete from announcements where id = :id")
    void deleteAnnouncementById(int id);

    @Query("Select * from announcements where title like :query or description like :query")
    LiveData<List<AnnouncementEntity>> loadAnnouncementsByQuery(String query);
}
