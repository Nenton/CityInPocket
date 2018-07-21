package com.nenton.trehgornyinpocket.ui.screens.annoncements;

import android.content.Context;
import android.util.AttributeSet;

import com.nenton.trehgornyinpocket.di.DaggerService;
import com.nenton.trehgornyinpocket.mvp.views.AbstractView;

public class AnnouncementsView extends AbstractView<AnnouncementsScreen.AnnouncementsPresenter> {
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
}
