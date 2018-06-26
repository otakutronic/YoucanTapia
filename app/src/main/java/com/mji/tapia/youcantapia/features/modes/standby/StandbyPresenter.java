package com.mji.tapia.youcantapia.features.modes.standby;

import android.util.Log;

import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.features.user.model.UserRepository;
import com.mji.tapia.youcantapia.managers.resources.ResourcesManager;
import com.mji.tapia.youcantapia.managers.tts.TTSManager;
import com.mji.tapia.youcantapia.managers.wake_up.WakeUpManager;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class StandbyPresenter extends BasePresenter<StandbyContract.View, StandbyContract.Navigator> implements StandbyContract.Presenter {

    private String caller;
    private ResourcesManager resourcesManager;
    private TTSManager ttsManager;
    private UserRepository userRepository;
    private WakeUpManager wakeUpManager;

    static final private int minWaitInMilli = 30 * 1000;
    static final private int maxWaitInMilli = 30 * 60 * 1000;

    StandbyPresenter(StandbyContract.View view, StandbyContract.Navigator navigator, String caller, ResourcesManager resourcesManager,
                     TTSManager ttsManager, UserRepository userRepository, WakeUpManager wakeUpManager) {
        super(view, navigator);
        this.caller = caller;
        this.resourcesManager = resourcesManager;
        this.ttsManager = ttsManager;
        this.userRepository = userRepository;
        this.wakeUpManager = wakeUpManager;
    }

    private Action autoSpeechAction = new Action() {
        @Override
        public void run() throws Exception {
            Log.e("STANDBY", "start speech");
            autoSpeechDisposable = ttsManager.createSession(R.array.standby_auto_speech).start()
            .subscribe(() -> autoSpeechDisposable = Completable.timer(getRandomTime(), TimeUnit.MILLISECONDS)
                    .subscribe(autoSpeechAction));

        }
    };

    @Override
    public void init() {
        super.init();
        wakeUpManager.init();
    }

    private Disposable stateDisposable;
    private Disposable autoSpeechDisposable;
    private Disposable wakeUpDisposable;
    @Override
    public void activate() {
        super.activate();
        Log.e("STANDBY", "ACTIVATE");
        stateDisposable = ttsManager.getCurrentStateObserver()
                .subscribe(ttsState -> {
                   switch (ttsState) {
                       case SPEAKING:
                           view.setSpeakBackground();
                           break;
                       default:
                           view.setNormalBackground();
                           break;
                   }
                });
        wakeUpDisposable = wakeUpManager.startListening().subscribe(s -> {
            wakeUpManager.stopListening();
            navigator.openTalkModeScreen();
        });
    }

    @Override
    public void deactivate() {
        super.deactivate();
        Log.e("STANDBY", "DEACTIVATE");
        if (stateDisposable != null && !stateDisposable.isDisposed()) {
            stateDisposable.dispose();
        }
        if (autoSpeechDisposable != null && !autoSpeechDisposable.isDisposed()) {
            autoSpeechDisposable.dispose();
        }

        if ( wakeUpDisposable != null && !wakeUpDisposable.isDisposed()) {
            wakeUpDisposable.dispose();
        }
        wakeUpManager.stopListening();
    }

    @Override
    public void onMenu() {
        view.startTransition().subscribe(() -> navigator.openMainMenuScreen());
    }

    @Override
    public void onReady() {
        TTSManager.Session ttsSession = null;
        switch (caller) {
            case StandbyFragment.FIRST_SETTING_CALLER:
                ttsSession = ttsManager.createSession(String.format(resourcesManager.getString(R.string.standby_first_introduction), userRepository.getUser().name));
                break;
            case StandbyFragment.SPLASH_CALLER:
                ttsSession = ttsManager.createSession(R.array.standby_auto_speech);
                break;
            case StandbyFragment.SLEEP_CALLER:
                ttsSession = ttsManager.createSession(R.array.standby_auto_speech);
                break;
        }
        if (ttsSession != null) {
            autoSpeechDisposable = ttsSession.start().subscribe(() -> autoSpeechDisposable = Completable.timer(getRandomTime(), TimeUnit.MILLISECONDS)
                    .subscribe(autoSpeechAction));
        }
    }

    private int getRandomTime() {
        Random random = new Random();
        int randTime = random.nextInt(maxWaitInMilli - minWaitInMilli) + minWaitInMilli;
        Log.e("STANDBY", "next speech time: " + randTime);
        return randTime;
    }
}
