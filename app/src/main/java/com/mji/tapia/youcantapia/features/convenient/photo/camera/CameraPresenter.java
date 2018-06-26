package com.mji.tapia.youcantapia.features.convenient.photo.camera;

import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.managers.audio.TapiaAudioManager;
import com.mji.tapia.youcantapia.managers.tts.TTSManager;

import java.io.File;

/**
 * Created by Usman on 2018/05/01.
 */

public class CameraPresenter extends BasePresenter<CameraContract.View, CameraContract.Navigator> implements CameraContract.Presenter {
    private TTSManager ttsManager;

    protected CameraPresenter(CameraContract.View view, CameraContract.Navigator navigator, TTSManager ttsManager) {
        super(view, navigator);
        this.ttsManager = ttsManager;
    }

    public void getReady() {
        File[] fileList = new File(CameraFragment.PHOTO_DIR_SMALL).listFiles();
        if (fileList != null && fileList.length >= 1000) {
            view.showDialogForCrossingLimitaion();
        } else {
            ttsManager.createSession(R.string.photo_shooting_start_screen_tts)
                    .start()
                    .subscribe(() -> view.takePicture());
        }
    }

    @Override
    public void onGalarySelect() {
        navigator.openGalaryScreen();
    }

    @Override
    public void playSound(String path) {
        tapiaAudioManager.createAudioSessionFromAsset(path, TapiaAudioManager.AudioType.SOUND_EFFECT).play();
    }

    @Override
    public void playSpeech(String speech) {
        ttsManager.createSession(speech).start();
    }
}
