package com.mji.tapia.youcantapia.features.setting;

import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.managers.audio.TapiaAudioManager;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class MainSettingPresenter extends BasePresenter<MainSettingContract.View, MainSettingContract.Navigator> implements MainSettingContract.Presenter {
    MainSettingPresenter(MainSettingContract.View view, MainSettingContract.Navigator navigator) {
        super(view, navigator);
    }

    @Override
    public void onBack() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        navigator.goBack();
    }
}
