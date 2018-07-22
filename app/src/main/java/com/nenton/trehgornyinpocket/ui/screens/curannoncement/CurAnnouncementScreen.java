package com.nenton.trehgornyinpocket.ui.screens.curannoncement;

import com.nenton.trehgornyinpocket.R;
import com.nenton.trehgornyinpocket.data.storage.dto.AnnouncementDto;
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

@Screen(R.layout.screen_cur_announcements)
public class CurAnnouncementScreen extends AbstractScreen<RootActivity.RootComponent> {
    private AnnouncementDto announcement;

    public CurAnnouncementScreen(AnnouncementDto announcement) {
        this.announcement = announcement;
    }

    @Override
    public Object createScreenComponent(RootActivity.RootComponent parentComponent) {
        return DaggerCurAnnouncementScreen_Component.builder()
                .rootComponent(parentComponent)
                .module(new Module())
                .build();
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

    public class CurAnnouncementPresenter extends AbstractPresenter<CurAnnouncementView, CurAnnouncementModel> {

        @Override
        protected void initActionBar() {
            mRootPresenter.newActionBarBuilder()
                    .setTitle("Current announcement")
                    .setBackArrow(true)
                    .build();
        }

        @Override
        protected void initDagger(MortarScope scope) {
            Component component = scope.getService(DaggerService.SERVICE_NAME);
            component.inject(this);
        }
    }
}
