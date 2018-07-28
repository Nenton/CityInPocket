package com.nenton.trehgornyinpocket.ui.screens.news;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.nenton.trehgornyinpocket.data.managers.AppDatabase;
import com.nenton.trehgornyinpocket.data.storage.room.NewsEntity;

import java.util.List;

public class NewsViewModel extends AndroidViewModel {
    private LiveData<List<NewsEntity>> newsAll;

    public NewsViewModel(@NonNull Application application) {
        super(application);
        newsAll = AppDatabase.getInstance(this.getApplication()).newsDao().loadAllNews();
    }

    public LiveData<List<NewsEntity>> getNewsAll() {
        return newsAll;
    }
}
