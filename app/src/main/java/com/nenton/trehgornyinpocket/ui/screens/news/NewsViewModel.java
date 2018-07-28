package com.nenton.trehgornyinpocket.ui.screens.news;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;

import com.nenton.trehgornyinpocket.data.managers.AppDatabase;
import com.nenton.trehgornyinpocket.data.storage.room.NewsEntity;

import java.util.List;

public class NewsViewModel extends AndroidViewModel {
    private LiveData<List<NewsEntity>> newsAll;
    private LiveData<List<NewsEntity>> newsByQuery;
    private MediatorLiveData<List<NewsEntity>> mediatorLiveData = new MediatorLiveData<>();

    public NewsViewModel(@NonNull Application application) {
        super(application);
        newsAll = AppDatabase.getInstance(this.getApplication()).newsDao().loadAllNews();
    }

    public LiveData<List<NewsEntity>> getNewsByQuery(String query) {
        if (newsByQuery != null) {
            mediatorLiveData.removeSource(newsByQuery);
        }
        newsByQuery = AppDatabase.getInstance(this.getApplication()).newsDao().loadNewsByQuery(query);
        mediatorLiveData.addSource(newsByQuery, value -> mediatorLiveData.setValue(value));
        return newsByQuery;
    }

    public LiveData<List<NewsEntity>> getNewsAll() {
        return newsAll;
    }
}
