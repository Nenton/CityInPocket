package com.nenton.trehgornyinpocket.ui.screens.annoncements;

import com.nenton.trehgornyinpocket.R;
import com.nenton.trehgornyinpocket.di.DaggerService;
import com.nenton.trehgornyinpocket.di.sqopes.DaggerScope;
import com.nenton.trehgornyinpocket.flow.AbstractScreen;
import com.nenton.trehgornyinpocket.flow.Screen;
import com.nenton.trehgornyinpocket.mvp.presenters.AbstractPresenter;
import com.nenton.trehgornyinpocket.mvp.presenters.RootPresenter;
import com.nenton.trehgornyinpocket.ui.activities.RootActivity;
import com.squareup.picasso.Picasso;

import dagger.Provides;
import mortar.MortarScope;

@Screen(R.layout.screen_announcements)
public class AnnouncementsScreen extends AbstractScreen<RootActivity.RootComponent> {
    @Override
    public Object createScreenComponent(RootActivity.RootComponent parentComponent) {
        return DaggerAnnouncementsScreen_Component.builder()
                .rootComponent(parentComponent)
                .module(new Module())
                .build();
    }

    @dagger.Module
    public class Module {
        @Provides
        @DaggerScope(AnnouncementsScreen.class)
        AnnouncementsModel provideAnnouncementModel() {
            return new AnnouncementsModel();
        }

        @Provides
        @DaggerScope(AnnouncementsScreen.class)
        AnnouncementsPresenter provideAnnouncementPresenter() {
            return new AnnouncementsPresenter();
        }
    }

    @dagger.Component(dependencies = RootActivity.RootComponent.class, modules = Module.class)
    @DaggerScope(AnnouncementsScreen.class)
    public interface Component {
        void inject(AnnouncementsPresenter presenter);

        void inject(AnnouncementsView view);

        void inject(AnnouncementsAdapter adapter);

        Picasso getPicasso();

        RootPresenter getRootPresenter();
    }

    public class AnnouncementsPresenter extends AbstractPresenter<AnnouncementsView, AnnouncementsModel> {

        @Override
        protected void initActionBar() {
            mRootPresenter.newActionBarBuilder()
                    .setTitle("Announcements")
                    .build();
        }

        @Override
        protected void initDagger(MortarScope scope) {
            ((Component) scope.getService(DaggerService.SERVICE_NAME)).inject(this);
        }
    }
}
