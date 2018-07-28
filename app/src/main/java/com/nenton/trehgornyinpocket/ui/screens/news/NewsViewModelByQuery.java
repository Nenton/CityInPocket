package com.nenton.trehgornyinpocket.ui.screens.news;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.nenton.trehgornyinpocket.data.managers.AppDatabase;
import com.nenton.trehgornyinpocket.data.storage.room.NewsEntity;

import java.util.List;

public class NewsViewModelByQuery extends ViewModel {

    private LiveData<List<NewsEntity>> newsByQuery;

    public NewsViewModelByQuery(AppDatabase database, String query) {
        this.newsByQuery = database.newsDao().loadNewsByQuery(query);
    }

    public LiveData<List<NewsEntity>> getNewsByQuery() {
        return newsByQuery;
    }
}
