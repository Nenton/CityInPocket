package com.nenton.trehgornyinpocket.di.components;

import com.nenton.trehgornyinpocket.data.managers.DataManager;
import com.nenton.trehgornyinpocket.di.modules.LocalModule;
import com.nenton.trehgornyinpocket.di.modules.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

@Component(dependencies = AppComponent.class, modules = {LocalModule.class, NetworkModule.class})
@Singleton
public interface DataManagerComponent {
    void inject(DataManager dataManager);
}
