package com.nenton.trehgornyinpocket.mvp.models;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;

import com.nenton.trehgornyinpocket.data.storage.room.OrganizationEntity;
import com.nenton.trehgornyinpocket.ui.activities.RootActivity;
import com.nenton.trehgornyinpocket.ui.screens.dirorganization.DirOrganizationsViewModel;

import java.util.List;

public class DirOrganizationsModel extends AbstractModel {
    public LiveData<List<OrganizationEntity>> getOrganizationAllObs(RootActivity lifecycle) {
        return ViewModelProviders.of(lifecycle).get(DirOrganizationsViewModel.class).getOrganizationsAll();
    }

    public LiveData<List<OrganizationEntity>> getOrganizationsOnSearch(RootActivity lifecycle, String query) {
        return ViewModelProviders.of(lifecycle).get(DirOrganizationsViewModel.class).getOrganizationsByQuery(query);
    }
}
