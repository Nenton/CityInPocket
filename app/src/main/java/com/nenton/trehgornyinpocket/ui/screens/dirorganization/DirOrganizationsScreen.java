package com.nenton.trehgornyinpocket.ui.screens.dirorganization;

import android.arch.lifecycle.LiveData;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.MenuItem;

import com.nenton.trehgornyinpocket.R;
import com.nenton.trehgornyinpocket.data.storage.room.OrganizationEntity;
import com.nenton.trehgornyinpocket.di.DaggerService;
import com.nenton.trehgornyinpocket.di.sqopes.DaggerScope;
import com.nenton.trehgornyinpocket.flow.AbstractScreen;
import com.nenton.trehgornyinpocket.flow.Screen;
import com.nenton.trehgornyinpocket.mvp.models.DirOrganizationsModel;
import com.nenton.trehgornyinpocket.mvp.presenters.AbstractPresenter;
import com.nenton.trehgornyinpocket.mvp.presenters.MenuItemHolder;
import com.nenton.trehgornyinpocket.mvp.presenters.RootPresenter;
import com.nenton.trehgornyinpocket.ui.activities.RootActivity;
import com.nenton.trehgornyinpocket.ui.screens.organization.OrganizationScreen;
import com.nenton.trehgornyinpocket.utils.UpdateType;
import com.squareup.picasso.Picasso;

import java.util.List;

import dagger.Provides;
import flow.Flow;
import mortar.MortarScope;

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
        private Runnable runnable;

        @Override
        protected void initActionBar() {
            MenuItemHolder.MenuItemSearch itemSearch = new MenuItemHolder.MenuItemSearch(
                    "Search organizations by description", this, this, query, true);

            mRootPresenter.newActionBarBuilder()
                    .setTitle("Organizations")
                    .addAction(new MenuItemHolder("Search", R.drawable.ic_search, menuItem -> false, itemSearch))
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
            updateData(mModel.getOrganizationAllObs(((RootActivity) getRootView())));
        }

        public void clickOnOrganization(OrganizationEntity organization) {
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
            showNews(newText, 2000);
            return true;
        }

        @Override
        public boolean onMenuItemActionExpand(MenuItem menuItem) {
            return true;
        }

        @Override
        public boolean onMenuItemActionCollapse(MenuItem menuItem) {
            if (getView() != null && runnable != null) {
                getView().removeCallbacks(runnable);
            }
            updateData(mModel.getOrganizationAllObs(((RootActivity) getRootView())));
            query = "";
            return true;
        }

        private void updateData(LiveData<List<OrganizationEntity>> observable) {
            checkNet();
            if (getRootView() != null && getView() != null) {
                observable.observe(((RootActivity) getRootView()),
                        organizationsEntities -> {
                            if (getView() != null) {
                                getView().getAdapter().reloadAdapter(organizationsEntities);
                            }
                            getRootView().hideLoad();
                            if (organizationsEntities != null && !organizationsEntities.isEmpty()) {
                                getRootView().hideError();
                            }
                        });
                mListLiveData.add(observable);
            }
        }

        private void showNews(final String q, int delay) {
            Runnable newRunnable = () -> {
                query = q;
                updateData(mModel.getOrganizationsOnSearch(((RootActivity) getRootView()), query));
            };
            if (getView() != null) {
                if (runnable != null) {
                    getView().removeCallbacks(runnable);
                }
                runnable = newRunnable;
                getView().postDelayed(runnable, delay);
            }
        }

        public void swipeUpdate() {
            if (getRootView() != null) {
                getRootView().startUpdateService(UpdateType.ORGANIZATIONS_UPDATE);
            }
            if (query != null && !query.isEmpty()) {
                updateData(mModel.getOrganizationsOnSearch(((RootActivity) getRootView()), query));
            } else {
                updateData(mModel.getOrganizationAllObs(((RootActivity) getRootView())));
            }
        }
    }
}
