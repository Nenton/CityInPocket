package com.nenton.trehgornyinpocket.di.modules;

import com.nenton.trehgornyinpocket.di.sqopes.RootScope;
import com.nenton.trehgornyinpocket.mvp.presenters.RootPresenter;

import dagger.Provides;

@dagger.Module
public class RootModule {
    @Provides
    @RootScope
    RootPresenter provideRootPresenter() {
        return new RootPresenter();
    }
}
