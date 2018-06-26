package com.mji.tapia.youcantapia.managers.audio;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Created by Sami on 3/30/2018.
 */

public interface TapiaAudioManager {

    int MAX_VOLUME = 15;

    enum AudioType {
        SOUND_EFFECT,
        BGM,
        VOICE
    }

    //default AudioType is BGM
    interface AudioSession {
        AudioType getAudioType();
        boolean isPrepared();
        Single<Integer> getDuration();
        Completable play();
        Completable restart();
        void stop();
        void pause();
        void resume();

    }

    void init();

    int getVolume();

    int getDefaultVolume();

    int getMaxVolume();

    void setVolume(int volume, boolean save);

    AudioSession createAudioSessionFromAsset(String filePath, AudioType audioType);

    AudioSession createAudioSessionFromStorage(String filePath, AudioType audioType);

}
