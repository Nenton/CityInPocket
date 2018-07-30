package com.nenton.trehgornyinpocket.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.nenton.trehgornyinpocket.BuildConfig;
import com.nenton.trehgornyinpocket.R;
import com.nenton.trehgornyinpocket.data.network.intentservices.AnnouncementsIntentService;
import com.nenton.trehgornyinpocket.data.network.intentservices.NewsIntentService;
import com.nenton.trehgornyinpocket.data.network.intentservices.OrganizationsIntentService;
import com.nenton.trehgornyinpocket.data.network.intentservices.WeatherIntentService;
import com.nenton.trehgornyinpocket.di.DaggerService;
import com.nenton.trehgornyinpocket.di.components.AppComponent;
import com.nenton.trehgornyinpocket.di.modules.PicassoCacheModule;
import com.nenton.trehgornyinpocket.di.modules.RootModule;
import com.nenton.trehgornyinpocket.di.sqopes.RootScope;
import com.nenton.trehgornyinpocket.flow.TreeKeyDispatcher;
import com.nenton.trehgornyinpocket.mvp.presenters.MenuItemHolder;
import com.nenton.trehgornyinpocket.mvp.presenters.RootPresenter;
import com.nenton.trehgornyinpocket.mvp.views.IActionBarView;
import com.nenton.trehgornyinpocket.mvp.views.IRootView;
import com.nenton.trehgornyinpocket.mvp.views.IView;
import com.nenton.trehgornyinpocket.ui.screens.annoncements.AnnouncementsScreen;
import com.nenton.trehgornyinpocket.ui.screens.dirorganization.DirOrganizationsScreen;
import com.nenton.trehgornyinpocket.ui.screens.news.NewsScreen;
import com.nenton.trehgornyinpocket.ui.screens.weather.WeatherScreen;
import com.nenton.trehgornyinpocket.utils.UpdateType;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import flow.Flow;
import mortar.MortarScope;
import mortar.bundler.BundleServiceRunner;

