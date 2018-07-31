package com.nenton.trehgornyinpocket.utils;

import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;

public interface Playable {
    MediaSessionCompat getMediaSession();

    PlaybackStateCompat.Builder getStatePlayback();
}
