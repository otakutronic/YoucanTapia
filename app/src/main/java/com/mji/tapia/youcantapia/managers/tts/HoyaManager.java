package com.mji.tapia.youcantapia.managers.tts;

import android.content.Context;
import android.support.annotation.ArrayRes;
import android.support.annotation.StringRes;
import android.util.Log;

import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.managers.audio.TapiaAudioManager;
import com.mji.tapia.youcantapia.util.AssetUtils;

import java.io.File;
import java.util.Locale;
import java.util.Random;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.CompletableSubject;
import io.reactivex.subjects.SingleSubject;
import kr.co.voiceware.HIKARI;

/**
 * Created by Sami on 4/2/2018.
 *
 */

public class HoyaManager implements TTSManager {

    static final private String TAG = "HoyaManager";

    private Context context;

    private TapiaAudioManager tapiaAudioManager;

    private BehaviorSubject<TTSState> currentStateSubject = BehaviorSubject.create();

    private HoyaSession curSession;

    private CompletableSubject initCompletableSubject = CompletableSubject.create();

    static private TTSManager instance;
    static public TTSManager getInstance(Context context, TapiaAudioManager tapiaAudioManager) {
        if(instance == null) {
            instance = new HoyaManager(context, tapiaAudioManager);
        }
        return instance;
    }

    private HoyaManager(Context context, TapiaAudioManager tapiaAudioManager) {
        this.context = context;
        this.tapiaAudioManager = tapiaAudioManager;


    }

    @Override
    public Completable init() {
        if (initCompletableSubject.hasThrowable()){
            initCompletableSubject = CompletableSubject.create();
        }
        if (!initCompletableSubject.hasComplete()){
            Schedulers.io().scheduleDirect(() -> {
                syncDBFiles(initCompletableSubject);
                loadDB(initCompletableSubject);
                initCompletableSubject.onComplete();
            });
        }
        return initCompletableSubject;
    }

    @Override
    public Session createSession(String text) {
        if (!initCompletableSubject.hasComplete()) {
            init().blockingAwait();
        }
        return new HoyaSession(text);
    }

    public Session createRandomSession(String[] texts) {
        Random random = new Random();
        if (texts.length > 0) {

            if (!initCompletableSubject.hasComplete()) {
                init().blockingAwait();
            }
            return new HoyaSession(texts[random.nextInt(texts.length)]);
        } else {
            throw new RuntimeException("Create TTS Session With empty string array");
        }


    }

    @Override
    public Session createSession(@StringRes @ArrayRes int resId) {
        if (context.getResources().getResourceTypeName(resId).equals("string")) {
            return createSession(context.getResources().getString(resId));
        } else if (context.getResources().getResourceTypeName(resId).equals("array")) {
            return createRandomSession(context.getResources().getStringArray(resId));
        } else {
            throw new RuntimeException("Create TTS Session With resource different from string");
        }
    }

    @Override
    public Session createSession(@StringRes int resId, Object... args) {
        return createSession(String.format(Locale.JAPANESE, context.getResources().getString(resId), args));
    }

    @Override
    public Observable<TTSState> getCurrentStateObserver() {
        return currentStateSubject.distinctUntilChanged();
    }

    @Override
    public void stopCurrent() {
        if (curSession != null) {
            curSession.cancel();
        }
    }

    private Disposable currentStateDisposable;
    private void setCurSession(HoyaSession hoyaSession) {
        if (currentStateDisposable != null && !currentStateDisposable.isDisposed()){
            currentStateDisposable.dispose();
        }
        currentStateDisposable = hoyaSession.ttsStateBehaviorSubject.subscribe(currentStateSubject::onNext);
        curSession = hoyaSession;
    }

    private void loadDB(CompletableSubject completableSubject) {
        int license_rtn_jp = 0;

        final byte[] license_jp =  new byte[2048];

        try
        {
            license_rtn_jp = context.getResources().openRawResource(R.raw.verification_jp).read(license_jp);
        }
        catch(Exception ex)
        {
            completableSubject.onError(ex);
        }

        // TODO Auto-generated method stub
        int load_rtn_jp = 0;


        String engine_version_jp = "";
        String db_path = context.getFilesDir().getAbsolutePath() + "/hoya";
        try
        {
            load_rtn_jp = HIKARI.LOADTTS(db_path, license_jp);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            completableSubject.onError(e);
        }
        if(load_rtn_jp != 0)
        {
//            completableSubject.onError(new Error("could not load the voice db"));
        }
    }

    private void syncDBFiles(CompletableSubject completableSubject) {
        //check if database voice are here for Hoya
        File hoyaDir = new File(context.getFilesDir().getPath() + "/hoya");
        if (!hoyaDir.exists()) {
            if(!hoyaDir.mkdir())
                return;
        }
        File japaneseDb = new File(context.getFilesDir().getPath() + "/hoya/tts_single_db_hikari.vtdb");
        if(!japaneseDb.exists()) {
            AssetUtils.copyAssetFile(context, "hoya/tts_single_db_hikari.vtdb", context.getFilesDir().getPath() );
        }
    }

    private class HoyaSession implements Session {

        private String speech;

        private boolean isPreparedStart = false;

        private CompletableSubject prepareCompletable;

        private CompletableSubject endCompletable;

        private SingleSubject<Integer> durationSubject;

