package com.nenton.trehgornyinpocket.di.components;

import com.nenton.trehgornyinpocket.di.modules.ModelModule;
import com.nenton.trehgornyinpocket.mvp.models.AbstractModel;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = ModelModule.class)
@Singleton
public interface ModelComponent {
    void inject(AbstractModel abstractModel);
}
