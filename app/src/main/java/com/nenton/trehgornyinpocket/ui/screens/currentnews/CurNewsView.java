package com.nenton.trehgornyinpocket.ui.screens.currentnews;

import android.content.Context;
import android.util.AttributeSet;

import com.nenton.trehgornyinpocket.di.DaggerService;
import com.nenton.trehgornyinpocket.mvp.views.AbstractView;

public class CurNewsView extends AbstractView<CurNewsScreen.CurNewsPresenter> {
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
}
