package com.nenton.trehgornyinpocket.ui.screens.dirorganization;

import android.content.Context;
import android.util.AttributeSet;

import com.nenton.trehgornyinpocket.di.DaggerService;
import com.nenton.trehgornyinpocket.mvp.views.AbstractView;
import com.nenton.trehgornyinpocket.ui.screens.currentnews.CurNewsScreen;

public class DirOrganizationsView extends AbstractView<DirOrganizationsScreen.DirOrganizationsPresenter> {
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
}
