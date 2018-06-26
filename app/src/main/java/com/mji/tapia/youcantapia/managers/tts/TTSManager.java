package com.mji.tapia.youcantapia.managers.tts;

import android.support.annotation.ArrayRes;
import android.support.annotation.StringRes;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by Sami on 3/30/2018.
 */

public interface TTSManager {

    enum TTSState {
        IDLE,
        SPEAKING,
        FINISH,
        ERROR
    }

    enum TTSEmotion {
        NORMAL,
        HAPPY,
        SAD,
        ANGRY
    }

    Completable init();

    Session createSession(String text);

    Session createRandomSession(String[] texts);

    Session createSession(@StringRes @ArrayRes int resId);

    Session createSession(@StringRes int resId, Object... args);

    Observable<TTSState> getCurrentStateObserver();

    void stopCurrent();

    interface Session {

        String getText();

        Single<Integer> getDuration();

        Completable prepare();

        Session setEmotion(TTSEmotion emotion);

        TTSEmotion getEmotion();

        void pause();

        void resume();

        void cancel();

        boolean isPrepared();

        //Return the endCompletable
        Completable start();

        Completable restart();

        Completable getEndCompletable();
    }
}
