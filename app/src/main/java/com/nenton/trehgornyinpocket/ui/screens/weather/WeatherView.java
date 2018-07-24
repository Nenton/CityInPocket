package com.nenton.trehgornyinpocket.ui.screens.weather;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.nenton.trehgornyinpocket.R;
import com.nenton.trehgornyinpocket.di.DaggerService;
import com.nenton.trehgornyinpocket.mvp.views.AbstractView;

import butterknife.BindView;

public class WeatherView extends AbstractView<WeatherScreen.WeatherPresenter> {
    @BindView(R.id.weather_rv)
    RecyclerView weatherRecyclerView;
    private WeatherAdapter adapter;

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

    @Override
    protected void afterInflate() {
        if (adapter == null) {
            adapter = new WeatherAdapter();
        }

        weatherRecyclerView.setAdapter(adapter);
    }

    public WeatherAdapter getAdapter() {
        return adapter;
    }
}