        private TapiaAudioManager.AudioSession audioSession;

        private TTSEmotion emotion = TTSEmotion.NORMAL;

        private BehaviorSubject<TTSState> ttsStateBehaviorSubject;

        private HoyaSession(String text) {
            speech = text;
            durationSubject = SingleSubject.create();
            prepareCompletable = CompletableSubject.create();
            ttsStateBehaviorSubject = BehaviorSubject.create();
            ttsStateBehaviorSubject.onNext(TTSState.IDLE);
            ttsStateBehaviorSubject.subscribe(ttsState -> HoyaManager.this.getCurrentStateObserver());
        }

        @Override
        public String getText() {
            return speech;
        }

        @Override
        public Single<Integer> getDuration() {
            return durationSubject;
        }


        @Override
        public Completable prepare() {
            if(prepareCompletable.hasComplete()){
                Log.w(TAG, "This session already prepared");
            } else {
                isPreparedStart = true;
                AndroidSchedulers.mainThread().scheduleDirect(() -> {
                    int pitch = 100;
                    int speed = 90;
                    int volume = 140;
                    int pause = 100;

                    switch (emotion) {
                        case SAD:
                            speech = "<vtml_emotion category=\"sadness\" level=\"2\">" + speech + "</vtml_emotion>";
                            break;
                        case ANGRY:
                            speech = "<vtml_emotion category=\"anger\" level=\"2\">" + speech + "</vtml_emotion>";
                            break;
                        case HAPPY:
                            speech = "<vtml_emotion category=\"happiness\" level=\"2\">" + speech + "</vtml_emotion>";
                            break;
                    }

                    String filename = (context.getFilesDir().getAbsolutePath()+"/play.wav");
                    int file_rtn = 0;
                    try {
                        HIKARI.SetPitchSpeedVolumePause(pitch, speed, volume, pause);
                        file_rtn = HIKARI.TextToFile(HIKARI.VT_FILE_API_FMT_S16PCM_WAVE, speech, filename, -1, -1, -1, -1, -1, -1);
                    }
                    catch(Exception e) {
                        prepareCompletable.onError(e);
                    }

                    if(file_rtn != 1) {
                        prepareCompletable.onError(new Error("Could not generate voice file"));
                    }

                    audioSession = tapiaAudioManager.createAudioSessionFromStorage(filename, TapiaAudioManager.AudioType.VOICE);
                    audioSession.getDuration().subscribe(durationSubject::onSuccess);
                    prepareCompletable.onComplete();
                });
            }
            return prepareCompletable;
        }

        @Override
        public Session setEmotion(TTSEmotion emotion) {
            if(emotion == null) {
                this.emotion = TTSEmotion.NORMAL;
            } else {
                this.emotion = emotion;
            }
            return this;
        }

        @Override
        public TTSEmotion getEmotion() {
            return emotion;
        }

        @Override
        public void pause() {
            if(audioSession != null) {
                audioSession.pause();
            }
        }

        @Override
        public void resume() {
            if(audioSession != null) {
                audioSession.resume();
            }
        }

        @Override
        public void cancel() {
            if(audioSession != null) {
                audioSession.stop();
            }
        }

        @Override
        public boolean isPrepared() {
            return isPreparedStart;
        }

        @Override
        public Completable start() {
            setCurSession(this);
            endCompletable = CompletableSubject.create();
            ttsStateBehaviorSubject.onNext(TTSState.IDLE);
            endCompletable.subscribe(() -> ttsStateBehaviorSubject.onNext(TTSState.FINISH), err -> ttsStateBehaviorSubject.onNext(TTSState.ERROR));
            if(isPreparedStart) {
                if(prepareCompletable.hasComplete()) {
                    ttsStateBehaviorSubject.onNext(TTSState.SPEAKING);
                    audioSession.play().subscribe(endCompletable::onComplete, err -> {
                        ttsStateBehaviorSubject.onNext(TTSState.ERROR);
                    });
                } else {
                    prepareCompletable.subscribe(
                            () -> {
                                ttsStateBehaviorSubject.onNext(TTSState.SPEAKING);
                                audioSession.play().subscribe(endCompletable::onComplete, err -> {
                                    ttsStateBehaviorSubject.onNext(TTSState.ERROR);
                                });
                            }
                    );
                }
            } else {
                prepare().subscribe(() -> {
                    ttsStateBehaviorSubject.onNext(TTSState.SPEAKING);
                    audioSession.play().subscribe(endCompletable::onComplete, err -> {
                        ttsStateBehaviorSubject.onNext(TTSState.ERROR);
                    });
                });
            }
            return endCompletable;
        }

        @Override
        public Completable restart() {
            setCurSession(this);
            endCompletable = CompletableSubject.create();
            ttsStateBehaviorSubject.onNext(TTSState.IDLE);
            endCompletable.subscribe(() -> ttsStateBehaviorSubject.onNext(TTSState.FINISH), err -> ttsStateBehaviorSubject.onNext(TTSState.ERROR));
            if(audioSession != null) {
                ttsStateBehaviorSubject.onNext(TTSState.SPEAKING);
                audioSession.restart();
            } else {
                start();
            }
            return endCompletable;
        }

        @Override
        public Completable getEndCompletable() {
            return endCompletable;
        }
    }
}
