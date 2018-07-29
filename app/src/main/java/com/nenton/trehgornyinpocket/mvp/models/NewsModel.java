package com.nenton.trehgornyinpocket.mvp.models;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;

import com.nenton.trehgornyinpocket.data.storage.room.NewsEntity;
import com.nenton.trehgornyinpocket.ui.activities.RootActivity;
import com.nenton.trehgornyinpocket.ui.screens.news.NewsViewModel;

import java.util.List;

public class NewsModel extends AbstractModel {

    public LiveData<List<NewsEntity>> getNewsAllObs(RootActivity lifecycle) {
        return ViewModelProviders.of(lifecycle).get(NewsViewModel.class).getNewsAll();
    }

    public LiveData<List<NewsEntity>> getNewsOnSearch(RootActivity context, String query) {
        return ViewModelProviders.of(context).get(NewsViewModel.class).getNewsByQuery(query);
    }
}