package com.nenton.trehgornyinpocket.ui.screens.annoncements;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;

import com.nenton.trehgornyinpocket.data.managers.AppDatabase;
import com.nenton.trehgornyinpocket.data.storage.room.AnnouncementEntity;

import java.util.List;

public class AnnouncementsViewModel extends AndroidViewModel {
    private LiveData<List<AnnouncementEntity>> announcementsAll;
    private LiveData<List<AnnouncementEntity>> announcementsByQuery;
    private MediatorLiveData<List<AnnouncementEntity>> mediatorLiveData = new MediatorLiveData<>();

    public AnnouncementsViewModel(@NonNull Application application) {
        super(application);
        announcementsAll = AppDatabase.getInstance(this.getApplication()).announcementsDao().loadAllAnnouncements();
    }

    public LiveData<List<AnnouncementEntity>> getAnnouncementsByQuery(String query) {
        if (announcementsByQuery != null) {
            mediatorLiveData.removeSource(announcementsByQuery);
        }
        announcementsByQuery = AppDatabase.getInstance(this.getApplication()).announcementsDao().loadAnnouncementsByQuery(query);
        mediatorLiveData.addSource(announcementsByQuery, value -> mediatorLiveData.setValue(value));
        return announcementsByQuery;
    }

    public LiveData<List<AnnouncementEntity>> getAnnouncementsAll() {
        return announcementsAll;
    }
}
