package com.nenton.trehgornyinpocket.ui.screens.annoncements;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.nenton.trehgornyinpocket.R;
import com.nenton.trehgornyinpocket.di.DaggerService;
import com.nenton.trehgornyinpocket.mvp.views.AbstractView;

import butterknife.BindView;

public class AnnouncementsView extends AbstractView<AnnouncementsScreen.AnnouncementsPresenter> {
    @BindView(R.id.announcements_rv)
    RecyclerView announcementsRecyclerView;
    @BindView(R.id.announcements_swipe_srl)
    SwipeRefreshLayout swipeRefreshLayout;

    private AnnouncementsAdapter adapter;

    public AnnouncementsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initDagger(Context context) {
        DaggerService.<AnnouncementsScreen.Component>getDaggerComponent(context).inject(this);
    }

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }

    @Override
    protected void afterInflate() {
        if (adapter == null) {
            adapter = new AnnouncementsAdapter();
        }

        announcementsRecyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            mPresenter.swipeUpdate();
            swipeRefreshLayout.postDelayed(() -> {
                swipeRefreshLayout.setRefreshing(false);
            }, 1500);
        });
    }

    public AnnouncementsAdapter getAdapter() {
        return adapter;
    }
}
