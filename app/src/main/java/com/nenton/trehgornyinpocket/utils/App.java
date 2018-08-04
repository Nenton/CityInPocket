package com.nenton.trehgornyinpocket.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.crashlytics.android.Crashlytics;
import com.facebook.stetho.Stetho;
import com.nenton.trehgornyinpocket.data.managers.AppDatabase;
import com.nenton.trehgornyinpocket.di.DaggerService;
import com.nenton.trehgornyinpocket.di.components.AppComponent;
import com.nenton.trehgornyinpocket.di.components.DaggerAppComponent;
import com.nenton.trehgornyinpocket.di.modules.AppModule;
import com.nenton.trehgornyinpocket.di.modules.PicassoCacheModule;
import com.nenton.trehgornyinpocket.di.modules.RootModule;
import com.nenton.trehgornyinpocket.mortar.ScreenScoper;
import com.nenton.trehgornyinpocket.ui.activities.DaggerRootActivity_RootComponent;
import com.nenton.trehgornyinpocket.ui.activities.RootActivity;

import io.fabric.sdk.android.Fabric;
import mortar.MortarScope;
import mortar.bundler.BundleServiceRunner;

public class App extends Application {

    private SharedPreferences sSharedPreferences;
    private static Context sContext;
    private static AppComponent sAppComponent;
    private RootActivity.RootComponent mRootActivityRootComponent;
    private MortarScope mMortarScope;
    private MortarScope mRootActivityScope;

    public static AppComponent getAppComponent() {
        return sAppComponent;
    }

    @Override
    public Object getSystemService(String name) {
        // т. к. выполняем инструментальный тест выполняем так иначе не найдет mortarScope
        if (mMortarScope != null) {
            return mMortarScope.hasService(name) ? mMortarScope.getService(name) : super.getSystemService(name);
        } else {
            return super.getSystemService(name);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sContext = getApplicationContext();
        AppDatabase.getInstance(sContext);
        sSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        createAppComponent();
        createRootActivityComponent();

        Stetho.initialize(Stetho.newInitializerBuilder(getApplicationContext())
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(getApplicationContext()))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(getApplicationContext()))
                .build());

        mMortarScope = MortarScope.buildRootScope()
                .withService(DaggerService.SERVICE_NAME, sAppComponent)
                .build("Root");

        mRootActivityScope = mMortarScope.buildChild()
                .withService(DaggerService.SERVICE_NAME, mRootActivityRootComponent)
                .withService(BundleServiceRunner.SERVICE_NAME, new BundleServiceRunner())
                .build(RootActivity.class.getName());

        ScreenScoper.registerScope(mMortarScope);
        ScreenScoper.registerScope(mRootActivityScope);

        final Fabric fabric = new Fabric.Builder(this)
                .kits(new Crashlytics())
                .debuggable(true)           // Enables Crashlytics debugger
                .build();
        Fabric.with(fabric);
    }

    private void createRootActivityComponent() {
        mRootActivityRootComponent = DaggerRootActivity_RootComponent.builder()
                .appComponent(sAppComponent)
                .rootModule(new RootModule())
                .picassoCacheModule(new PicassoCacheModule())
                .build();
    }

    private void createAppComponent() {
        sAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(getApplicationContext()))
                .build();
    }

    public static Context getContext() {
        return sContext;
    }

    public RootActivity.RootComponent getRootActivityRootComponent() {
        return mRootActivityRootComponent;
    }
}
