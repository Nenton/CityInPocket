package com.nenton.trehgornyinpocket.ui.screens.currentnews;

import android.os.Bundle;

import com.nenton.trehgornyinpocket.R;
import com.nenton.trehgornyinpocket.data.storage.room.NewsEntity;
import com.nenton.trehgornyinpocket.di.DaggerService;
import com.nenton.trehgornyinpocket.di.sqopes.DaggerScope;
import com.nenton.trehgornyinpocket.flow.AbstractScreen;
import com.nenton.trehgornyinpocket.flow.Screen;
import com.nenton.trehgornyinpocket.mvp.models.CurNewsModel;
import com.nenton.trehgornyinpocket.mvp.presenters.AbstractPresenter;
import com.nenton.trehgornyinpocket.mvp.presenters.RootPresenter;
import com.nenton.trehgornyinpocket.ui.activities.RootActivity;
import com.squareup.picasso.Picasso;

import dagger.Provides;
import mortar.MortarScope;

@Screen(R.layout.screen_cur_news)
public class CurNewsScreen extends AbstractScreen<RootActivity.RootComponent> {
    private final NewsEntity currentNews;

    public CurNewsScreen(NewsEntity currentNew) {
        this.currentNews = currentNew;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CurNewsScreen that = (CurNewsScreen) o;
        return currentNews.equals(that.currentNews);
    }

    @Override
    public int hashCode() {
        return super.hashCode() + currentNews.hashCode();
    }

    @Override
    public Object createScreenComponent(RootActivity.RootComponent parentComponent) {
        return DaggerCurNewsScreen_Component.builder()
                .rootComponent(parentComponent)
                .module(new Module())
                .build();
    }

    @dagger.Module
    public class Module {
        @Provides
        @DaggerScope(CurNewsScreen.class)
        CurNewsModel provideCurNewsModel() {
            return new CurNewsModel();
        }

        @Provides
        @DaggerScope(CurNewsScreen.class)
        CurNewsPresenter provideCurNewsPresenter() {
            return new CurNewsPresenter();
        }
    }

    @dagger.Component(dependencies = RootActivity.RootComponent.class, modules = Module.class)
    @DaggerScope(CurNewsScreen.class)
    public interface Component {
        void inject(CurNewsPresenter presenter);

        void inject(CurNewsView view);

        void inject(CurNewsAdapter adapter);

        Picasso getPicasso();

        RootPresenter getRootPresenter();
    }

    public class CurNewsPresenter extends AbstractPresenter<CurNewsView, CurNewsModel> {

        @Override
        protected void initActionBar() {
            mRootPresenter.newActionBarBuilder()
                    .setTitle("News")
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
                getView().initView(currentNews);
            }
        }
    }
}
