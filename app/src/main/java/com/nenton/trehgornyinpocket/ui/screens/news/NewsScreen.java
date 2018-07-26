package com.nenton.trehgornyinpocket.ui.screens.news;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.SearchView;
import android.view.MenuItem;

import com.crashlytics.android.Crashlytics;
import com.nenton.trehgornyinpocket.R;
import com.nenton.trehgornyinpocket.data.storage.dto.NewsDto;
import com.nenton.trehgornyinpocket.di.DaggerService;
import com.nenton.trehgornyinpocket.di.sqopes.DaggerScope;
import com.nenton.trehgornyinpocket.flow.AbstractScreen;
import com.nenton.trehgornyinpocket.flow.Screen;
import com.nenton.trehgornyinpocket.mvp.presenters.AbstractPresenter;
import com.nenton.trehgornyinpocket.mvp.presenters.MenuItemHolder;
import com.nenton.trehgornyinpocket.mvp.presenters.RootPresenter;
import com.nenton.trehgornyinpocket.ui.activities.RootActivity;
import com.nenton.trehgornyinpocket.ui.screens.currentnews.CurNewsScreen;
import com.squareup.picasso.Picasso;

import dagger.Provides;
import flow.Flow;
import mortar.MortarScope;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

@Screen(R.layout.screen_news)
public class NewsScreen extends AbstractScreen<RootActivity.RootComponent> {

    @Override
    public Object createScreenComponent(RootActivity.RootComponent parentComponent) {
        return DaggerNewsScreen_Component.builder()
                .rootComponent(parentComponent)
                .module(new Module())
                .build();
    }

    @dagger.Module
    public class Module {
        @Provides
        @DaggerScope(NewsScreen.class)
        NewsModel provideNewsModel() {
            return new NewsModel();
        }

        @Provides
        @DaggerScope(NewsScreen.class)
        NewsPresenter provideNewsPresenter() {
            return new NewsPresenter();
        }
    }

    @dagger.Component(dependencies = RootActivity.RootComponent.class, modules = Module.class)
    @DaggerScope(NewsScreen.class)
    public interface Component {
        void inject(NewsPresenter presenter);

        void inject(NewsView view);

        void inject(NewsAdapter adapter);

        Picasso getPicasso();

        RootPresenter getRootPresenter();
    }

    public class NewsPresenter extends AbstractPresenter<NewsView, NewsModel> implements MenuItem.OnActionExpandListener, SearchView.OnQueryTextListener {
        private String query;
        private Handler handler = new Handler();

        @Override
        protected void initActionBar() {
            MenuItemHolder.MenuItemSearch itemSearch = new MenuItemHolder.MenuItemSearch(
                    "Search news by description", this, this, query, true);

            mRootPresenter.newActionBarBuilder()
                    .setTitle("News")
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
            updateData(mModel.getNewsAllObs());
        }

        public void clickOnNew(NewsDto currentNew) {
            if (getView() != null) {
                CurNewsScreen curNewsScreen = new CurNewsScreen(currentNew);
                Flow.get(getView().getContext())
                        .set(curNewsScreen);
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
            updateData(mModel.getNewsAllObs());
            query = "";
            return true;
        }

        private void updateData(Observable<NewsDto> observable) {
            if (getView() != null) {
                getView().getAdapter().reloadAdapter();
                mCompSubs.clear();
                mCompSubs.add(subscribeOnNews(observable));
            }
        }

        private Subscription subscribeOnNews(Observable<NewsDto> observable) {
            return observable.subscribe(new DataSubscriber());
        }

        private void showNews(final String q, int delay) {
            Runnable runnable = () -> {
                query = q;
                updateData(mModel.getNewsOnSearch(query));
            };
            handler.removeCallbacks(runnable);
            handler.postDelayed(runnable, delay);
        }

        private class DataSubscriber extends Subscriber<NewsDto> {
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
            public void onNext(NewsDto newsDto) {
                if (getView() != null) {
                    NewsAdapter adapter = getView().getAdapter();
                    adapter.addNew(newsDto);
                }
            }
        }
    }
}
