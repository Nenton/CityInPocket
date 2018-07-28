package com.nenton.trehgornyinpocket.ui.screens.annoncements;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.SearchView;
import android.view.MenuItem;

import com.crashlytics.android.Crashlytics;
import com.nenton.trehgornyinpocket.R;
import com.nenton.trehgornyinpocket.data.storage.dto.AnnouncementDto;
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

import dagger.Provides;
import flow.Flow;
import mortar.MortarScope;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

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
            updateData(mModel.getAnnouncementsAllObs());
        }

        public void clickOnAnnouncement(AnnouncementDto announcement) {
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
            updateData(mModel.getAnnouncementsAllObs());
            query = "";
            return true;
        }

        private void updateData(Observable<AnnouncementDto> observable) {
            if (getView() != null) {
                getView().getAdapter().clearAdapter();
                mCompSubs.clear();
                mCompSubs.add(subscribeOnAnnouncement(observable));
            }
        }

        private Subscription subscribeOnAnnouncement(Observable<AnnouncementDto> observable) {
            return observable.subscribe(new DataSubscriber());
        }

        private void showNews(final String q, int delay) {
            Runnable runnable = () -> {
                query = q;
                updateData(mModel.getAnnouncementsOnSearch(query));
            };
            handler.removeCallbacks(runnable);
            handler.postDelayed(runnable, delay);
        }

        private class DataSubscriber extends Subscriber<AnnouncementDto> {
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
                    Crashlytics.logException(e);
                }
            }

            @Override
            public void onNext(AnnouncementDto announcementDto) {
                if (getView() != null) {
                    AnnouncementsAdapter adapter = getView().getAdapter();
                    adapter.addAnnouncement(announcementDto);
                }
            }
        }
    }
}
