package com.nenton.trehgornyinpocket.ui.screens.curannoncement;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;

import com.nenton.trehgornyinpocket.data.managers.AppDatabase;
import com.nenton.trehgornyinpocket.data.storage.room.AnnouncementEntity;

import java.util.List;

public class AnnouncementsModel extends AndroidViewModel {
    private LiveData<List<AnnouncementEntity>> announcementsAll;
    private LiveData<List<AnnouncementEntity>> announcementsByQuery;
    private MediatorLiveData<List<AnnouncementEntity>> mediatorLiveData = new MediatorLiveData<>();

    public AnnouncementsModel(@NonNull Application application) {
        super(application);
        announcementsAll = AppDatabase.getInstance(this.getApplication()).announcementsDao().loadAllAnnouncements();
    }

    public LiveData<List<AnnouncementEntity>> getNewsByQuery(String query) {
        if (announcementsByQuery != null) {
            mediatorLiveData.removeSource(announcementsByQuery);
        }
        announcementsByQuery = AppDatabase.getInstance(this.getApplication()).announcementsDao().loadAnnouncementsByQuery(query);
        mediatorLiveData.addSource(announcementsByQuery, value -> mediatorLiveData.setValue(value));
        return announcementsByQuery;
    }

    public LiveData<List<AnnouncementEntity>> getNewsAll() {
        return announcementsAll;
    }
}
