package com.nenton.trehgornyinpocket.ui.screens.news;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.nenton.trehgornyinpocket.data.managers.AppDatabase;

public class FindNewsViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final AppDatabase mDb;
    private final String mQuery;

    public FindNewsViewModelFactory(AppDatabase mDb, String mQuery) {
        this.mDb = mDb;
        this.mQuery = mQuery;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new NewsViewModelByQuery(mDb, mQuery);
    }
}
