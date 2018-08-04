package com.nenton.trehgornyinpocket.ui.screens.weather;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.nenton.trehgornyinpocket.R;
import com.nenton.trehgornyinpocket.di.DaggerService;
import com.nenton.trehgornyinpocket.mvp.views.AbstractView;

import butterknife.BindView;

public class WeatherView extends AbstractView<WeatherScreen.WeatherPresenter> {
    @BindView(R.id.weather_rv)
    RecyclerView weatherRecyclerView;
    @BindView(R.id.weather_swipe_srl)
    SwipeRefreshLayout swipeRefreshLayout;
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

        swipeRefreshLayout.setOnRefreshListener(() -> {
            mPresenter.swipeUpdate();
            swipeRefreshLayout.postDelayed(() -> swipeRefreshLayout.setRefreshing(false), 1500);
        });
    }

    public WeatherAdapter getAdapter() {
        return adapter;
    }
}
