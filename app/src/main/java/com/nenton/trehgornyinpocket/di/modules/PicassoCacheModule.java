package com.nenton.trehgornyinpocket.di.modules;

import android.content.Context;

import com.nenton.trehgornyinpocket.di.sqopes.RootScope;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;

@Module
public class PicassoCacheModule {

    @Provides
    @RootScope
    Picasso providePicasso(Context context) {
        OkHttp3Downloader okHttp3Downloader = new OkHttp3Downloader(context);
        Picasso picasso = new Picasso.Builder(context)
                .downloader(okHttp3Downloader)
                .loggingEnabled(true)
                .build();
        Picasso.setSingletonInstance(picasso);
        return picasso;
    }
}
