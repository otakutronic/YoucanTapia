package com.mji.tapia.youcantapia.features.music;

import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.managers.audio.TapiaAudioManager;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class MusicPresenter extends BasePresenter<MusicContract.View, MusicContract.Navigator> implements MusicContract.Presenter {
    MusicPresenter(MusicContract.View view, MusicContract.Navigator navigator) {
        super(view, navigator);
    }

    @Override
    public void onBack() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        navigator.goBack();
    }
}
