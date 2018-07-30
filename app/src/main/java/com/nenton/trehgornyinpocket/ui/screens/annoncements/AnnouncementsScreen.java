package com.nenton.trehgornyinpocket.ui.screens.annoncements;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.SearchView;
import android.view.MenuItem;

import com.nenton.trehgornyinpocket.R;
import com.nenton.trehgornyinpocket.data.storage.room.AnnouncementEntity;
import com.nenton.trehgornyinpocket.di.DaggerService;
import com.nenton.trehgornyinpocket.di.sqopes.DaggerScope;
import com.nenton.trehgornyinpocket.flow.AbstractScreen;
import com.nenton.trehgornyinpocket.flow.Screen;
import com.nenton.trehgornyinpocket.mvp.models.AnnouncementsModel;
import com.nenton.trehgornyinpocket.mvp.presenters.AbstractPresenter;
import com.nenton.trehgornyinpocket.mvp.presenters.MenuItemHolder;
import com.nenton.trehgornyinpocket.mvp.presenters.RootPresenter;
import com.nenton.trehgornyinpocket.ui.activities.RootActivity;
import com.nenton.trehgornyinpocket.ui.screens.curannoncement.CurAnnouncementScreen;
import com.squareup.picasso.Picasso;

import java.util.List;

import dagger.Provides;
import flow.Flow;
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

    public class AnnouncementsPresenter extends AbstractPresenter<AnnouncementsView, AnnouncementsModel> implements MenuItem.OnActionExpandListener, SearchView.OnQueryTextListener {
        private String query;
        private Handler handler = new Handler();

        @Override
        protected void initActionBar() {
            MenuItemHolder.MenuItemSearch itemSearch = new MenuItemHolder.MenuItemSearch(
                    "Search announcements by description", this, this, query, true);

            mRootPresenter.newActionBarBuilder()
                    .setTitle("Announcements")
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
            updateData(mModel.getAnnouncementsAllObs(((RootActivity) getRootView())));
        }

        public void clickOnAnnouncement(AnnouncementEntity announcement) {
            if (getView() != null) {
                CurAnnouncementScreen curAnnouncementScreen = new CurAnnouncementScreen(announcement);
                Flow.get(getView().getContext())
                        .set(curAnnouncementScreen);
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
            updateData(mModel.getAnnouncementsAllObs(((RootActivity) getRootView())));
            query = "";
            return true;
        }

        private void updateData(LiveData<List<AnnouncementEntity>> observable) {
            if (getRootView() != null && getView() != null) {
                observable.observe(((RootActivity) getRootView()),
                        new Observer<List<AnnouncementEntity>>() {
                            @Override
                            public void onChanged(@Nullable List<AnnouncementEntity> newsEntities) {
//                                observable.removeObserver(this);
                                getView().getAdapter().reloadAdapter(newsEntities);
                            }
                        });
            }
        }

        private void showNews(final String q, int delay) {
            Runnable runnable = () -> {
                query = q;
                updateData(mModel.getAnnouncementsOnSearch(((RootActivity) getRootView()), query));
            };
            handler.removeCallbacksAndMessages(null);
            handler.postDelayed(runnable, delay);
        }

        public void swipeUpdate() {
            // TODO: 30.07.2018 implement me
        }
    }
}
