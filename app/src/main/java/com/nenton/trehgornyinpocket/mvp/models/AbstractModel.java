package com.nenton.trehgornyinpocket.mvp.models;

import com.birbit.android.jobqueue.JobManager;
import com.nenton.trehgornyinpocket.data.managers.AppDatabase;
import com.nenton.trehgornyinpocket.data.managers.DataManager;
import com.nenton.trehgornyinpocket.di.DaggerService;
import com.nenton.trehgornyinpocket.di.components.DaggerModelComponent;
import com.nenton.trehgornyinpocket.di.components.ModelComponent;
import com.nenton.trehgornyinpocket.di.modules.ModelModule;
import com.nenton.trehgornyinpocket.utils.App;

import javax.inject.Inject;

public abstract class AbstractModel {
    @Inject
    DataManager mDataManager;
    @Inject
    JobManager mJobManager;
    @Inject
    AppDatabase appDatabase;

    public AbstractModel() {
        ModelComponent component = DaggerService.getComponent(ModelComponent.class);
        if (component == null) {
            component = createDaggerModelComponent();
            DaggerService.registerComponent(ModelComponent.class, component);
        }
        component.inject(this);
    }

    private ModelComponent createDaggerModelComponent() {
        return DaggerModelComponent.builder()
                .appComponent(App.getAppComponent())
                .modelModule(new ModelModule())
                .build();
    }
}