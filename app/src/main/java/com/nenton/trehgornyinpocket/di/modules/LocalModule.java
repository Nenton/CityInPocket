package com.nenton.trehgornyinpocket.di.modules;

import android.content.Context;


import com.nenton.trehgornyinpocket.data.managers.PreferencesManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LocalModule {

    @Provides
    @Singleton
    PreferencesManager provideAppPrefManager(Context context){
        return new PreferencesManager(context);
    }

}
