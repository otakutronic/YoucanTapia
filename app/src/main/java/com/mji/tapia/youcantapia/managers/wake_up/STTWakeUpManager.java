package com.mji.tapia.youcantapia.managers.wake_up;

import android.util.Log;

import com.mji.tapia.youcantapia.managers.stt.STTManager;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Sami on 5/18/2018.
 *
 */

public class STTWakeUpManager implements WakeUpManager{

    static private final String wakeWord = "タピタ";

    static private WakeUpManager instance;
    static public WakeUpManager getInstance(STTManager sttManager) {
        if (instance == null) {
            instance = new STTWakeUpManager(sttManager);
        }
        return instance;
    }

    private STTManager sttManager;
    private PublishSubject<String> wakeWordPublishSubject;

    private STTWakeUpManager(STTManager sttManager) {
        this.sttManager = sttManager;
        wakeWordPublishSubject = PublishSubject.create();
    }

    @Override
    public Completable init() {
        return sttManager.initOffline("fuetrek/mji_robot.fuet");
    }

    private Disposable wakeDisposable;
    @Override
    public Observable<String> startListening() {
        Log.e("WAKE", "start session 1");
        wakeDisposable = sttManager.createSession().listen().subscribe(new Consumer<List<String>>() {
            @Override
            public void accept(List<String> stringList) throws Exception {
                String match = null;
                for (String s : stringList) {
                    if (s.contains(wakeWord)) {
                        match = s;
                    }
                }
                if (match != null) {
                    wakeWordPublishSubject.onNext(match);
                }
                //wake word
                Log.e("WAKE", "start session 2");
                wakeDisposable = sttManager.createSession().listen().subscribe(this);
            }
        });
        return wakeWordPublishSubject;
    }

    @Override
    public void stopListening() {
        if (wakeDisposable != null && !wakeDisposable.isDisposed()) {
            wakeDisposable.dispose();
        }
        Log.e("WAKE", "stop session");
        sttManager.stopCurrent();
    }

    @Override
    public Observable<String> getWakeUpObservable() {
        return wakeWordPublishSubject;
    }
}
