package com.nenton.trehgornyinpocket.ui.screens.organization;

import android.os.Bundle;

import com.nenton.trehgornyinpocket.R;
import com.nenton.trehgornyinpocket.data.storage.room.OrganizationEntity;
import com.nenton.trehgornyinpocket.di.DaggerService;
import com.nenton.trehgornyinpocket.di.sqopes.DaggerScope;
import com.nenton.trehgornyinpocket.flow.AbstractScreen;
import com.nenton.trehgornyinpocket.flow.Screen;
import com.nenton.trehgornyinpocket.mvp.models.OrganizationModel;
import com.nenton.trehgornyinpocket.mvp.presenters.AbstractPresenter;
import com.nenton.trehgornyinpocket.mvp.presenters.RootPresenter;
import com.nenton.trehgornyinpocket.ui.activities.RootActivity;
import com.squareup.picasso.Picasso;

import dagger.Provides;
import mortar.MortarScope;

@Screen(R.layout.screen_organization)
public class OrganizationScreen extends AbstractScreen<RootActivity.RootComponent> {
    private OrganizationEntity organization;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        OrganizationScreen that = (OrganizationScreen) o;

        return organization != null ? organization.equals(that.organization) : that.organization == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (organization != null ? organization.hashCode() : 0);
        return result;
    }

    public OrganizationScreen(OrganizationEntity organization) {
        this.organization = organization;
    }

    @Override
    public Object createScreenComponent(RootActivity.RootComponent parentComponent) {
        return DaggerOrganizationScreen_Component.builder()
                .rootComponent(parentComponent)
                .module(new Module())
                .build();
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

        void inject(OrganizationAdapter adapter);

        Picasso getPicasso();

        RootPresenter getRootPresenter();
    }

    public class OrganizationPresenter extends AbstractPresenter<OrganizationView, OrganizationModel> {

        @Override
        protected void initActionBar() {
            mRootPresenter.newActionBarBuilder()
                    .setTitle("Organization")
                    .setBackArrow(true)
                    .build();
        }

        @Override
        protected void initDagger(MortarScope scope) {
            Component component = scope.getService(DaggerService.SERVICE_NAME);
            component.inject(this);
        }

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);
            if (getView() != null) {
                getView().initView(organization);
            }
        }
    }
}
