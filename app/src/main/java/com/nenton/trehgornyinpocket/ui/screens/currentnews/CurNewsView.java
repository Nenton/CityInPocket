package com.nenton.trehgornyinpocket.ui.screens.currentnews;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.TextView;

import com.nenton.trehgornyinpocket.R;
import com.nenton.trehgornyinpocket.data.storage.dto.NewsDto;
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

    public void initView(NewsDto currentNews) {
        titleView.setText(currentNews.getDescription());
        descriptionView.setText(currentNews.getFullNew());
        date.setText(ViewHelper.getDateFromPattern(currentNews.getDate()));

        if (currentNews.getImagesUrl() == null || currentNews.getImagesUrl().isEmpty()) {
            photosViews.setVisibility(GONE);
        } else {
            adapter = new CurNewsAdapter();
            photosViews.setAdapter(adapter);
            for (String urlPhotos : currentNews.getImagesUrl()) {
                adapter.addUrl(urlPhotos);
            }
            adapter.addUrl("https://avatars.mds.yandex.net/get-pdb/812271/1934c8a2-a8f3-4b18-8ed5-7683e9842bfb/s1200");
            adapter.addUrl("https://avatars.mds.yandex.net/get-pdb/70729/6b068f73-2c77-4d10-927e-9fd5b2ee2302/s1200");
        }
    }
}
