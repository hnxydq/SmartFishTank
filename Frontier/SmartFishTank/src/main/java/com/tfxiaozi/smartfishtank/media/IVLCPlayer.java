package com.tfxiaozi.smartfishtank.media;

/**
 * Created by dongqiang on 2016/9/26.
 */

public interface IVLCPlayer {

    public abstract void prepare();

    public abstract void play();

    public abstract void pause();

    public abstract void seekTo(long msec);

    public abstract void stop();
}
