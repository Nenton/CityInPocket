package com.nenton.trehgornyinpocket.mvp.models;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;

import com.nenton.trehgornyinpocket.data.storage.room.NewsEntity;
import com.nenton.trehgornyinpocket.ui.activities.RootActivity;
import com.nenton.trehgornyinpocket.ui.screens.news.FindNewsViewModelFactory;
import com.nenton.trehgornyinpocket.ui.screens.news.NewsViewModel;
import com.nenton.trehgornyinpocket.ui.screens.news.NewsViewModelByQuery;

import java.util.List;

public class NewsModel extends AbstractModel {
    private NewsViewModel viewModel;

    public void setLifecycle(RootActivity lifecycle) {
        viewModel = ViewModelProviders.of(lifecycle).get(NewsViewModel.class);
    }

    public LiveData<List<NewsEntity>> getNewsAllObs() {
        return viewModel.getNewsAll();
    }

    public LiveData<List<NewsEntity>> getNewsOnSearch(RootActivity context, String query) {
        FindNewsViewModelFactory factory = new FindNewsViewModelFactory(appDatabase, query);
        return ViewModelProviders.of(context, factory).get(NewsViewModelByQuery.class).getNewsByQuery();
    }

}
