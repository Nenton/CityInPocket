package com.nenton.trehgornyinpocket.ui.screens.weather;

import com.nenton.trehgornyinpocket.R;
import com.nenton.trehgornyinpocket.di.DaggerService;
import com.nenton.trehgornyinpocket.di.sqopes.DaggerScope;
import com.nenton.trehgornyinpocket.flow.AbstractScreen;
import com.nenton.trehgornyinpocket.flow.Screen;
import com.nenton.trehgornyinpocket.mvp.presenters.AbstractPresenter;
import com.nenton.trehgornyinpocket.mvp.presenters.RootPresenter;
import com.nenton.trehgornyinpocket.ui.activities.RootActivity;
import com.squareup.picasso.Picasso;

import dagger.Provides;
import mortar.MortarScope;

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
            ((Component) scope.getService(DaggerService.SERVICE_NAME)).inject(this);
        }
    }
}
