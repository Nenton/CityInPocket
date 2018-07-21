package com.nenton.trehgornyinpocket.ui.screens.weather;

import android.content.Context;
import android.util.AttributeSet;

import com.nenton.trehgornyinpocket.di.DaggerService;
import com.nenton.trehgornyinpocket.mvp.views.AbstractView;

public class WeatherView extends AbstractView<WeatherScreen.WeatherPresenter> {
    public WeatherView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initDagger(Context context) {
        DaggerService.<WeatherScreen.Component>getDaggerComponent(context).inject(this);
    }

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }
}
