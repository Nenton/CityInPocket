package com.nenton.trehgornyinpocket.ui.screens.dirorganization;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.SearchView;
import android.view.MenuItem;

import com.nenton.trehgornyinpocket.R;
import com.nenton.trehgornyinpocket.data.storage.dto.OrganizationDto;
import com.nenton.trehgornyinpocket.di.DaggerService;
import com.nenton.trehgornyinpocket.di.sqopes.DaggerScope;
import com.nenton.trehgornyinpocket.flow.AbstractScreen;
import com.nenton.trehgornyinpocket.flow.Screen;
import com.nenton.trehgornyinpocket.mvp.presenters.AbstractPresenter;
import com.nenton.trehgornyinpocket.mvp.presenters.RootPresenter;
import com.nenton.trehgornyinpocket.ui.activities.RootActivity;
import com.nenton.trehgornyinpocket.ui.screens.organization.OrganizationScreen;
import com.squareup.picasso.Picasso;

import dagger.Provides;
import flow.Flow;
import mortar.MortarScope;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

@Screen(R.layout.screen_organizations)
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

    public class DirOrganizationsPresenter extends AbstractPresenter<DirOrganizationsView, DirOrganizationsModel>
            implements MenuItem.OnActionExpandListener, SearchView.OnQueryTextListener {
        private String query;
        private Handler handler = new Handler();

        @Override
        protected void initActionBar() {
            mRootPresenter.newActionBarBuilder()
                    .setTitle("Organizations")
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
            updateData(mModel.getOrganizationAllObs());
        }

        public void clickOnOrganization(OrganizationDto organization) {
            if (getView() != null) {
                OrganizationScreen organizationScreen = new OrganizationScreen(organization);
                Flow.get(getView().getContext())
                        .set(organizationScreen);
            }
        }

        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            if (newText.isEmpty()) {
                showNews(newText, 0);
            } else {
                showNews(newText, 2000);
            }
            return true;
        }

        @Override
        public boolean onMenuItemActionExpand(MenuItem menuItem) {
            return true;
        }

        @Override
        public boolean onMenuItemActionCollapse(MenuItem menuItem) {
            updateData(mModel.getOrganizationAllObs());
            query = "";
            return true;
        }

        private void updateData(Observable<OrganizationDto> observable) {
            if (getView() != null) {
                getView().getAdapter().clearAdapter();
                mCompSubs.clear();
                mCompSubs.add(subscribeOnOrganizations(observable));
            }
        }

        private Subscription subscribeOnOrganizations(Observable<OrganizationDto> observable) {
            return observable.subscribe(new DataSubscriber());
        }

        private void showNews(final String q, int delay) {
            Runnable runnable = () -> {
                query = q;
                updateData(mModel.getOrganizationsOnSearch(query));
            };
            handler.removeCallbacks(runnable);
            handler.postDelayed(runnable, delay);
        }

        private class DataSubscriber extends Subscriber<OrganizationDto> {
            @Override
            public void onCompleted() {
                if (getRootView() != null) {
                    getRootView().showMessage("Completed!");
                }
            }

            @Override
            public void onError(Throwable e) {
                if (getRootView() != null) {
                    getRootView().showError(e);
                    // TODO: 22.07.2018 Add Firebase Crash
                }
            }

            @Override
            public void onNext(OrganizationDto organization) {
                if (getView() != null) {
                    DirOrganizationsAdapter adapter = getView().getAdapter();
                    adapter.addOrganization(organization);
                }
            }
        }
    }
}
