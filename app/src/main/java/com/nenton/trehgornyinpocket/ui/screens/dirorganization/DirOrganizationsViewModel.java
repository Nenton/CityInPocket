package com.nenton.trehgornyinpocket.ui.screens.dirorganization;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;

import com.nenton.trehgornyinpocket.data.managers.AppDatabase;
import com.nenton.trehgornyinpocket.data.storage.room.OrganizationEntity;

import java.util.List;

public class DirOrganizationsViewModel extends AndroidViewModel {
    private LiveData<List<OrganizationEntity>> organizationsAll;
    private LiveData<List<OrganizationEntity>> organizationsByQuery;
    private MediatorLiveData<List<OrganizationEntity>> mediatorLiveData = new MediatorLiveData<>();

    public DirOrganizationsViewModel(@NonNull Application application) {
        super(application);
        organizationsAll = AppDatabase.getInstance(this.getApplication()).organizationsDao().loadAllOrganizations();
    }

    public LiveData<List<OrganizationEntity>> getOrganizationsByQuery(String query) {
        if (organizationsByQuery != null) {
            mediatorLiveData.removeSource(organizationsByQuery);
        }
        organizationsByQuery = AppDatabase.getInstance(this.getApplication()).organizationsDao().loadOrganizationsByQuery(query);
        mediatorLiveData.addSource(organizationsByQuery, value -> mediatorLiveData.setValue(value));
        return organizationsByQuery;
    }

    public LiveData<List<OrganizationEntity>> getOrganizationsAll() {
        return organizationsAll;
    }
}
