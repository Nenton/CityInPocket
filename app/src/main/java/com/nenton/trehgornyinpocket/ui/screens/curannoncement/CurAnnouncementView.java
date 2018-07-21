package com.nenton.trehgornyinpocket.ui.screens.curannoncement;

import android.content.Context;
import android.util.AttributeSet;

import com.nenton.trehgornyinpocket.di.DaggerService;
import com.nenton.trehgornyinpocket.mvp.views.AbstractView;

public class CurAnnouncementView extends AbstractView<CurAnnouncementScreen.CurAnnouncementPresenter> {
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
}
