package com.mji.tapia.youcantapia.features.convenient;

import com.mji.tapia.youcantapia.BasePresenter;
import com.mji.tapia.youcantapia.managers.audio.TapiaAudioManager;

/**
 * Created by Sami on 3/30/2018.
 *
 */

public class ConvenientPresenter extends BasePresenter<ConvenientContract.View, ConvenientContract.Navigator> implements ConvenientContract.Presenter {


    ConvenientPresenter(ConvenientContract.View view, ConvenientContract.Navigator navigator) {
        super(view, navigator);
    }

    @Override
    public void onBack() {
        tapiaAudioManager.createAudioSessionFromAsset("shared/se/button_click.mp3", TapiaAudioManager.AudioType.SOUND_EFFECT).play();
        navigator.goBack();
    }
}
