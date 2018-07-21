package com.nenton.trehgornyinpocket.ui.screens.curannoncement;

import com.nenton.trehgornyinpocket.di.DaggerService;
import com.nenton.trehgornyinpocket.di.sqopes.DaggerScope;
import com.nenton.trehgornyinpocket.flow.AbstractScreen;
import com.nenton.trehgornyinpocket.mvp.presenters.AbstractPresenter;
import com.nenton.trehgornyinpocket.mvp.presenters.RootPresenter;
import com.nenton.trehgornyinpocket.ui.activities.RootActivity;
import com.squareup.picasso.Picasso;

import dagger.Provides;
import mortar.MortarScope;

public class CurAnnouncementScreen extends AbstractScreen<RootActivity.RootComponent> {
    @Override
    public Object createScreenComponent(RootActivity.RootComponent parentComponent) {
        return null;
    }

    @dagger.Module
    public class Module {
        @Provides
        @DaggerScope(CurAnnouncementScreen.class)
        CurAnnouncementModel provideCurAnnouncementModel() {
            return new CurAnnouncementModel();
        }

        @Provides
        @DaggerScope(CurAnnouncementScreen.class)
        CurAnnouncementPresenter provideCurAnnouncementPresenter() {
            return new CurAnnouncementPresenter();
        }
    }

    @dagger.Component(dependencies = RootActivity.RootComponent.class, modules = Module.class)
    @DaggerScope(CurAnnouncementScreen.class)
    public interface Component {
        void inject(CurAnnouncementPresenter presenter);

        void inject(CurAnnouncementView view);

        void inject(CurAnnouncementAdapter adapter);

        Picasso getPicasso();

        RootPresenter getRootPresenter();
    }

    public class CurAnnouncementPresenter extends AbstractPresenter<CurAnnouncementView, CurAnnouncementModel>{

        @Override
        protected void initActionBar() {

        }

        @Override
        protected void initDagger(MortarScope scope) {
            ((Component) scope.getService(DaggerService.SERVICE_NAME)).inject(this);
        }
    }
}
