package com.mji.tapia.youcantapia.features.setting.reset.finish;

import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.managers.audio.TapiaAudioManager;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class FactoryResetFinishPresenter extends BasePresenter<FactoryResetFinishContract.View, FactoryResetFinishContract.Navigator> implements FactoryResetFinishContract.Presenter {

    FactoryResetFinishPresenter(FactoryResetFinishContract.View view, FactoryResetFinishContract.Navigator navigator) {
        super(view, navigator);
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void onOk() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        navigator.reboot();
    }
}
