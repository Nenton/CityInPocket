package com.nenton.trehgornyinpocket.ui.screens.curannoncement;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.TextView;

import com.nenton.trehgornyinpocket.R;
import com.nenton.trehgornyinpocket.data.storage.dto.AnnouncementDto;
import com.nenton.trehgornyinpocket.di.DaggerService;
import com.nenton.trehgornyinpocket.mvp.views.AbstractView;

import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;

public class CurAnnouncementView extends AbstractView<CurAnnouncementScreen.CurAnnouncementPresenter> {
    @BindView(R.id.announcement_day_tv)
    TextView announcementDateDay;
    @BindView(R.id.announcement_title_tv)
    TextView announcementTitle;
    @BindView(R.id.announcement_month_tv)
    TextView announcementDateMounth;
    @BindView(R.id.announcement_full_tv)
    TextView announcementDescription;
    @BindView(R.id.announcement_photos)
    ViewPager viewPager;

    private CurAnnouncementAdapter adapter;

    public CurAnnouncementView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initDagger(Context context) {
        DaggerService.<CurAnnouncementScreen.Component>getDaggerComponent(context).inject(this);
    }

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }

    public void initView(AnnouncementDto announcement) {
        announcementTitle.setText(announcement.getTitle());
        announcementDescription.setText(announcement.getDescription());
        SimpleDateFormat formatDay = new SimpleDateFormat("dd", Locale.ENGLISH);
        announcementDateDay.setText(formatDay.format(announcement.getDate()));
        SimpleDateFormat formatMonth = new SimpleDateFormat("MMMM", Locale.ENGLISH);
        announcementDateMounth.setText(formatMonth.format(announcement.getDate()));

        if (announcement.getImagesUrl() == null || announcement.getImagesUrl().isEmpty()) {
            viewPager.setVisibility(GONE);
        } else {
            adapter = new CurAnnouncementAdapter();
            viewPager.setAdapter(adapter);
            for (String url : announcement.getImagesUrl()) {
                adapter.addUrl(url);
            }
        }
    }
}
