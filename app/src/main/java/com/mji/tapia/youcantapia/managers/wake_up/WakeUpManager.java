package com.mji.tapia.youcantapia.managers.wake_up;

import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * Created by Sami on 5/15/2018.
 */

public interface WakeUpManager {

    Completable init();

    Observable<String> startListening();

    void stopListening();

    Observable<String> getWakeUpObservable();
}
