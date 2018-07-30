package com.nenton.trehgornyinpocket.ui.screens.news;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.MenuItem;

import com.nenton.trehgornyinpocket.R;
import com.nenton.trehgornyinpocket.data.storage.room.NewsEntity;
import com.nenton.trehgornyinpocket.di.DaggerService;
import com.nenton.trehgornyinpocket.di.sqopes.DaggerScope;
import com.nenton.trehgornyinpocket.flow.AbstractScreen;
import com.nenton.trehgornyinpocket.flow.Screen;
import com.nenton.trehgornyinpocket.mvp.models.NewsModel;
import com.nenton.trehgornyinpocket.mvp.presenters.AbstractPresenter;
import com.nenton.trehgornyinpocket.mvp.presenters.MenuItemHolder;
import com.nenton.trehgornyinpocket.mvp.presenters.RootPresenter;
import com.nenton.trehgornyinpocket.ui.activities.RootActivity;
import com.nenton.trehgornyinpocket.ui.screens.currentnews.CurNewsScreen;
import com.nenton.trehgornyinpocket.utils.UpdateType;
import com.squareup.picasso.Picasso;

import java.util.List;

import dagger.Provides;
import flow.Flow;
import mortar.MortarScope;

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
            updateData(mModel.getNewsAllObs(((RootActivity) getRootView())));
        }

        public void clickOnNew(NewsEntity currentNew) {
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
            if (newText != null && !newText.isEmpty()) {
                showNews(newText, 2000);
                Log.e("onQueryTextChange", "Change text search");
                return true;
            }
            return false;
        }

        @Override
        public boolean onMenuItemActionExpand(MenuItem menuItem) {
            return true;
        }

        @Override
        public boolean onMenuItemActionCollapse(MenuItem menuItem) {
            updateData(mModel.getNewsAllObs(((RootActivity) getRootView())));
            query = "";
            return true;
        }

        private void updateData(LiveData<List<NewsEntity>> observable) {
            if (getRootView() != null && getView() != null) {
                observable.observe(((RootActivity) getRootView()),
                        new Observer<List<NewsEntity>>() {
                            @Override
                            public void onChanged(@Nullable List<NewsEntity> newsEntities) {
//                                observable.removeObserver(this);
                                getView().getAdapter().reloadAdapter(newsEntities);
                            }
                        });
            }
        }

        private void showNews(final String q, int delay) {
            Runnable runnable = () -> {
                query = "%" + q + "%";
                updateData(mModel.getNewsOnSearch(((RootActivity) getRootView()), query));
            };
            handler.removeCallbacksAndMessages(null);
            handler.postDelayed(runnable, delay);
        }

        public void swipeUpdate() {
            if (getRootView() != null) {
                getRootView().startUpdateService(UpdateType.NEWS_UPDATE);
            }
            if (query != null && !query.isEmpty()) {
                updateData(mModel.getNewsOnSearch(((RootActivity) getRootView()), query));
            } else {
                updateData(mModel.getNewsAllObs(((RootActivity) getRootView())));
            }
        }
    }
}
