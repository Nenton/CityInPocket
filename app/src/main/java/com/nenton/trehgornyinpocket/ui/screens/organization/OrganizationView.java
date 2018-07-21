package com.nenton.trehgornyinpocket.ui.screens.organization;

import android.content.Context;
import android.util.AttributeSet;

import com.nenton.trehgornyinpocket.di.DaggerService;
import com.nenton.trehgornyinpocket.mvp.views.AbstractView;

public class OrganizationView extends AbstractView<OrganizationScreen.OrganizationPresenter> {
    public OrganizationView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initDagger(Context context) {
        DaggerService.<OrganizationScreen.Component>getDaggerComponent(context).inject(this);
    }

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }
}
