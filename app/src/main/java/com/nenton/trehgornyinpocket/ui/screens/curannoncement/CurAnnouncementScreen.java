package com.nenton.trehgornyinpocket.ui.screens.curannoncement;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.media.session.MediaSessionCompat;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.nenton.trehgornyinpocket.R;
import com.nenton.trehgornyinpocket.data.storage.room.AnnouncementEntity;
import com.nenton.trehgornyinpocket.di.DaggerService;
import com.nenton.trehgornyinpocket.di.sqopes.DaggerScope;
import com.nenton.trehgornyinpocket.flow.AbstractScreen;
import com.nenton.trehgornyinpocket.flow.Screen;
import com.nenton.trehgornyinpocket.mvp.models.CurAnnouncementModel;
import com.nenton.trehgornyinpocket.mvp.presenters.AbstractPresenter;
import com.nenton.trehgornyinpocket.mvp.presenters.RootPresenter;
import com.nenton.trehgornyinpocket.ui.activities.RootActivity;
import com.nenton.trehgornyinpocket.utils.ExoEventListener;
import com.nenton.trehgornyinpocket.utils.Playable;
import com.squareup.picasso.Picasso;

import dagger.Provides;
import mortar.MortarScope;

@Screen(R.layout.screen_cur_announcements)
public class CurAnnouncementScreen extends AbstractScreen<RootActivity.RootComponent> {
    private AnnouncementEntity announcement;

    public CurAnnouncementScreen(AnnouncementEntity announcement) {
        this.announcement = announcement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        CurAnnouncementScreen that = (CurAnnouncementScreen) o;

        return announcement != null ? announcement.equals(that.announcement) : that.announcement == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (announcement != null ? announcement.hashCode() : 0);
        return result;
    }

    @Override
    public Object createScreenComponent(RootActivity.RootComponent parentComponent) {
        return DaggerCurAnnouncementScreen_Component.builder()
                .rootComponent(parentComponent)
                .module(new Module())
                .build();
    }

    @dagger.Module
    public class Module {
        @Provides
        @DaggerScope(CurAnnouncementScreen.class)
        CurAnnouncementModel provideCurAnnouncementModel() {
            return new CurAnnouncementModel();
        }

        @Provides
        @DaggerScope(CurAnnouncementScreen.class)
        CurAnnouncementPresenter provideCurAnnouncementPresenter() {
            return new CurAnnouncementPresenter();
        }
    }

    @dagger.Component(dependencies = RootActivity.RootComponent.class, modules = Module.class)
    @DaggerScope(CurAnnouncementScreen.class)
    public interface Component {
        void inject(CurAnnouncementPresenter presenter);

        void inject(CurAnnouncementView view);

        void inject(CurAnnouncementAdapter adapter);

        Picasso getPicasso();

        RootPresenter getRootPresenter();
    }

    public class CurAnnouncementPresenter extends AbstractPresenter<CurAnnouncementView, CurAnnouncementModel> {
        private static final String POSITION_PLAYER_KEY = "POSITION_PLAYER_KEY";
        private static final String PLAY_KEY = "PLAY_KEY";

        private SimpleExoPlayer mExoPlayer;

        @Override
        protected void initActionBar() {
            mRootPresenter.newActionBarBuilder()
                    .setTitle("Announcement")
                    .setBackArrow(true)
                    .build();
        }

        @Override
        protected void initDagger(MortarScope scope) {
            Component component = scope.getService(DaggerService.SERVICE_NAME);
            component.inject(this);
        }

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);
            if (getView() != null) {
                getView().initView(announcement);
                if (announcement.getVideoUrl() != null && !announcement.getVideoUrl().isEmpty()) {
                    initPlayer();
                    loadMediaSource();
                    getView().chainPlayer(mExoPlayer);

                    if (savedInstanceState != null) {
                        mExoPlayer.seekTo(savedInstanceState.getLong(POSITION_PLAYER_KEY));
                        mExoPlayer.setPlayWhenReady(savedInstanceState.getBoolean(PLAY_KEY));
                    }
                } else {
                    getView().hidePlayer();
                }
            }
        }

        @Override
        protected void onSave(Bundle outState) {
            super.onSave(outState);
            outState.putLong(POSITION_PLAYER_KEY, mExoPlayer.getCurrentPosition());
            outState.putBoolean(PLAY_KEY, mExoPlayer.getPlayWhenReady());
        }

        private void releasePlayer() {
            if (mExoPlayer != null) {
                mExoPlayer.stop();
                mExoPlayer.release();
                mExoPlayer = null;
            }
        }

        private void initPlayer() {
            if (mExoPlayer == null && getRootView() != null && getView() != null) {
                Playable playable = (Playable) getRootView();
                playable.getMediaSession().setCallback(new MySessionCallback());
                mExoPlayer = ExoPlayerFactory.newSimpleInstance(getView().getContext(), new DefaultTrackSelector());
                mExoPlayer.addListener(new ExoEventListener(mExoPlayer, playable));
            }
        }

        public void eventDetachedView() {
            if (mExoPlayer != null) {
                mExoPlayer.setPlayWhenReady(false);
                releasePlayer();
            }
        }

        @Override
        protected void onExitScope() {
            if (mExoPlayer != null) {
                mExoPlayer.setPlayWhenReady(false);
                releasePlayer();
            }
            if (getRootView() != null) {
                ((Playable) getRootView()).getMediaSession().setActive(false);
            }
            super.onExitScope();
        }

        private void loadMediaSource() {
            if (getView() != null) {
                String cityInPocket = Util.getUserAgent(getView().getContext(), "cityInPocket");
                DefaultDataSourceFactory factory = new DefaultDataSourceFactory(getView().getContext(), cityInPocket, new DefaultBandwidthMeter());
                MediaSource mediaSource = new ExtractorMediaSource.Factory(factory).createMediaSource(Uri.parse(announcement.getVideoUrl()));
                mExoPlayer.prepare(mediaSource);
            }
        }

        private class MySessionCallback extends MediaSessionCompat.Callback {
            @Override
            public void onPlay() {
                mExoPlayer.setPlayWhenReady(true);
            }

            @Override
            public void onPause() {
                mExoPlayer.setPlayWhenReady(false);
            }

            @Override
            public void onSkipToPrevious() {
                mExoPlayer.seekTo(0);
            }
        }
    }
}
