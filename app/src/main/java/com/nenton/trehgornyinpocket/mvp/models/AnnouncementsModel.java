package com.nenton.trehgornyinpocket.mvp.models;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;

import com.nenton.trehgornyinpocket.data.storage.room.AnnouncementEntity;
import com.nenton.trehgornyinpocket.ui.activities.RootActivity;
import com.nenton.trehgornyinpocket.ui.screens.annoncements.AnnouncementsViewModel;

import java.util.List;

public class AnnouncementsModel extends AbstractModel {

    public LiveData<List<AnnouncementEntity>> getAnnouncementsAllObs(RootActivity lifecycle) {
        return ViewModelProviders.of(lifecycle).get(AnnouncementsViewModel.class).getAnnouncementsAll();
    }

    public LiveData<List<AnnouncementEntity>> getAnnouncementsOnSearch(RootActivity lifecycle, String query) {
        return ViewModelProviders.of(lifecycle).get(AnnouncementsViewModel.class).getAnnouncementsByQuery(query);
    }
}
