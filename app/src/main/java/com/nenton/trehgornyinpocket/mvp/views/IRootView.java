package com.nenton.trehgornyinpocket.mvp.views;

import android.support.annotation.Nullable;

import com.nenton.trehgornyinpocket.utils.UpdateType;

public interface IRootView extends IView{
    void showMessage(String message);
    void showError(Throwable e);

    void showLoad();
    void hideLoad();

    @Nullable
    IView getCurrentScreen();

    void startUpdateService(UpdateType typeUpdate);
}