public class RootActivity extends AppCompatActivity implements IRootView, IActionBarView, NavigationView.OnNavigationItemSelectedListener {
    @Inject
    RootPresenter mRootPresenter;
    @BindView(R.id.root_frame)
    FrameLayout mFrameContainer;
    @BindView(R.id.appbar)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.drawer_root)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;
    @BindView(R.id.nav_root_view)
    NavigationView mNavigationView;
    @BindView(R.id.wrap_container_pb)
    FrameLayout mWrapProgressbar;

    private ActionBarDrawerToggle mToggle;
    private ActionBar mActionBar;
    private List<MenuItemHolder> mActionBarMenuItems;

    private Runnable runnable = () -> {
        mWrapProgressbar.setVisibility(View.GONE);
        showMessage("Превышен лимит ожидания. Попробуйте позже");
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);
        BundleServiceRunner.getBundleServiceRunner(this).onCreate(savedInstanceState);
        ButterKnife.bind(this);
        RootComponent rootComponent = DaggerService.getDaggerComponent(this);
        rootComponent.inject(this);
        mRootPresenter.takeView(this);
        initToolbar();
        startUpdateService(UpdateType.ALL_UPDATE);
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open_drawer, R.string.close_drawer);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onDestroy() {
        mRootPresenter.dropView(this);
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        BundleServiceRunner.getBundleServiceRunner(this).onSaveInstanceState(outState);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        newBase = Flow.configure(newBase, this)
                .defaultKey(new NewsScreen())
                .dispatcher(new TreeKeyDispatcher(this))
                .install();
        super.attachBaseContext(newBase);
    }

    @Override
    public Object getSystemService(String name) {
        MortarScope rootActivityScope = MortarScope.findChild(getApplicationContext(), RootActivity.class.getName());
        return rootActivityScope.hasService(name) ? rootActivityScope.getService(name) : super.getSystemService(name);
    }

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showError(Throwable e) {
        Crashlytics.logException(e);
        if (BuildConfig.DEBUG) {
            showMessage(e.getMessage());
            Log.e(RootActivity.class.getName(), "Error", e);
        } else {
            showMessage("Что-то пошло не так. Попробуйте повторить позже");
        }
    }

    @Override
    public void showLoad() {
        if (mWrapProgressbar != null) {
            mWrapProgressbar.setVisibility(View.VISIBLE);
            mWrapProgressbar.postDelayed(runnable, 10000);
        }
    }

    @Override
    public void hideLoad() {
        if (mWrapProgressbar != null) {
            mWrapProgressbar.setVisibility(View.GONE);
            mWrapProgressbar.removeCallbacks(runnable);
        }
    }

    @Override
    public void setVisibleToolbar(boolean visible) {
        if (visible) {
            mActionBar.show();
        } else {
            mActionBar.hide();
        }
    }

    @Override
    public void setBackArrow(boolean enabled) {
        if (mToggle != null && mActionBar != null) {
            if (enabled) {
                mToggle.setDrawerIndicatorEnabled(false);
                mActionBar.setDisplayHomeAsUpEnabled(true);
                if (mToggle.getToolbarNavigationClickListener() == null) {
                    mToggle.setToolbarNavigationClickListener(v -> onBackPressed());
                }
            } else {
                mToggle.setDrawerIndicatorEnabled(true);
                mActionBar.setDisplayHomeAsUpEnabled(false);
                mToggle.setToolbarNavigationClickListener(null);
            }

            mDrawerLayout.setDrawerLockMode(enabled ? DrawerLayout.LOCK_MODE_LOCKED_CLOSED : DrawerLayout.LOCK_MODE_UNLOCKED);
            mToggle.syncState();
        }
    }

    @Override
    public void setMenuItem(List<MenuItemHolder> items) {
        mActionBarMenuItems = items;
        supportInvalidateOptionsMenu();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (mActionBarMenuItems != null && !mActionBarMenuItems.isEmpty()) {
            for (MenuItemHolder menuItem : mActionBarMenuItems) {
                if (menuItem.getMenuItemSearch() != null) {
                    getMenuInflater().inflate(R.menu.search_menu, menu);
                    MenuItem searchItem = menu.findItem(R.id.search);
                    SearchView searchView = (SearchView) searchItem.getActionView();
                    if (menuItem.getMenuItemSearch().getQuery() != null
                            && !menuItem.getMenuItemSearch().getQuery().isEmpty()) {
                        searchItem.expandActionView();
                        searchView.setQuery(menuItem.getMenuItemSearch().getQuery(), true);
                    }
                    searchView.setQueryHint(menuItem.getMenuItemSearch().getHint());
                    searchItem.setOnActionExpandListener(menuItem.getMenuItemSearch().getExpandListener());
                    searchView.setOnQueryTextListener(menuItem.getMenuItemSearch().getQueryListener());
                } else {
                    MenuItem item = menu.add(menuItem.getTitle());
                    item.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS)
                            .setIcon(menuItem.getIconResId())
                            .setOnMenuItemClickListener(menuItem.getListener());
                }
            }
        } else {
            menu.clear();
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void setTabLayout(ViewPager pager) {
        TabLayout tabView = new TabLayout(this);
        tabView.setupWithViewPager(pager);
        mAppBarLayout.addView(tabView);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabView));
    }

    @Override
    public void removeTabLayout() {
        View tabView = mAppBarLayout.getChildAt(1);
        if (tabView instanceof TabLayout) {
            mAppBarLayout.removeView(tabView);
        }
    }

    @Nullable
    @Override
    public IView getCurrentScreen() {
        return (IView) mFrameContainer.getChildAt(0);
    }

    @Override
    public void startUpdateService(UpdateType typeUpdate) {
        switch (typeUpdate) {
            case ALL_UPDATE:
                startService(NewsIntentService.class);
                startService(AnnouncementsIntentService.class);
                startService(OrganizationsIntentService.class);
                startService(WeatherIntentService.class);
                break;
            case NEWS_UPDATE:
                startService(NewsIntentService.class);
                break;
            case WEATHER_UPDATE:
                startService(WeatherIntentService.class);
                break;
            case ANNOUNCEMENTS_UPDATE:
                startService(AnnouncementsIntentService.class);
                break;
            case ORGANIZATIONS_UPDATE:
                startService(OrganizationsIntentService.class);
                break;
        }
    }

    private void startService(Class aClass) {
        Intent intent = new Intent(this, aClass);
        startService(intent);
    }

    @Override
    public void onBackPressed() {
        if (getCurrentScreen() != null && !getCurrentScreen().viewOnBackPressed() && !Flow.get(this).goBack()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Выход")
                    .setPositiveButton("Да", (dialog, which) -> super.onBackPressed())
                    .setNegativeButton("Нет", (dialog, which) -> dialog.cancel())
                    .setMessage("Вы действительно хотите выйти?")
                    .show();
        }
    }

    public boolean isAllGranted(@NonNull String[] permissions, boolean allGranted) {
        for (String permission : permissions) {
            int selfPermission = ContextCompat.checkSelfPermission((this), permission);
            if (selfPermission != PackageManager.PERMISSION_GRANTED) {
                allGranted = false;
                break;
            }
        }
        return allGranted;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mRootPresenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Object key = null;
        switch (item.getItemId()) {
            case R.id.nav_news:
                key = new NewsScreen();
                break;
            case R.id.nav_announcement:
                key = new AnnouncementsScreen();
                break;
            case R.id.nav_organizations:
                key = new DirOrganizationsScreen();
                break;
            case R.id.nav_weather:
                key = new WeatherScreen();
                break;
        }

        if (key != null) {
            Flow.get(this).set(key);
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    //region ========================= DI =========================

    @dagger.Component(dependencies = AppComponent.class, modules = {RootModule.class, PicassoCacheModule.class})
    @RootScope
    public interface RootComponent {
        void inject(RootActivity rootActivity);

        void inject(RootPresenter rootPresenter);

        RootPresenter getRootPresenter();

        Picasso getPicasso();
    }
    //endregion
}
