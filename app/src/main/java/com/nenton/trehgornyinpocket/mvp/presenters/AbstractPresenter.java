package com.nenton.trehgornyinpocket.mvp.presenters;

import android.arch.lifecycle.LiveData;
import android.os.Bundle;

import com.nenton.trehgornyinpocket.mvp.models.AbstractModel;
import com.nenton.trehgornyinpocket.mvp.views.AbstractView;
import com.nenton.trehgornyinpocket.mvp.views.IRootView;
import com.nenton.trehgornyinpocket.ui.activities.RootActivity;
import com.nenton.trehgornyinpocket.utils.NetworkStatusChecker;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import mortar.MortarScope;
import mortar.ViewPresenter;

public abstract class AbstractPresenter<V extends AbstractView, M extends AbstractModel> extends ViewPresenter<V> {
    @Inject
    protected M mModel;

    @Inject
    protected RootPresenter mRootPresenter;

    protected List<LiveData> mListLiveData = new ArrayList<>();

    @Override
    protected void onEnterScope(MortarScope scope) {
        super.onEnterScope(scope);
        initDagger(scope);
    }


    @Override
    protected void onLoad(Bundle savedInstanceState) {
        super.onLoad(savedInstanceState);
        initActionBar();
    }

    protected void checkNet() {
        if (!NetworkStatusChecker.isNetworkAvailible()) {
            getRootView().showError("Network not available. Try later");
        }
    }

    @Override
    public void dropView(V view) {
        if (!mListLiveData.isEmpty()) {
            for (LiveData liveData : mListLiveData) {
                liveData.removeObservers(((RootActivity) getRootView()));
            }
        }
        super.dropView(view);
    }

    protected abstract void initActionBar();

    protected abstract void initDagger(MortarScope scope);


    protected IRootView getRootView() {
        return mRootPresenter.getRootView();
    }
}
