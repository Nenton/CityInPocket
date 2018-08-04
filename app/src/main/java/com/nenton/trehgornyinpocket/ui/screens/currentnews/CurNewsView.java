package com.nenton.trehgornyinpocket.ui.screens.currentnews;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.TextView;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.nenton.trehgornyinpocket.R;
import com.nenton.trehgornyinpocket.data.storage.room.NewsEntity;
import com.nenton.trehgornyinpocket.di.DaggerService;
import com.nenton.trehgornyinpocket.mvp.views.AbstractView;
import com.nenton.trehgornyinpocket.utils.ViewHelper;

import butterknife.BindView;

public class CurNewsView extends AbstractView<CurNewsScreen.CurNewsPresenter> {
    @BindView(R.id.cur_news_title)
    TextView titleView;
    @BindView(R.id.cur_news_description)
    TextView descriptionView;
    @BindView(R.id.cur_news_photos)
    ViewPager photosViews;
    @BindView(R.id.cur_news_date)
    TextView date;
    @BindView(R.id.cur_news_exo_player)
    SimpleExoPlayerView exoPlayerView;

    private CurNewsAdapter adapter;

    public CurNewsAdapter getAdapter() {
        return adapter;
    }

    public CurNewsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initDagger(Context context) {
        DaggerService.<CurNewsScreen.Component>getDaggerComponent(context).inject(this);
    }

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }

    @Override
    protected void onDetachedFromWindow() {
        mPresenter.eventDetachedView();
        super.onDetachedFromWindow();
    }

    public void initView(NewsEntity currentNews) {
        titleView.setText(currentNews.getTitle());
        descriptionView.setText(currentNews.getDescription());
        date.setText(ViewHelper.getDateFromPattern(currentNews.getDate()));

        if (currentNews.getImagesUrl() == null || currentNews.getImagesUrl().isEmpty()) {
            photosViews.setVisibility(GONE);
        } else {
            adapter = new CurNewsAdapter();
            photosViews.setAdapter(adapter);
            for (String urlPhotos : currentNews.getImagesUrl()) {
                adapter.addUrl(urlPhotos);
            }
        }
    }

    public void chainPlayer(SimpleExoPlayer exoPlayer) {
        exoPlayerView.setPlayer(exoPlayer);
    }

    public void hidePlayer() {
        exoPlayerView.setVisibility(GONE);
    }
}
