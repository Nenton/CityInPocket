package com.nenton.trehgornyinpocket.ui.screens.organization;

import com.nenton.trehgornyinpocket.di.DaggerService;
import com.nenton.trehgornyinpocket.di.sqopes.DaggerScope;
import com.nenton.trehgornyinpocket.flow.AbstractScreen;
import com.nenton.trehgornyinpocket.mvp.presenters.AbstractPresenter;
import com.nenton.trehgornyinpocket.mvp.presenters.RootPresenter;
import com.nenton.trehgornyinpocket.ui.activities.RootActivity;
import com.squareup.picasso.Picasso;

import dagger.Provides;
import mortar.MortarScope;

public class OrganizationScreen extends AbstractScreen<RootActivity.RootComponent> {
    @Override
    public Object createScreenComponent(RootActivity.RootComponent parentComponent) {
        return null;
    }

    @dagger.Module
    public class Module {
        @Provides
        @DaggerScope(OrganizationScreen.class)
        OrganizationModel provideOrganizationModel() {
            return new OrganizationModel();
        }

        @Provides
        @DaggerScope(OrganizationScreen.class)
        OrganizationPresenter provideOrganizationPresenter() {
            return new OrganizationPresenter();
        }
    }

    @dagger.Component(dependencies = RootActivity.RootComponent.class, modules = Module.class)
    @DaggerScope(OrganizationScreen.class)
    public interface Component {
        void inject(OrganizationPresenter presenter);

        void inject(OrganizationView view);

        Picasso getPicasso();

        RootPresenter getRootPresenter();
    }

    public class OrganizationPresenter extends AbstractPresenter<OrganizationView, OrganizationModel>{

        @Override
        protected void initActionBar() {

        }

        @Override
        protected void initDagger(MortarScope scope) {
            ((Component) scope.getService(DaggerService.SERVICE_NAME)).inject(this);
        }
    }
}
