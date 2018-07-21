package com.nenton.trehgornyinpocket.di.components;

import android.content.Context;


import com.nenton.trehgornyinpocket.di.modules.AppModule;

import dagger.Component;

@Component(modules = AppModule.class)
public interface AppComponent {
    Context getContext();
}
