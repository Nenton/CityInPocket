package com.nenton.trehgornyinpocket.mvp.presenters;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import com.nenton.trehgornyinpocket.data.storage.dto.ActivityResultDto;
import com.nenton.trehgornyinpocket.di.DaggerService;
import com.nenton.trehgornyinpocket.mvp.views.IRootView;
import com.nenton.trehgornyinpocket.ui.activities.RootActivity;

import java.util.ArrayList;
import java.util.List;

import mortar.MortarScope;
import mortar.Presenter;
import mortar.bundler.BundleService;
import rx.subjects.PublishSubject;

public class RootPresenter extends Presenter<IRootView> {

    private PublishSubject<ActivityResultDto> mActivityResultSubject = PublishSubject.create();

    private static int DEFAULT_MODE = 0;
    private static int TAB_MODE = 1;

    @Override
    protected BundleService extractBundleService(IRootView view) {
        return BundleService.getBundleService((RootActivity) view);
    }

    public PublishSubject<ActivityResultDto> getActivityResultSubject() {
        return mActivityResultSubject;
    }

    @Override
    protected void onEnterScope(MortarScope scope) {
        super.onEnterScope(scope);
        RootActivity.RootComponent component = scope.getService(DaggerService.SERVICE_NAME);
        component.inject(this);
    }

    @Nullable
    public IRootView getRootView() {
        return getView();
    }

    public ActionBarBuilder newActionBarBuilder() {
        return this.new ActionBarBuilder();
    }

    public boolean checkPermissionsAndRequestIfNotGranted(@NonNull String[] permissions, int requestCode) {
        boolean allGranted = true;
        allGranted = ((RootActivity) getView()).isAllGranted(permissions, allGranted);

        if (!allGranted) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ((RootActivity) getView()).requestPermissions(permissions, requestCode);
            }
            return false;
        }
        return allGranted;
    }


    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        mActivityResultSubject.onNext(new ActivityResultDto(requestCode, resultCode, intent));
    }

    public void onRequestPermissionResult(int requetCode, @NonNull String[] permissions, @NonNull int[] grantResult) {
        throw new UnsupportedOperationException();
    }

    public class ActionBarBuilder {
        private boolean isGoBack = false;
        private boolean isVisable = true;
        private CharSequence title;
        private List<MenuItemHolder> items = new ArrayList<>();
        private ViewPager pager;
        private int toolbarMode = DEFAULT_MODE;

        public ActionBarBuilder setBackArrow(boolean enable) {
            this.isGoBack = enable;
            return this;
        }

        public ActionBarBuilder setVisibleToolbar(boolean visibale) {
            this.isVisable = visibale;
            return this;
        }

        public ActionBarBuilder addAction(MenuItemHolder menuItem) {
            this.items.add(menuItem);
            return this;
        }

        public ActionBarBuilder setTab(ViewPager pager) {
            this.toolbarMode = TAB_MODE;
            this.pager = pager;
            return this;
        }

        public ActionBarBuilder setTitle(CharSequence title) {
            this.title = title;
            return this;
        }

        public void build() {
            if (getRootView() != null) {
                RootActivity activity = (RootActivity) getRootView();
                activity.removeTabLayout();
                activity.setVisibleToolbar(isVisable);
                activity.setTitle(title);
                activity.setBackArrow(isGoBack);
                activity.setMenuItem(items);
                if (toolbarMode == TAB_MODE) {
                    activity.setTabLayout(pager);
                } else {
                    activity.removeTabLayout();
                }
            }
        }
    }
}