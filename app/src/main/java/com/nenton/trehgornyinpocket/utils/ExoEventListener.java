package com.nenton.trehgornyinpocket.utils;

import android.support.v4.media.session.PlaybackStateCompat;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;

public class ExoEventListener extends Player.DefaultEventListener {
    private final Playable playable;
    private SimpleExoPlayer mExoPlayer;

    public ExoEventListener(SimpleExoPlayer exoPlayer, Playable playable) {
        this.mExoPlayer = exoPlayer;
        this.playable = playable;
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {
        // Do nothing
    }

    @Override
    public void onPositionDiscontinuity(int reason) {
        // Do nothing
    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
        // Do nothing
    }

    @Override
    public void onSeekProcessed() {
        // Do nothing
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {
        // Do nothing
    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
        // Do nothing
    }

    @Override
    public void onLoadingChanged(boolean isLoading) {
        // Do nothing
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if ((playbackState == ExoPlayer.STATE_READY) && playWhenReady) {
            playable.getStatePlayback().setState(PlaybackStateCompat.STATE_PLAYING, mExoPlayer.getCurrentPosition(), 1f);
        } else if ((playbackState == ExoPlayer.STATE_READY)) {
            playable.getStatePlayback().setState(PlaybackStateCompat.STATE_PAUSED, mExoPlayer.getCurrentPosition(), 1f);
        }
        playable.getMediaSession().setPlaybackState(playable.getStatePlayback().build());
    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {
        // Do nothing
    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {
        // Do nothing
    }
}
