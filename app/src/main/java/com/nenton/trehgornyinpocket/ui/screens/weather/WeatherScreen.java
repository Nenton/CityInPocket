package com.nenton.trehgornyinpocket.ui.screens.weather;

import android.os.Bundle;

import com.crashlytics.android.Crashlytics;
import com.nenton.trehgornyinpocket.R;
import com.nenton.trehgornyinpocket.data.storage.dto.WeatherDto;
import com.nenton.trehgornyinpocket.di.DaggerService;
import com.nenton.trehgornyinpocket.di.sqopes.DaggerScope;
import com.nenton.trehgornyinpocket.flow.AbstractScreen;
import com.nenton.trehgornyinpocket.flow.Screen;
import com.nenton.trehgornyinpocket.mvp.presenters.AbstractPresenter;
import com.nenton.trehgornyinpocket.mvp.presenters.RootPresenter;
import com.nenton.trehgornyinpocket.ui.activities.RootActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import dagger.Provides;
import mortar.MortarScope;
import rx.Subscriber;

@Screen(R.layout.screen_weather)
public class WeatherScreen extends AbstractScreen<RootActivity.RootComponent> {
    @Override
    public Object createScreenComponent(RootActivity.RootComponent parentComponent) {
        return DaggerWeatherScreen_Component.builder()
                .rootComponent(parentComponent)
                .module(new Module())
                .build();
    }

    @dagger.Module
    public class Module {
        @Provides
        @DaggerScope(WeatherScreen.class)
        WeatherModel provideWeatherModel() {
            return new WeatherModel();
        }

        @Provides
        @DaggerScope(WeatherScreen.class)
        WeatherPresenter provideWeatherPresenter() {
            return new WeatherPresenter();
        }
    }

    @dagger.Component(dependencies = RootActivity.RootComponent.class, modules = Module.class)
    @DaggerScope(WeatherScreen.class)
    public interface Component {
        void inject(WeatherPresenter presenter);

        void inject(WeatherView view);

        void inject(WeatherAdapter adapter);

        Picasso getPicasso();

        RootPresenter getRootPresenter();
    }

    public class WeatherPresenter extends AbstractPresenter<WeatherView, WeatherModel> {

        @Override
        protected void initActionBar() {
            mRootPresenter.newActionBarBuilder()
                    .setTitle("Weather")
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
            mCompSubs.add(mModel.getWeatherOnFourteenDays().subscribe(new DataSubscriber()));
        }

        private class DataSubscriber extends Subscriber<List<WeatherDto>> {
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
            public void onNext(List<WeatherDto> weathers) {
                if (getView() != null) {
                    WeatherAdapter adapter = getView().getAdapter();
                    adapter.reloadAdapter(weathers);
                }
            }
        }
    }
}
