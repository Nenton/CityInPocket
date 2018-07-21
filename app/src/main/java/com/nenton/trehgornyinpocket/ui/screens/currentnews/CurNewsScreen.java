package com.nenton.trehgornyinpocket.ui.screens.currentnews;

import com.nenton.trehgornyinpocket.di.DaggerService;
import com.nenton.trehgornyinpocket.di.sqopes.DaggerScope;
import com.nenton.trehgornyinpocket.flow.AbstractScreen;
import com.nenton.trehgornyinpocket.mvp.presenters.AbstractPresenter;
import com.nenton.trehgornyinpocket.mvp.presenters.RootPresenter;
import com.nenton.trehgornyinpocket.ui.activities.RootActivity;
import com.squareup.picasso.Picasso;

import dagger.Provides;
import mortar.MortarScope;

public class CurNewsScreen extends AbstractScreen<RootActivity.RootComponent> {
    @Override
    public Object createScreenComponent(RootActivity.RootComponent parentComponent) {
        return null;
    }

    @dagger.Module
    public class Module {
        @Provides
        @DaggerScope(CurNewsScreen.class)
        CurNewsModel provideCurNewsModel() {
            return new CurNewsModel();
        }

        @Provides
        @DaggerScope(CurNewsScreen.class)
        CurNewsPresenter provideCurNewsPresenter() {
            return new CurNewsPresenter();
        }
    }

    @dagger.Component(dependencies = RootActivity.RootComponent.class, modules = Module.class)
    @DaggerScope(CurNewsScreen.class)
    public interface Component {
        void inject(CurNewsPresenter presenter);

        void inject(CurNewsView view);

        void inject(CurNewsAdapter adapter);

        Picasso getPicasso();

        RootPresenter getRootPresenter();
    }

    public class CurNewsPresenter extends AbstractPresenter<CurNewsView, CurNewsModel>{

        @Override
        protected void initActionBar() {

        }

        @Override
        protected void initDagger(MortarScope scope) {
            ((Component) scope.getService(DaggerService.SERVICE_NAME)).inject(this);
        }
    }
}
