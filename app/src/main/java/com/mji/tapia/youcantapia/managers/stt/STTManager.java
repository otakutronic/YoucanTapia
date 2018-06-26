package com.mji.tapia.youcantapia.managers.stt;


import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.subjects.SingleSubject;

/**
 * Created by Sami on 5/15/2018.
 */

public interface STTManager {

    enum STTState{
        IDLE,
        LISTENING,// WAITING FOR VOICE
        RECORDING,//VOICE DETECTED
        PROCESSING,
        DONE,
        ERROR
    }

//    Completable initOnline();

    Completable initOffline(String modelPath);

//    Completable initOffline(int modelId);

    Session createSession();

    Observable<STTState> getCurrentStateObserver();

    Observable<Integer> getMicrophoneVolumeObserver();

    void stopCurrent();

    interface Session {

        void cancel();

        Single<List<String>> listen();

        Observable<STTState> getStateObserver();

//        Observable<Integer> getProgressObserver();

        Throwable getError();
    }
}
