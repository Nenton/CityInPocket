package com.nenton.trehgornyinpocket.ui.screens.news;

import android.os.Bundle;

import com.nenton.trehgornyinpocket.R;
import com.nenton.trehgornyinpocket.data.storage.dto.NewsDto;
import com.nenton.trehgornyinpocket.di.DaggerService;
import com.nenton.trehgornyinpocket.di.sqopes.DaggerScope;
import com.nenton.trehgornyinpocket.flow.AbstractScreen;
import com.nenton.trehgornyinpocket.flow.Screen;
import com.nenton.trehgornyinpocket.mvp.presenters.AbstractPresenter;
import com.nenton.trehgornyinpocket.mvp.presenters.RootPresenter;
import com.nenton.trehgornyinpocket.ui.activities.RootActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dagger.Provides;
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

    public class NewsPresenter extends AbstractPresenter<NewsView, NewsModel> {

        @Override
        protected void initActionBar() {
            mRootPresenter.newActionBarBuilder()
                    .setTitle("News")
                    .build();
        }

        @Override
        protected void initDagger(MortarScope scope) {
            ((Component) scope.getService(DaggerService.SERVICE_NAME)).inject(this);
        }

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);
            loadData();
        }

        private void loadData() {
            if (getView() != null) {
                List<NewsDto> news = new ArrayList<>();

                List<String> images = new ArrayList<>();
                images.add("https://avatars.mds.yandex.net/get-pdb/25978/a6b1eff3-6894-434c-947e-cc0b69309565/s1200");
                news.add(new NewsDto("First", "FIRST NEW", new Date(System.currentTimeMillis()), images, ""));

                images = new ArrayList<>();
                images.add("https://avatars.mds.yandex.net/get-pdb/812271/1934c8a2-a8f3-4b18-8ed5-7683e9842bfb/s1200");
                news.add(new NewsDto("Second", "SECOND NEW", new Date(System.currentTimeMillis()), images, ""));

                images = new ArrayList<>();
                images.add("https://avatars.mds.yandex.net/get-pdb/70729/6b068f73-2c77-4d10-927e-9fd5b2ee2302/s1200");
                news.add(new NewsDto("Third", "THIRD NEW", new Date(System.currentTimeMillis()), images, ""));

                getView().getAdapter().swapAdapter(news);
            }
        }
    }
}
