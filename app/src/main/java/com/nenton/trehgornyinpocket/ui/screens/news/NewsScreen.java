package com.nenton.trehgornyinpocket.ui.screens.news;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.SearchView;
import android.view.MenuItem;

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

import java.util.List;

import dagger.Provides;
import flow.Flow;
import mortar.MortarScope;
import rx.Subscriber;

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
            loadData();
        }

        private void loadData() {
            if (getView() != null) {
                List<NewsDto> newsMock = mModel.getNewsMock();
                getView().getAdapter().swapAdapter(newsMock);
            }
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
            // TODO: 22.07.2018 Стандартный возврат всех данных
            query = "";
            return true;
        }

        private void showNews(final String q, int delay) {
            Runnable runnable = () -> {
                query = q;
                getRootView().showMessage("Search...");
                // TODO: 22.07.2018 Реализовать получение данных с поисковым запросом
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
                    // TODO: 22.07.2018 Add Firebase Crash
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
