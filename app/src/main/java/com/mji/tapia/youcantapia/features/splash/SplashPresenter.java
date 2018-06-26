package com.mji.tapia.youcantapia.features.splash;

import android.content.Context;

import com.mji.tapia.youcantapia.BasePresenter;

import com.mji.tapia.youcantapia.features.user.model.UserRepository;
import com.mji.tapia.youcantapia.managers.brightness.TapiaBrightnessManager;
import com.mji.tapia.youcantapia.managers.resources.ResourcesManager;
import com.mji.tapia.youcantapia.managers.time.TapiaTimeManager;
import com.mji.tapia.youcantapia.managers.tts.TTSManager;
import com.mji.tapia.youcantapia.room_db.AppDatabase;

import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class SplashPresenter extends BasePresenter<SplashContract.View, SplashContract.Navigator> implements SplashContract.Presenter {

    static final private String TAG = "SplashPresenter";

    private TTSManager ttsManager;

    private Completable ttsCompletable;

    private Completable minimumTime;

    private TapiaTimeManager tapiaTimeManager;

    private UserRepository userRepository;

    private TapiaBrightnessManager tapiaBrightnessManager;

    private Context context;

    SplashPresenter(SplashContract.View view,
                    SplashContract.Navigator navigator,
                    Context context,
                    UserRepository userRepository,
                    TTSManager ttsManager,
                    TapiaTimeManager tapiaTimeManager,
                    TapiaBrightnessManager tapiaBrightnessManager) {
        super(view, navigator);
        this.ttsManager = ttsManager;
        this.context = context;
        this.tapiaTimeManager = tapiaTimeManager;
        this.userRepository = userRepository;
        this.tapiaBrightnessManager = tapiaBrightnessManager;
    }

    @Override
    public void init() {
        super.init();
        tapiaTimeManager.init();
    }

    @Override
    public void activate() {
        super.activate();

        ttsCompletable = ttsManager.init();
        minimumTime = Completable.timer(10000, TimeUnit.MILLISECONDS);//Minimum showed time

        tapiaBrightnessManager.setTapiaBrightness(tapiaBrightnessManager.getDefaultBrightness());
        tapiaAudioManager.setVolume(tapiaAudioManager.getDefaultVolume(), false);
        Completable.mergeArray(minimumTime, ttsCompletable, AppDatabase.copyDataBase(context))
        .subscribe(() ->view.clearScreen().subscribe(this::next));
    }

    @Override
    public void deactivate() {
        super.deactivate();
    }


    void next() {
        if (userRepository.getUser() == null) {
            navigator.openIntroductionScreen();
        } else {
            navigator.openStandByScreen();
        }
    }
}
