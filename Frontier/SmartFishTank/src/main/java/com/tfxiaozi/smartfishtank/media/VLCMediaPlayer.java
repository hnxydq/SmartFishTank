package com.tfxiaozi.smartfishtank.media;

import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;

import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.MediaPlayer;

import java.util.ArrayList;

/**
 * Created by dongqiang on 2016/9/26.
 */

public class VLCMediaPlayer implements IVLCPlayer{

    private static final String TAG = VLCMediaPlayer.class.getSimpleName();

    private static MediaPlayer sMediaPlayer;

    private static LibVLC sLibVLC;

    private int mPlayState = PlaybackStateCompat.STATE_NONE;

    static {
        ArrayList<String> options = new ArrayList<>();
        options.add("--http-reconnect");
        options.add("--network-caching=2000");
        sLibVLC = new LibVLC(options);
        sMediaPlayer = new MediaPlayer(sLibVLC);
    }


    private class MediaPlayrListener implements MediaPlayer.EventListener {

        @Override
        public void onEvent(MediaPlayer.Event event) {

        }
    }

    public VLCMediaPlayer() {
        sMediaPlayer = new MediaPlayer(sLibVLC);



    }

    public static LibVLC getLibVlcInstance() {
        return sLibVLC;
    }

    public static MediaPlayer getMediaPlayerInstance() {
        return sMediaPlayer;
    }


    @Override
    public void prepare() {

    }

    @Override
    public void play() throws IllegalStateException {
        Log.d(TAG, "play()");
        mPlayState = PlaybackStateCompat.STATE_PLAYING;
        handlePlayState();
    }

    @Override
    public void pause() throws IllegalStateException {
        Log.d(TAG, "pause()");
        mPlayState = PlaybackStateCompat.STATE_PAUSED;
        handlePlayState();
    }



    @Override
    public void seekTo(long msec) throws IllegalStateException {
        Log.d(TAG, "seekTo()");
        getMediaPlayerInstance().setTime(msec);
    }

    @Override
    public void stop() {
        getMediaPlayerInstance().stop();
    }

    private void handlePlayState() {
        if (mPlayState == PlaybackStateCompat.STATE_PAUSED
                && getMediaPlayerInstance().isPlaying()) {
            getMediaPlayerInstance().pause();
        } else if (mPlayState == PlaybackStateCompat.STATE_PLAYING
                && !getMediaPlayerInstance().isPlaying()) {
            getMediaPlayerInstance().play();
        }
    }
}
