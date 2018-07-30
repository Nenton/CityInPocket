package com.nenton.trehgornyinpocket.ui.screens.news;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.nenton.trehgornyinpocket.R;
import com.nenton.trehgornyinpocket.di.DaggerService;
import com.nenton.trehgornyinpocket.mvp.views.AbstractView;

import butterknife.BindView;

public class NewsView extends AbstractView<NewsScreen.NewsPresenter> {
    @BindView(R.id.news_rv)
    RecyclerView newsRecyclerView;
    @BindView(R.id.news_swipe_srl)
    SwipeRefreshLayout swipeRefreshLayout;

    private NewsAdapter adapter;

    public NewsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initDagger(Context context) {
        DaggerService.<NewsScreen.Component>getDaggerComponent(context).inject(this);
    }

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }

    @Override
    protected void afterInflate() {
        if (adapter == null) {
            adapter = new NewsAdapter();
        }
        newsRecyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            mPresenter.swipeUpdate();
            swipeRefreshLayout.postDelayed(() -> {
                swipeRefreshLayout.setRefreshing(false);
            }, 1500);
        });
    }

    public NewsAdapter getAdapter() {
        return adapter;
    }
}
