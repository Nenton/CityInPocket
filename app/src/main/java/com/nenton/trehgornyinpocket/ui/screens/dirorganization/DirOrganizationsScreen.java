package com.nenton.trehgornyinpocket.ui.screens.dirorganization;

import com.nenton.trehgornyinpocket.di.DaggerService;
import com.nenton.trehgornyinpocket.di.sqopes.DaggerScope;
import com.nenton.trehgornyinpocket.flow.AbstractScreen;
import com.nenton.trehgornyinpocket.mvp.presenters.AbstractPresenter;
import com.nenton.trehgornyinpocket.mvp.presenters.RootPresenter;
import com.nenton.trehgornyinpocket.ui.activities.RootActivity;
import com.squareup.picasso.Picasso;

import dagger.Provides;
import mortar.MortarScope;

public class DirOrganizationsScreen extends AbstractScreen<RootActivity.RootComponent> {
    @Override
    public Object createScreenComponent(RootActivity.RootComponent parentComponent) {
        return DaggerDirOrganizationsScreen_Component.builder()
                .rootComponent(parentComponent)
                .module(new Module())
                .build();
    }

    @dagger.Module
    public class Module {
        @Provides
        @DaggerScope(DirOrganizationsScreen.class)
        DirOrganizationsModel provideDirOrganizationsModel() {
            return new DirOrganizationsModel();
        }

        @Provides
        @DaggerScope(DirOrganizationsScreen.class)
        DirOrganizationsPresenter provideDirOrganizationsPresenter() {
            return new DirOrganizationsPresenter();
        }
    }

    @dagger.Component(dependencies = RootActivity.RootComponent.class, modules = Module.class)
    @DaggerScope(DirOrganizationsScreen.class)
    public interface Component {
        void inject(DirOrganizationsPresenter presenter);

        void inject(DirOrganizationsView view);

        void inject(DirOrganizationsAdapter adapter);

        Picasso getPicasso();

        RootPresenter getRootPresenter();
    }

    public class DirOrganizationsPresenter extends AbstractPresenter<DirOrganizationsView, DirOrganizationsModel>{

        @Override
        protected void initActionBar() {

        }

        @Override
        protected void initDagger(MortarScope scope) {
            ((Component) scope.getService(DaggerService.SERVICE_NAME)).inject(this);
        }
    }
}
