package com.nenton.trehgornyinpocket.data.managers;

import com.nenton.trehgornyinpocket.data.network.RestService;
import com.nenton.trehgornyinpocket.di.DaggerService;
import com.nenton.trehgornyinpocket.di.components.DaggerDataManagerComponent;
import com.nenton.trehgornyinpocket.di.components.DataManagerComponent;
import com.nenton.trehgornyinpocket.di.modules.LocalModule;
import com.nenton.trehgornyinpocket.di.modules.NetworkModule;
import com.nenton.trehgornyinpocket.utils.App;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class DataManager {

    private static DataManager ourInstance = new DataManager();

    @Inject
    RestService mRestService;
    @Inject
    PreferencesManager mPreferencesManager;
    @Inject
    Retrofit mRetrofit;

    public PreferencesManager getPreferencesManager() {
        return mPreferencesManager;
    }

    public static DataManager getInstance() {
        return ourInstance;
    }

    private DataManager() {
        DataManagerComponent component = DaggerService.getComponent(DataManagerComponent.class);
        if (component == null) {
            component = DaggerDataManagerComponent.builder()
                    .appComponent(App.getAppComponent())
                    .localModule(new LocalModule())
                    .networkModule(new NetworkModule())
                    .build();
            DaggerService.registerComponent(DataManagerComponent.class, component);
        }
        component.inject(this);
    }
}
