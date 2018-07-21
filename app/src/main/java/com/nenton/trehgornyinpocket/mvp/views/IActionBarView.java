package com.nenton.trehgornyinpocket.mvp.views;

import android.support.v4.view.ViewPager;

import com.nenton.trehgornyinpocket.mvp.presenters.MenuItemHolder;

import java.util.List;

public interface IActionBarView {
    void setTitle(CharSequence title);
    void setVisibleToolbar(boolean visible);
    void setBackArrow(boolean enabled);
    void setMenuItem(List<MenuItemHolder> items);
    void setTabLayout(ViewPager pager);
    void removeTabLayout();
}
