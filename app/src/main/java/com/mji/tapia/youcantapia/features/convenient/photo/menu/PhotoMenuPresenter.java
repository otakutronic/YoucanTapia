package com.mji.tapia.youcantapia.features.convenient.photo.menu;

import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.managers.audio.TapiaAudioManager;

/**
 * Created by Usman on 2018/05/01.
 */

public class PhotoMenuPresenter extends BasePresenter<PhotoMenuContract.View, PhotoMenuContract.Navigator> implements PhotoMenuContract.Presenter {
    protected PhotoMenuPresenter(PhotoMenuContract.View view, PhotoMenuContract.Navigator navigator) {
        super(view, navigator);
    }

    @Override
    public void onCameraSelect() {
        navigator.openCameraScreen();
    }

    @Override
    public void onPhotoGalarySelect() {
        navigator.openGalaryScreen();
    }

    @Override
    public void playButtonSound() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
    }
}
