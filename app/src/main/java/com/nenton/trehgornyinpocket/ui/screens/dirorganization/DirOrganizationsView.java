package com.nenton.trehgornyinpocket.ui.screens.dirorganization;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.nenton.trehgornyinpocket.R;
import com.nenton.trehgornyinpocket.di.DaggerService;
import com.nenton.trehgornyinpocket.mvp.views.AbstractView;

import butterknife.BindView;

public class DirOrganizationsView extends AbstractView<DirOrganizationsScreen.DirOrganizationsPresenter> {
    @BindView(R.id.organizations_rv)
    RecyclerView organizationsRecyclerView;
    @BindView(R.id.organizations_swipe_srl)
    SwipeRefreshLayout swipeRefreshLayout;

    private DirOrganizationsAdapter adapter;

    public DirOrganizationsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initDagger(Context context) {
        DaggerService.<DirOrganizationsScreen.Component>getDaggerComponent(context).inject(this);
    }

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }

    @Override
    protected void afterInflate() {
        if (adapter == null) {
            adapter = new DirOrganizationsAdapter();
        }
        organizationsRecyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            mPresenter.swipeUpdate();
            swipeRefreshLayout.postDelayed(() -> {
                swipeRefreshLayout.setRefreshing(false);
            }, 1500);
        });
    }

    public DirOrganizationsAdapter getAdapter() {
        return adapter;
    }
}
