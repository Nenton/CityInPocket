package com.nenton.trehgornyinpocket.di.components;
import com.nenton.trehgornyinpocket.di.modules.PicassoCacheModule;
import com.nenton.trehgornyinpocket.di.sqopes.RootScope;
import com.squareup.picasso.Picasso;

import dagger.Component;

@Component(dependencies = AppComponent.class, modules = PicassoCacheModule.class)
@RootScope
public interface PicassoCacheComponent {
    Picasso getPicasso();
}
