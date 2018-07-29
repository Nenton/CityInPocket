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
public interface OrganizationsDao {

    @Query("Select * from organizations")
    LiveData<List<OrganizationEntity>> loadAllOrganizations();

    @Insert
    void insertOrganization(OrganizationEntity entity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrganization(List<OrganizationEntity> list);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateOrganization(OrganizationEntity entity);

    @Delete
    void deleteOrganization(OrganizationEntity entity);

    @Query("delete from organizations where id = :id")
    void deleteOrganizationById(int id);

    @Query("Select * from organizations where title like :query or description like :query")
    LiveData<List<OrganizationEntity>> loadOrganizationsByQuery(String query);
}
