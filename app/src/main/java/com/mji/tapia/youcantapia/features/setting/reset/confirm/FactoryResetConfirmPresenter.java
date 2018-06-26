package com.mji.tapia.youcantapia.features.setting.reset.confirm;

import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.R;
import com.mji.tapia.youcantapia.managers.audio.TapiaAudioManager;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class FactoryResetConfirmPresenter extends BasePresenter<FactoryResetConfirmContract.View, FactoryResetConfirmContract.Navigator> implements FactoryResetConfirmContract.Presenter {

    FactoryResetConfirmPresenter(FactoryResetConfirmContract.View view, FactoryResetConfirmContract.Navigator navigator) {
        super(view, navigator);
    }

    @Override
    public void init() {
        super.init();
    }


    @Override
    public void activate() {
        super.activate();
        ttsManager.createSession(R.string.factory_reset_confirm_speech).start();
    }

    @Override
    public void onYes() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        navigator.onContinue();
    }

    @Override
    public void onNo() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        navigator.onBack();
    }
}
